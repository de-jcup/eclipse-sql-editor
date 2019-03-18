/*
 * Copyright 2019 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
package de.jcup.sqleditor.script.parser;

import static de.jcup.sqleditor.script.parser.ParserState.*;

import java.util.ArrayList;
import java.util.List;

public class TokenParser {

	static final char CHAR_STRING_SINGLE_APOSTROPHE = '\'';
	static final char CHAR_STRING_DOUBLE_APOSTROPHE = '\"';
	static final char CHAR_STRING_DOUBLE_TICKED = '`';

	public TokenParser() {
	}

	public List<ParseToken> parse(String sql) throws TokenParserException {
		if (sql == null) {
			return new ArrayList<>();
		}
		ParseContext context = new ParseContext();
		context.chars = sql.toCharArray();
		try {
			for (; context.hasValidPos(); context.moveForward()) {

				if (isCommentStateHandled(context)) {
					continue;
				}
				if (isStringStateHandled(context)) {
					continue;
				}

				handleNotVariableNorCommentOrString(context);
			}
			// add last token if existing
			context.addTokenAndResetText();
		} catch (RuntimeException e) {
			throw new TokenParserException("Was not able to parse script because of runtime error", e);
		}

		return context.tokens;
	}

	private boolean isStringStateHandled(ParseContext context) {
		char c = context.getCharAtPos();

		if (c == CHAR_STRING_SINGLE_APOSTROPHE) {
			return handleString(INSIDE_SINGLE_STRING, context, INSIDE_DOUBLE_TICKED, INSIDE_DOUBLE_STRING);
		}
		/* handle double string */
		if (c == CHAR_STRING_DOUBLE_APOSTROPHE) {
			return handleString(INSIDE_DOUBLE_STRING, context, INSIDE_DOUBLE_TICKED, INSIDE_SINGLE_STRING);
		}
		/* handle double ticked string */
		if (c == CHAR_STRING_DOUBLE_TICKED) {
			return handleString(INSIDE_DOUBLE_TICKED, context, INSIDE_SINGLE_STRING, INSIDE_DOUBLE_STRING);
		}
		if (context.insideString()) {
			context.appendCharToText();
			return true;
		}
		return false;
	}

	private boolean isCommentStateHandled(ParseContext context) {
		char c = context.getCharAtPos();

		if (context.inState(INSIDE_COMMENT)) {
			/* in comment state */
			if (c == '\n') {
				context.addTokenAndResetText();
				context.switchTo(CODE);
			} else {
				context.appendCharToText();
			}
			return true;
		}
		return false;
	}

	private boolean handleNotVariableNorCommentOrString(ParseContext context) {
		char c = context.getCharAtPos();
		if (c == '\r') {
			/*
			 * ignore - we only use \n inside the data parsed so we will handle
			 * easy \r\n and \n
			 */
			context.moveCurrentTokenPosWhenEmptyText();
			return true;
		}
		if (c == '\n') {
			context.addTokenAndResetText();
			if (context.inState(INSIDE_COMMENT)) {
				context.switchTo(CODE);
			}
			return true;
		}

		if (c == ';') {
			// special sql semicolon operator, separates sql statements .
		    // interesting for handling multiple statements so we need this information
			context.addTokenAndResetText();
			context.switchTo(CODE);
			context.appendCharToText();
            context.addTokenAndResetText();
			return true;
		}
		if (c == '=') {
			// special assign operator
			context.appendCharToText();
			if (!context.inState(VARIABLE)) {
				context.addTokenAndResetText();
			}
			return true;
		}

		if (c == '{' || c == '}') {
			// block start/ end found, add as own token
			context.addTokenAndResetText();
			context.appendCharToText();
			context.addTokenAndResetText();
			context.switchTo(CODE);
			return true;
		}

		if (c == '-' ) {
		    if (context.inState(INSIDE_COMMENT)) {
		        context.appendCharToText();
		    }else {
		        if (context.getCharBefore()=='-') {
		            context.addTokenAndResetText();
		            context.switchTo(INSIDE_COMMENT);
		            context.appendCharToText();
		            context.appendCharToText(); 
		        }else {
		            return true;
		        }
		    }
			return true;
		}
		/*
		 * not inside a comment build token nor in string, so whitespaces are
		 * not necessary!
		 */
		if (Character.isWhitespace(c)) {
			context.addTokenAndResetText();
			return true;
		}
		/* otherwise simply add text */
		context.appendCharToText();
		return false;
	}

	private boolean isStringChar(char c) {
		boolean isStringChar = c == '\"';
		isStringChar = isStringChar || c == '\'';
		isStringChar = isStringChar || c == '`';
		return isStringChar;
	}

	private boolean handleString(ParserState stringState, ParseContext context, ParserState... otherStringStates) {
		for (ParserState otherStringState : otherStringStates) {
			if (context.inState(otherStringState)) {
				/* inside other string - ignore */
				context.appendCharToText();
				return true;
			}

		}
		if (context.isCharBeforeEscapeSign()) {
			/* escaped */
			context.appendCharToText();
			return true;
		}
		if (context.inState(stringState)) {
			/* close single string */
			context.appendCharToText();
			context.restoreStateBeforeString();
			return true;
		}
		if (context.inState(ParserState.VARIABLE)) {
			context.appendCharToText();
			return true;
		}
		context.switchToStringState(stringState);
		context.appendCharToText();
		return true;

	}

	/**
	 * Situation: <br>
	 * 
	 * <pre>
	 * $('hello' a'x')
	     ^------  
	 * 012345678
	 * </pre>
	 * 
	 * Cursor is at a position after "$(". means index:2.<br>
	 * <br>
	 * 
	 * The method will now check if this is a string start, if so the complete
	 * content of string will be fetched and appended and pos changed. In the
	 * example above the postion will be 8 after execution and string of context
	 * will be <code>"$('hello'"</code>.
	 * 
	 */
	void moveUntilNextCharWillBeNoStringContent(ParseContext context) {
		context.appendCharToText();

		char c = context.getCharAtPos();
		if (!isStringChar(c)) {
			/*
			 * no string - do nothing, pos increment/move forward is done
			 * outside in for next loop!
			 */
			return;
		}
		if (context.isCharBeforeEscapeSign()) {
			return;
		}
		char stringCharToScan = c;
		moveToNextCharNotInStringAndAppendMovements(context, stringCharToScan);

	}

	private void moveToNextCharNotInStringAndAppendMovements(ParseContext context, char stringCharToScan) {
		if (!context.canMoveForward()) {
			return;
		}
		context.moveForward();
		context.appendCharToText();

		char c = context.getCharAtPos();
		if (c == stringCharToScan) {
			if (!context.isCharBeforeEscapeSign()) {
				/* found ending of string - so simply return */
				return;
			}
		}
		moveToNextCharNotInStringAndAppendMovements(context, stringCharToScan);
	}

}
