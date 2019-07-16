package de.jcup.sqleditor.script.parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.jcup.sqleditor.script.parser.ast.SQLAbstractSyntaxTree;
import de.jcup.sqleditor.script.parser.ast.Select;

public class AstTokenParserTest {
    
    private AstTokenParser parserToTest;

    @Before
    public void before() {
        parserToTest = new AstTokenParser();
    }

    @Test
    public void tokenparser_notnull() {
        assertNotNull(parserToTest.tokenParser);
    }
    
    @Test
    public void select_all_from_employees_correct_ast() throws Exception{
        /* execute */
        SQLAbstractSyntaxTree ast = parserToTest.parse("select * from EMPLOYEES");
        
        /* test */
        assertNotNull(ast);
        assertNotNull(ast.statements);
        assertEquals(1,ast.statements.size());
        Select select = (Select) ast.statements.get(0);
        assertEquals("EMPLOYEES", select.from.tableName);
        assertEquals(1, select.columns.size());
        assertEquals("*", select.columns.get(0).columnName);
        
    }

}
