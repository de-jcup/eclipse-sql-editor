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
package de.jcup.sqleditor.script.formatter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.jcup.sqleditor.script.formatter.token.ParseTokenRunner;
import de.jcup.sqleditor.script.parser.ParseToken;
import de.jcup.sqleditor.script.parser.TestParseToken;

public class ParseTokenRunnerTest {

    @Test
    public void test() {
        /* prepare */
        List<ParseToken> list = new ArrayList<ParseToken>();
        list.add(new TestParseToken("abc"));
        list.add(new TestParseToken("def"));
        ParseTokenRunner tokenRunnerToTest = new ParseTokenRunner(list);
        
        /* execute */
        assertTrue(tokenRunnerToTest.hasToken());
        assertEquals("abc", tokenRunnerToTest.getToken().getText());
        assertTrue(tokenRunnerToTest.canForward());
        assertTrue(tokenRunnerToTest.canGotoTokenNumber(0));
        assertTrue(tokenRunnerToTest.canGotoTokenNumber(1));
        tokenRunnerToTest.gotoTokenNumber(0);
        assertEquals("abc", tokenRunnerToTest.getToken().getText());
        tokenRunnerToTest.forward();
        assertEquals("def", tokenRunnerToTest.getToken().getText());
        tokenRunnerToTest.gotoTokenNumber(1);
        assertEquals("def", tokenRunnerToTest.getToken().getText());
        assertFalse(tokenRunnerToTest.canForward());
        assertEquals("def", tokenRunnerToTest.getToken().getText());
    }

}
