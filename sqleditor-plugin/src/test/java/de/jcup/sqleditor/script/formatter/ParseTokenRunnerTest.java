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
