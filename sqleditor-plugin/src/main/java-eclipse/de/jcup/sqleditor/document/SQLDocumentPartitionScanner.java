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

import de.jcup.sqleditor.document.keywords.SQLDataTypesKeywords;
import de.jcup.sqleditor.document.keywords.SQLFunctionKeywords;
import de.jcup.sqleditor.document.keywords.SQLKeyword;
import de.jcup.sqleditor.document.keywords.SQLSchemaKeywords;
import de.jcup.sqleditor.document.keywords.SQLStatementKeywords;
import de.jcup.sqleditor.document.keywords.SQLStatementTargetKeyWords;
import de.jcup.sqleditor.document.keywords.SQLWhereBlockKeyWords;

public class SQLDocumentPartitionScanner extends RuleBasedPartitionScanner {

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

		rules.add(new SingleLineRule("--", "", comment, (char) -1, true));
		rules.add(new MultiLineRule("/*", "*/", comment));
		rules.add(new SQLStringRule("\"", "\"", doubleString));
		rules.add(new SQLStringRule("'", "'", singleString));


		addStatementsRule(rules, sqlStatementKeywords, SQLStatementKeywords.values());
		addStatementsRule(rules, sqlWhereBlockKeywods, SQLWhereBlockKeyWords.values());
		addStatementsRule(rules, sqlSchemaKeywords, SQLSchemaKeywords.values());
		
		addStatementsRule(rules, dataTypeKeywords, SQLDataTypesKeywords.values());
		
		addStatementsRule(rules, sqlStatementTargetKeywords, SQLStatementTargetKeyWords.values());
		addStatementsRule(rules, sqlFunctionKeywords, SQLFunctionKeywords.values());

		setPredicateRules(rules.toArray(new IPredicateRule[rules.size()]));
	}

	private void addStatementsRule(List<IPredicateRule> rules, IToken token, SQLKeyword[] values) {
	    SQLKeyWordRule rule = new SQLKeyWordRule(token, values);
	    rules.add(rule);
	}

	private IToken createToken(SQLDocumentIdentifier identifier) {
		return new Token(identifier.getId());
	}
}
