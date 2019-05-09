package de.jcup.sqleditor.script.formatter;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.jcup.sqleditor.TestScriptLoader;
import de.jcup.sqleditor.script.formatter.token.TokenBasedSQLFormatter;

public class TokenBasedSQLFormatterTest {

    private TokenBasedSQLFormatter formatterToTest;

    @Before
    public void before() {
        formatterToTest=new TokenBasedSQLFormatter();
    }
    
    @Test
    public void comments_are_handled_check_by_testfiles() throws Exception{
        /* prepare*/
        String original= read("origin/test1.sql");
        
        String expected = read("result/test1.sql");
             
        
        
        /* execute*/
        String formatted = formatterToTest.format(original, SQLFormatConfig.DEFAULT);
        
        /* test*/
        assertEquals(expected, formatted);
    }
    
    private String read(String path) throws IOException {
       return TestScriptLoader.loadScriptFromTestScripts("formatter/"+path);
    }
    
    @Test
    public void comments_are_handled() throws Exception{
        /* prepare*/
        String original= "CREATE TABLE adm_project\n" + 
                "(\n" + 
                "   project_name varchar(60) not null, -- we accept 60 (3x20)\n" + 
                "   project_description varchar(512), -- description fields always 512 chars\n" + 
                "   version integer,\n" + 
                "   PRIMARY KEY (project_name)\n" + 
                ");";
        
        String expected = 
               "CREATE TABLE adm_project\n" + 
               "(\n" + 
               "   project_name varchar(60) not null, -- we accept 60 (3x20)\n" + 
               "   project_description varchar(512), -- description fields always 512 chars\n" + 
               "   version integer,\n" + 
               "   PRIMARY KEY (project_name)\n" + 
               ");";
        
        
        /* execute*/
        String formatted = formatterToTest.format(original, SQLFormatConfig.DEFAULT);
        
        /* test*/
        assertEquals(expected, formatted);
    }
    @Test
    public void select_subselect_is_indented() throws Exception{
        /* prepare*/
        String original= "DELETE FROM MYSCHEMA.DBP_FIRSTNAMEINDEX "+ 
                "where " + 
                "   FIRS_USERS_ID in (" + 
                "select USER_ID from MYSCHEMA.DBP_USER " + 
                "where " + 
                "USER_UID_UPPER= 'albert.tregnaghi'" + 
                ")";

        String expected = 
               "DELETE FROM MYSCHEMA.DBP_FIRSTNAMEINDEX\n" + 
               "where\n" + 
               "FIRS_USERS_ID in\n" + 
               "(\n" + 
               "   select\n" + 
               "      USER_ID\n" + 
               "   from MYSCHEMA.DBP_USER\n" + 
               "   where\n" + 
               "   USER_UID_UPPER= 'albert.tregnaghi'\n" + 
               ")";
        
        
        /* execute*/
        String formatted = formatterToTest.format(original, SQLFormatConfig.DEFAULT);
        
        /* test*/
        assertEquals(expected, formatted);
    }
    
    @Test
    public void select_star_from_xyz() throws Exception{
        /* prepare*/
        String expected = "select * from table1";
        
        /* execute*/
        String formatted = formatterToTest.format("select * from table1", SQLFormatConfig.DEFAULT);
        
        /* test*/
        assertEquals(expected, formatted);
    }
    
    @Test
    public void two_separated_selects_with_asterisk_projection_will_be_in_two_lines() throws Exception{
        // @formatter:off
        /* prepare*/
        String expected = "select * from table1;\n"
                        + "select * from table2";
        // @formatter:on
        
        /* execute*/
        String formatted = formatterToTest.format("select * from table1;select * from table2",SQLFormatConfig.DEFAULT);
        
        /* test*/
        assertEquals(expected, formatted);
    }
    
    @Test
    public void two_separated_selects_with_asterisk_projection_one_with_wherewill_be_in_multiple_lines() throws Exception{
        // @formatter:off
        /* prepare*/
        String expected = "select * from table1 u\n"
                        + "where\n"
                        + "   u.firstname= 'albert';\n"
                        + "select * from table2";
        // @formatter:on
        
        /* execute*/
        String formatted = formatterToTest.format("select * from table1 u where u.firstname='albert';select * from table2",SQLFormatConfig.DEFAULT);
        
        /* test*/
        assertEquals(expected, formatted);
    }
    
    @Test
    public void a_select_with_fields_is_separated_on_default() throws Exception{
        // @formatter:off
        /* prepare*/
        String sql      = "select firstname,lastname,phone,email from table1";
        String expected = "select\n"
                        + "   firstname,\n"
                        + "   lastname,\n"
                        + "   phone,\n"
                        + "   email\n"
                        + "from\n"
                        + "   table1";
        // @formatter:on
        
        /* execute*/
        String formatted = formatterToTest.format(sql);
        
        /* test*/
        assertEquals(expected, formatted);
    }
    
    
    @Test
    public void a_create_table_block() throws Exception{
        // @formatter:off
        /* prepare*/
        String sql      = "create table Table1 ( created timestamp not null )";
        String expected = "create table Table1\n"
                        + "(\n"
                        + "   created timestamp not null\n"
                        + ")";
        // @formatter:on
        
        /* execute*/
        String formatted = formatterToTest.format(sql);
        
        /* test*/
        assertEquals(expected, formatted);
    }
    
    @Test
    public void a_create_table_block_2() throws Exception{
        // @formatter:off
        /* prepare*/
        String sql      = "create table Table1 ( created timestamp not null, destroyed timestamp not null )";
        String expected = "create table Table1\n"
                        + "(\n"
                        + "   created timestamp not null,\n"
                        + "   destroyed timestamp not null\n"
                        + ")";
        // @formatter:on
        
        /* execute*/
        String formatted = formatterToTest.format(sql);
        
        /* test*/
        assertEquals(expected, formatted);
    }
    
    @Test
    public void a_select_distint_with_fields_is_separated_on_default() throws Exception{
        // @formatter:off
        /* prepare*/
        String sql      = "select distinct firstname,lastname,phone,email from table1";
        String expected = "select distinct\n"
                        + "   firstname,\n"
                        + "   lastname,\n"
                        + "   phone,\n"
                        + "   email\n"
                        + "from table1";
        // @formatter:on
        
        /* execute*/
        String formatted = formatterToTest.format(sql);
        
        /* test*/
        assertEquals(expected, formatted);
    }
    
    @Test
    public void a_select_distint_with_fields_is_separated_on_default_with_comments() throws Exception{
        // @formatter:off
        /* prepare*/
        String sql      = "select distinct firstname, -- my comment1\nlastname, -- my comment2\nphone,email from table1";
        String expected = "select distinct\n"
                        + "   firstname,\n"
                        + "   -- my comment1\n"
                        + "   lastname,\n"
                        + "   -- my comment2\n"
                        + "   phone,\n"
                        + "   email\n"
                        + "from table1";
        // @formatter:on
        
        /* execute*/
        String formatted = formatterToTest.format(sql);
        
        /* test*/
        assertEquals(expected, formatted);
    }
    
    @Test
    public void a_select_case_in_order_by() throws Exception{
        // @formatter:off
        /* prepare*/
        String sql = "SELECT CustomerName, City, Country\n" + 
                          "FROM Customers\n" + 
                          "ORDER BY\n" + 
                          "(CASE\n" + 
                          "    WHEN City IS NULL THEN Country\n" + 
                          "    ELSE City\n" + 
                          "END);";
        String expected = "SELECT CustomerName, City, Country\n" + 
                "FROM Customers\n" + 
                "ORDER BY\n" + 
                "(CASE\n" + 
                "    WHEN City IS NULL THEN Country\n" + 
                "    ELSE City\n" + 
                "END);";
        // @formatter:on
        
        /* execute*/
        String formatted = formatterToTest.format(sql);
        
        /* test*/
        assertEquals(expected, formatted);
    }

    @Test
    public void a_select_with_fields_is_NOT_separated_when_turned_off() throws Exception{
     // @formatter:off
        /* prepare*/
        String sql      = "select firstname,lastname,phone,email from table1";
        String expected = "select firstname,lastname,phone,email from table1";
        // @formatter:on
        
        /* execute*/
        String formatted = formatterToTest.format(sql,SQLFormatConfig.builder().setBreakingSelectProjection(false).build());
        
        /* test*/
        assertEquals(expected, formatted);
    }
    
    @Test
    public void a_select_with_fields_is_NOT_separated_when_turned_off_with_comments() throws Exception{
     // @formatter:off
        /* prepare*/
        String sql      = "select firstname,lastname,phone,email from table1 -- comment";
        String expected = "select firstname,lastname,phone,email from table1 -- comment";
        // @formatter:on
        
        /* execute*/
        String formatted = formatterToTest.format(sql,SQLFormatConfig.builder().setBreakingSelectProjection(false).build());
        
        /* test*/
        assertEquals(expected, formatted);
    }
    
    @Test
    public void a_complex_select() throws Exception{
        // @formatter:off
        /* prepare*/
        String sql      = "select firstname,lastname,phone,email from table1 where rownum<=(select count(*)/2 from emp); ";
        String expected = "select firstname,lastname,phone,email from table1\n" + 
                "where\n" + 
                "rownum<=\n" + 
                "(\n" + 
                "   select count (*) /2 from emp\n" + 
                ");";
        // @formatter:on
        
        /* execute*/
        String formatted = formatterToTest.format(sql,SQLFormatConfig.builder().setBreakingSelectProjection(false).build());
        
        /* test*/
        assertEquals(expected, formatted);
    }
}
