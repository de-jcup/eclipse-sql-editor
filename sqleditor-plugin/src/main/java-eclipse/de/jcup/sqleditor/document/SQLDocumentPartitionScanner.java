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
package de.jcup.sqleditor.document;

import static de.jcup.sqleditor.document.SQLDocumentIdentifiers.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

import de.jcup.eclipse.commons.keyword.DocumentKeyWord;
import de.jcup.sqleditor.document.keywords.SQLDataTypesKeywords;
import de.jcup.sqleditor.document.keywords.SQLFunctionKeywords;
import de.jcup.sqleditor.document.keywords.SQLKeyword;
import de.jcup.sqleditor.document.keywords.SQLSchemaKeywords;
import de.jcup.sqleditor.document.keywords.SQLStatementKeywords;
import de.jcup.sqleditor.document.keywords.SQLStatementTargetKeyWords;
import de.jcup.sqleditor.document.keywords.SQLWhereBlockKeyWords;

public class SQLDocumentPartitionScanner extends RuleBasedPartitionScanner {

	private OnlyLettersKeyWordDetector onlyLettersWordDetector = new OnlyLettersKeyWordDetector();
//	private VariableDefKeyWordDetector variableDefKeyWordDetector = new VariableDefKeyWordDetector();

	public int getOffset(){
		return fOffset;
	}
	
	public SQLDocumentPartitionScanner() {
	    IToken dataTypeKeywords = createToken(DATA_TYPE_KEYWORDS);
		IToken sqlFunctionKeywords = createToken(FUNCTION_KEYWORDS);
		IToken comment = createToken(COMMENT);
		IToken singleString = createToken(SINGLE_STRING);
		IToken doubleString = createToken(DOUBLE_STRING);

		IToken sqlStatementKeywords = createToken(STATEMENT_KEYWORD);

		IToken sqlStatementTargetKeywords = createToken(STATEMENT_TARGET_KEYWORD);
		IToken sqlSchemaKeywords = createToken(SCHEMA_KEYWORD);
		IToken sqlWhereBlockKeywods = createToken(WHERE_BLOCK_KEYWORD);

		List<IPredicateRule> rules = new ArrayList<>();
//		rules.add(new SQLVariableRule(variables));
		rules.add(new SingleLineRule("--", "", comment, (char) -1, true));
		rules.add(new MultiLineRule("/*", "*/", comment));
		rules.add(new SQLStringRule("\"", "\"", doubleString));
		rules.add(new SQLStringRule("'", "'", singleString));

//		rules.add(new CommandParameterRule(parameters));

		buildWordRules(rules, sqlStatementKeywords, SQLStatementKeywords.values());
		buildWordRules(rules, sqlWhereBlockKeywods, SQLWhereBlockKeyWords.values());
		buildWordRules(rules, sqlSchemaKeywords, SQLSchemaKeywords.values());
		
		buildWordRules(rules, dataTypeKeywords, SQLDataTypesKeywords.values());
		
		buildWordRules(rules, sqlStatementTargetKeywords, SQLStatementTargetKeyWords.values());
		buildWordRules(rules, sqlFunctionKeywords, SQLFunctionKeywords.values());

		setPredicateRules(rules.toArray(new IPredicateRule[rules.size()]));
	}

	private void buildWordRules(List<IPredicateRule> rules, IToken token, SQLKeyword[] values) {
		for (SQLKeyword keyWord : values) {
			ExactWordPatternRule rule1 = new ExactWordPatternRule(onlyLettersWordDetector, createWordStart(keyWord), token,
					keyWord.isBreakingOnEof());

			ExactWordPatternRule rule2 = new ExactWordPatternRule(onlyLettersWordDetector, keyWord.getText().toUpperCase(), token,
					keyWord.isBreakingOnEof());
			if (keyWord.isHavingParameters()) {
			    /* a silly workaround  - bu works... in schema "varchar(," shall be rendered, ( not highlighted */
			    rule1.setAllowedPostfix('(');
			    rule2.setAllowedPostfix('(');
			}else if (keyWord.isCommaPostFixAllowed()) {
			    /* a silly workaround  - bu works... in schema "integer," shall be rendered, commata not highlighted */
			    rule1.setAllowedPostfix(','); 
                rule2.setAllowedPostfix(',');
			}
			rules.add(rule1);
			rules.add(rule2);
			
		}
	}

	private String createWordStart(DocumentKeyWord keyWord) {
		return keyWord.getText();
	}

	private IToken createToken(SQLDocumentIdentifier identifier) {
		return new Token(identifier.getId());
	}
}
