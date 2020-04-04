/*
 * Copyright 2020 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
package de.jcup.sqleditor.document;

import static org.junit.Assert.*;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.junit.Before;
import org.junit.Test;

import de.jcup.sqleditor.document.keywords.SQLStatementKeywords;
import de.jcup.sqleditor.document.keywords.SQLStatementTargetKeyWords;

/**
 * Sorrowly not executable by gradle because of eclipse dependencies. But at
 * least executable in eclipse environment. Tests sql key word rule works
 * 
 * @author Albert Tregnaghi
 *
 */
public class SQLKeyWordRuleTest {

    private IToken token;
    private OneLineSimpleTestCharacterScanner scanner;

    @Before
    public void before() {
        token = new Token("mocked");
    }

    @Test
    public void INSERT_space_xyz_is_found() {
        /* prepare */
        scanner = new OneLineSimpleTestCharacterScanner("INSERT xyz");
        SQLKeyWordRule rule = new SQLKeyWordRule(token, SQLStatementKeywords.values());

        /* execute */
        IToken resultToken = rule.evaluate(scanner);

        /* test */
        assertEquals(token, resultToken);
        assertEquals(6, scanner.column);

    }

    @Test
    public void VALUES_space_bracket_xyz_bracket_is_found() {
        /* prepare */
        scanner = new OneLineSimpleTestCharacterScanner("VALUES (xyz)");
        SQLKeyWordRule rule = new SQLKeyWordRule(token, SQLStatementKeywords.values());

        /* execute */
        IToken resultToken = rule.evaluate(scanner);

        /* test */
        assertEquals(token, resultToken);
        assertEquals(6, scanner.column);

    }

    @Test
    public void VALUES_noSpace_bracket_xyz_bracket_is_found() {
        /* prepare */
        scanner = new OneLineSimpleTestCharacterScanner("VALUES(xyz)");
        SQLKeyWordRule rule = new SQLKeyWordRule(token, SQLStatementKeywords.values());

        /* execute */
        IToken resultToken = rule.evaluate(scanner);

        /* test */
        assertEquals(token, resultToken);
        assertEquals(6, scanner.column);

    }

    @Test
    public void NAME_WITH_INDEX_INSIDE_IS_NOT_CATCHED_BY_RULE() {
        /* prepare */
        scanner = new OneLineSimpleTestCharacterScanner("DBP_FIRSTNAMEINDEX where");
        SQLKeyWordRule rule = new SQLKeyWordRule(token, SQLStatementTargetKeyWords.values());

        /* execute */
        IToken resultToken = rule.evaluate(scanner);

        /* test */
        assertEquals(0, scanner.column);
        assertEquals(Token.UNDEFINED, resultToken);

    }

}
