package de.jcup.sqleditor.script;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SQLScriptModelBuilderTest {

	private SQLScriptModelBuilder builderToTest;

	@Before
	public void before() {
		builderToTest = new SQLScriptModelBuilder();
	}

	@Test
	public void select_all_from_table1_results_in_select_item() {
		/* prepare */
	    String sql = "SELECT * from TABLE1";
	    
		/* execute */
		SQLScriptModel result = builderToTest.build(sql);

		/* test */
		assertNotNull(result);
		List<SQLStatement> sqlCommands = result.getSQLStatements();
		assertEquals(1,sqlCommands.size());
		SQLStatement command = sqlCommands.get(0);
		assertEquals("SELECT * from TABLE1", command.getName());

	}
	
	@Test
    public void select_12345678_from_table1_results_in_select_item() {
        /* prepare */
        String sql = "SELECT 12345678 from TABLE1";
        
        /* execute */
        SQLScriptModel result = builderToTest.build(sql);

        /* test */
        assertNotNull(result);
        List<SQLStatement> sqlCommands = result.getSQLStatements();
        assertEquals(1,sqlCommands.size());
        SQLStatement command = sqlCommands.get(0);
        assertEquals("SELECT 12345678 from TABLE1", command.getName());

    }
	
	@Test
    public void select_123456789_from_table1_results_in_select_item_with_reduced_info() {
        /* prepare */
        String sql = "SELECT 123456789 FROM TABLE1";
        
        /* execute */
        SQLScriptModel result = builderToTest.build(sql);

        /* test */
        assertNotNull(result);
        List<SQLStatement> sqlCommands = result.getSQLStatements();
        assertEquals(1,sqlCommands.size());
        SQLStatement command = sqlCommands.get(0);
        assertEquals("SELECT {..} FROM TABLE1", command.getName());

    }
	
	@Test
    public void create_table_1() {
        /* prepare */
        String sql = "create\n table \nTABLE1\n(test varchar(45))";
        
        /* execute */
        SQLScriptModel result = builderToTest.build(sql);

        /* test */
        assertNotNull(result);
        List<SQLStatement> sqlCommands = result.getSQLStatements();
        assertEquals(1,sqlCommands.size());
        SQLStatement command = sqlCommands.get(0);
        assertEquals("create table TABLE1", command.getName());

    }
	@Test
    public void create_table_1_drop_table1() {
        /* prepare */
        String sql = "CREATE\n TABLE \nTABLE1\n(test varchar(45));\n"
                + "drop TABLE TABLE1";
        
        /* execute */
        SQLScriptModel result = builderToTest.build(sql);

        /* test */
        assertNotNull(result);
        List<SQLStatement> sqlCommands = result.getSQLStatements();
        assertEquals(2,sqlCommands.size());
        int i=0;
        assertEquals("CREATE TABLE TABLE1", sqlCommands.get(i++).getName());
        assertEquals("drop TABLE TABLE1", sqlCommands.get(i++).getName());

    }
	
	@Test
    public void select_table2_create_table_1_drop_table1() {
        /* prepare */
        String sql = "SELECT userId,more FROM TABLE2;CREATE\n TABLE \nTABLE1\n(test varchar(45));\n"
                + "DROP table Table1";
        
        /* execute */
        SQLScriptModel result = builderToTest.build(sql);

        /* test */
        assertNotNull(result);
        List<SQLStatement> sqlCommands = result.getSQLStatements();
        int i=0;
        assertEquals(3,sqlCommands.size());
        assertEquals("SELECT {..} FROM TABLE2", sqlCommands.get(i++).getName());
        assertEquals("CREATE TABLE TABLE1", sqlCommands.get(i++).getName());
        assertEquals("DROP table Table1", sqlCommands.get(i++).getName());

    }
	
	@Test
	public void alter_table_x_add_constraint__add_is_a_substatement() {
	    /* prepare */
        String sql = "ALTER TABLE adm_project ADD CONSTRAINT c07_adm_project2owner FOREIGN KEY";
        
        /* execute */
        SQLScriptModel result = builderToTest.build(sql);

        /* test */
        assertNotNull(result);
        List<SQLStatement> sqlCommands = result.getSQLStatements();
        assertEquals(1,sqlCommands.size());
        SQLStatement command = sqlCommands.get(0);
        List<SQLStatement> subs = command.getSubStatements();
        assertNotNull(subs);
        assertEquals(1,subs.size());
        assertTrue(subs.get(0).getName().startsWith("ADD"));
	}
	
	@Test
    public void two_selects_results_in_2_select_statements() {
        /* prepare */
        String sql = "SELECT * FROM TABLE1;\nSELECT * FROM table2;";
        
        /* execute */
        System.out.println("test two lines");
        SQLScriptModel result = builderToTest.build(sql);

        /* test */
        assertNotNull(result);
        List<SQLStatement> sqlCommands = result.getSQLStatements();
        int i=0;
        assertEquals(2,sqlCommands.size());
        assertEquals("SELECT * FROM TABLE1", sqlCommands.get(i++).getName());
        assertEquals("SELECT * FROM table2", sqlCommands.get(i++).getName());

    }
}
