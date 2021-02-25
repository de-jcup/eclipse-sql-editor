package de.jcup.sqleditor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.jcup.sqleditor.document.keywords.SQLDataTypesKeywords;
import de.jcup.sqleditor.document.keywords.SQLFunctionKeywords;
import de.jcup.sqleditor.document.keywords.SQLKeyword;
import de.jcup.sqleditor.document.keywords.SQLStatementKeywords;
import de.jcup.sqleditor.document.keywords.SQLWhereBlockKeyWords;

public class SQLTooltipModel {

    Map<SQLKeyword, Page> map = new LinkedHashMap<>();

    private Example innerJoinExample1 = createInnerJoinExample1();
    private Example leftJoinExample1 = createLeftJoinExample1();
    private Example rightJoinExample1 = createRightJoinExample1();
    private Example fullJoinExample1 = createFullJoinExample1();
    private Example unionExample1 = createUnionExample1();
    private Example numericDataTypesExample1 = createNumericDataTypesExample1();
    private Example dateDataTypesExample1 = createDateDataTypesExample1();
    private Example sqlTranslateExample1 = createSQLTranslateExample1();
    private Example sqlUpdateExample1 = createUpdateExample1();
    private Example sqlUpdateExample2 = createUpdateExample2();
    private Example sqlDeleteExample1 = createDeleteExample1();
    private Example sqlInsertExample1 = createInsertExample1();
    private Example sqlInsertExample2 = createInsertExample2();

    private void register(Page page) {
        SQLKeyword keyword = page.mainKeyword;
        map.put(keyword, page);
    }

    /* @formatter:off */
    // Some parts are adopted from https://www.w3resource.com/sql/tutorials.php -
    // License was: "This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License."
    public SQLTooltipModel() {

        handleStatements();

        handleDataTypes();
        
        handleFunctions();
        
        handleWhereParts();
    }

    private void handleWhereParts() {
        createPageAndRegister(SQLWhereBlockKeyWords.GROUP, 
                "The usage of SQL GROUP BY clause is, to divide the rows in a table into smaller groups.\n" + 
                "\n" + 
                "The GROUP BY clause is used with the SQL SELECT statement.\n" + 
                "\n" + 
                "The grouping can happen after retrieves the rows from a table.\n" + 
                "\n" + 
                "When some rows are retrieved from a grouped result against some condition, that is possible with HAVING clause.\n" + 
                "\n" + 
                "The GROUP BY clause is used with the SELECT statement to make a group of rows based on the values of a specific column or expression. The SQL AGGREGATE function can be used to get summary information for every group and these are applied to an individual group.\n" + 
                "\n" + 
                "The WHERE clause is used to retrieve rows based on a certain condition, but it can not be applied to grouped result.\n" + 
                "\n" + 
                "In an SQL statement, suppose you are using GROUP BY, if required you can use HAVING instead of WHERE, after GROUP BY.",
                
                syntax("SELECT <column_list> \n" + 
                        "FROM < table name >\n" + 
                        "WHERE <condition>GROUP BY <columns> \n" + 
                        "[HAVING] <condition>;\n"
                        + "<ul>"
                        + "<li>table_name - Name of the table.</li>"
                        + "<li>column_list - Name of the columns of the table.</li>"
                        + "<li>columns - Name of the columns which will participate in grouping.</li>"
                        + "</ul>")
                );
        
        
        createPageAndRegister(SQLWhereBlockKeyWords.ORDER, 
                "The ORDER BY clause orders or sorts the result of a query according to the values in one or more specific columns. More than one columns can be ordered one within another. It depends on the user that, whether to order them in ascending or descending order. The default order is ascending.\n" + 
                "\n" + 
                "The SQL ORDER BY clause is used with the SQL SELECT statement.\n" + 
                "\n" + 
                "<b>Note:</b> SQL ORDER BY clause always come at the end of a SELECT statement.",
                
                syntax("SELECT <column_list> FROM < table name >.\n" + 
                        "WHERE <condition>\n" + 
                        "ORDER BY <columns> [ASC | DESC];\n"
                        + "<ul>"
                        + "<li>table_name - Name of the table.</li>"
                        + "<li>column_list - Name of the columns of the table.</li>"
                        + "<li>columns - Name of the columns which will participate in ordering.</li>"
                        + "</ul>")
                );
        
        createPageAndRegister(SQLWhereBlockKeyWords.ASC, 
                "Sort order ascending");
        
        createPageAndRegister(SQLWhereBlockKeyWords.DESC, 
                "Sort order descending");
        
    }

    private void handleFunctions() {
        handleArithmeticFunctions();
        handleSQLCharacterFunctions();
        
    }
    private void handleSQLCharacterFunctions() {
        createPageAndRegister(SQLFunctionKeywords.LOWER, 
                "The SQL LOWER() function is used to convert all characters of a string to lower case.\n"
                + "Parameter string is a string",
                syntax("LOWER(string)"));
        
        createPageAndRegister(SQLFunctionKeywords.UPPER, 
                "The SQL UPPER() function is used to convert all characters of a string to uppercase.\n"
                        + "Parameter string is a string",
                syntax("UPPER(string)"));
        
        createPageAndRegister(SQLFunctionKeywords.TRIM, 
                "The SQL TRIM() removes leading and trailing characters(or both) from a character string.\n"
                + "<ul>"
                + "<li>LEADING - Right most position of a string.</li>"
                + "<li>TRAILING - Left most position of a string.</li>"
                + "<li>BOTH - Right and left most position of a string.</li>"
                + "<li>removal_char - Character to be removed.</li>"
                + "<li>target_string - String on which the action will take place.</li>"
                + "<li>collation_name - Name of the collation to be applied to the expression.</li>"
                + "</ul>",
                syntax("TRIM( [ [{LEADING | TRAILING | BOTH}] [removal_char]\n" + 
                        "FROM ]   target_string    [COLLATE collation_name])"));
        
        createPageAndRegister(SQLFunctionKeywords.TRANSLATE, 
                "The SQL TRANSLATE() function replaces a sequence of characters in a string with another sequence of "
                + "characters. The function replaces a single character at a time.\n"
                + "<ul>"
                + "<li>char_value - A string.</li>"
                + "<li>translation_name - A character set.</li>"
                + "</ul>",
            syntax("TRANSLATE(char_value USING translation_name)"),
            sqlTranslateExample1);        
    }
    
    private void handleArithmeticFunctions() {
        createPageAndRegister(SQLFunctionKeywords.ABS, 
                "SQL ABS() function is used to get the absolute value of a number passed as an argument.\n"
                + "Parameter expression is a numeric value or numeric data type. The bit data type is not allowed.",
                syntax("ABS(expression)"));

        createPageAndRegister(SQLFunctionKeywords.CEIL, 
                "SQL CEIL() function is used to get the smallest integer which is greater than, or equal to, the specified numeric expression.\n"
                + "Parameter expression is a numeric value or numeric data type. The bit data type is not allowed.",
                syntax("CEIL(expression)"));
        
        createPageAndRegister(SQLFunctionKeywords.FLOOR, 
                "The SQL FLOOR() function rounded up any positive or negative decimal value down to the next least integer value. "
                + "SQL DISTINCT along with the SQL FLOOR() function is used to retrieve only unique value after rounded down to the next least integer value depending on the column specified.\n"
                + "Parameter expression is a numeric value or numeric data type. The bit data type is not allowed.",
                syntax("FLOOR(expression)"));

        createPageAndRegister(SQLFunctionKeywords.EXP, 
                "SQL EXP() function returns e raised to the n-th power(n is the numeric expression), where e is the base of a natural algorithm and the value of e is approximately 2.71828183.\n"
                + "Parameter expression is an expression which is a float or can be converted to a float.",
                syntax("EXP(expression)"));
        
        createPageAndRegister(SQLFunctionKeywords.LN, 
                "SQL LN() function returns the natural logarithm of n, where n is greater than 0 and its base is a number equal to approximately 2.71828183.\n "+ 
                "Parameter expression is an expression which is a float or can be converted to a float.",
                syntax("LN(expression)"));

        createPageAndRegister(SQLFunctionKeywords.MOD, 
                "SQL MOD() function is used to get the remainder from a division. The SQL DISTINCT command along with the SQL MOD() function is used to retrieve only unique records depending on the specified column or expression.\n"+ 
                "Parameter dividend and divider are numbers.",
                syntax("MOD( dividend, divider )"));
        
        createPageAndRegister(SQLFunctionKeywords.POWER, 
                "SQL POWER() function returns the value of a number raised to another, where both of the numbers are passed as arguments. The SQL DISTINCT command along with the SQL POWER() function can be used to retrieve only unique data depending on a specified expression.\n"+ 
                "Parameters base and exponent are numbers.",
                syntax("POWER( base, exponent )"));
        
        createPageAndRegister(SQLFunctionKeywords.SQRT, 
                "SQL SQRT() returns the square root of a given value in the argument.\n"+ 
                "Parameter expression is a numeric value or numeric data type.",
                syntax("SQRT( expression )"));
    }

    /**
     * This class is only to have on data type for syntax, so not able to mess up in parameter hell...
     * @author albert
     *
     */
    private class Syntax {
        private String text;

        Syntax(String text){
            this.text=text;
        }
        public String getText() {
            return text;
        }
    }
    
    private Syntax syntax(String syntax) {
        return new Syntax(syntax);
    }
    
    private void handleDataTypes() {
        createPageAndRegister(SQLDataTypesKeywords.NULL, "represents null value inside database");
        createPageAndRegister(SQLDataTypesKeywords.CHAR, 
                "Character string, fixed length n.",
                syntax("CHARACTER(n) | CHAR(n)"));
        createPageAndRegister(SQLDataTypesKeywords.VARCHAR, 
                "Variable length character string, maximum length n.",
                syntax("CHARACTER VARYING(n) | VARCHAR(n)"));
        createPageAndRegister(SQLDataTypesKeywords.CLOB,
                "A character large object represents a collection of character data, and is often stored internally at separate locations and referenced by the table");
        createPageAndRegister(SQLDataTypesKeywords.BOOLEAN, 
                "Stores truth values - either TRUE or FALSE.",
                syntax("BOOLEAN"));
        createPageAndRegister(SQLDataTypesKeywords.BINARY, 
                "Fixed-length binary string, maximum length n.",
                syntax("BINARY(n)"));
        createPageAndRegister(SQLDataTypesKeywords.VARBINARY, 
                "Variable length binary string, maximum length n.",
                syntax("BINARY VARYING(n) | VARBINARY(n)"));
        createPageAndRegister(SQLDataTypesKeywords.BLOB, "A type used to store a long sequence of bytes - e.g. pictures, sound, ...");

        // https://www.w3resource.com/sql/data-type.php#NUMERIC
        createPageAndRegister(SQLDataTypesKeywords.INTEGER, 
                "INTEGER(p) | INTEGER",
                syntax("Integer numerical, precision n - or per default 10."),
                numericDataTypesExample1);
        createPageAndRegister(SQLDataTypesKeywords.SMALLINT, 
                "Integer numerical normally precision 5.",
                syntax("SMALLINT"),
                numericDataTypesExample1);
        createPageAndRegister(SQLDataTypesKeywords.BIGINT, 
                "Integer numerical, normally precision 19.",
                syntax("BIGINT"),
                numericDataTypesExample1);

        createPageAndRegister(SQLDataTypesKeywords.DECIMAL,
                "Exact numerical, precision p, scale s.",
                syntax("DECIMAL(p, s)"), 
                numericDataTypesExample1);

        createPageAndRegister(SQLDataTypesKeywords.NUMERIC, 
                "Exact numerical, precision p, scale s.\n" + 
                "<i>(Same as DECIMAL )</i>.", 
                syntax("NUMERIC(p, s)"), 
                numericDataTypesExample1);
        
        createPageAndRegister(SQLDataTypesKeywords.FLOAT, 
                "Approximate numerical, mantissa precision p - often per default 16.", 
                syntax("FLOAT(p)| FLOAT"), 
                numericDataTypesExample1);
        createPageAndRegister(SQLDataTypesKeywords.REAL, 
                "Approximate numerical mantissa precision normally 7.", 
                numericDataTypesExample1);
        
        createPageAndRegister(SQLDataTypesKeywords.DOUBLE, 
                "Approximate numerical mantissa precision normally 16",
                syntax("DOUBLE PRECISION | DOUBLE"),
                numericDataTypesExample1);
        
        createPageAndRegister(SQLDataTypesKeywords.PRECISION, 
                "Approximate numerical mantissa precision normally 16",
                syntax("DOUBLE PRECISION | DOUBLE"),
                numericDataTypesExample1);
       
        createPageAndRegister(SQLDataTypesKeywords.DATE,
                "Represents a date - no time",
                syntax("DATE"), 
                dateDataTypesExample1);
        
        createPageAndRegister(SQLDataTypesKeywords.TIMESTAMP,
                "Represents date and time",
                syntax("TIMESTAMP"),
                dateDataTypesExample1
                );
        
        createPageAndRegister(SQLDataTypesKeywords.TIME,
                "Represents time without date",
                syntax("TIME"),
                dateDataTypesExample1
                );
        
        createPageAndRegister(SQLDataTypesKeywords.INTERVAL,
                "Composed of a number of integer fields, representing a period of time, depending on the type of interval.",
                syntax("INTERVAL"),
                dateDataTypesExample1
                );
        
        createPageAndRegister(SQLDataTypesKeywords.XML,
                "Stores XML data. It can be used wherever a SQL data type is allowed, such as a column of a table.",
                syntax("XML")
                );
        
        createPageAndRegister(SQLDataTypesKeywords.TEXT,
                "Is used for large pieces of string data. No limit is defined.",
                syntax("TEXT")
                );
        
        createPageAndRegister(SQLDataTypesKeywords.UUID,
                "Represents universally unique identifiers (UUIDs), see  RFC 4122 for details. An example would be 6cbf0734-24c1-36b7-bc06-43905d4538e7",
                syntax("UUID")
                );
    }

    private void handleStatements() {
        register(createSelect());

        register(createInner());
        register(createOuter());
        register(createJoin());
        register(createLeft());
        register(createRight());
        register(createFull());

        register(createUnion());

        createPageAndRegister(SQLStatementKeywords.UPDATE,
                "Once there is some data in the table, it may be required to modify the data. To do so, the SQL UPDATE command can be used. It changes the records in tables.\n" + 
                "\n" + 
                "The SQL UPDATE command changes the data which already exists in the table. Usually, it is needed to make a conditional UPDATE in order to specify which row(s) are going to be updated.\n" + 
                "\n" + 
                "The WHERE clause is used to make the update restricted and the updating can happen only on the specified rows.\n" + 
                "\n" + 
                "Without using any WHERE clause (or without making any restriction) the SQL UPDATE command can change all the records for the specific columns of a table. ",
                
                syntax("UPDATE < table name > SET<column1>=<value1>,<column2>=<value2>,.....\n" + 
                "WHERE <condition>;\n"
                + "<ul>"
                + "<li>table_name - Name of the table to be updated.</li>"
                + "<li>column1,column2 - Name of the columns of the table.</li>"
                + "<li>value1,value2 - New values.</li>"
                + "<li>condition - Condition(s) using various functions and operators.</li>"
                + "</ul>"),
                sqlUpdateExample1,sqlUpdateExample2);   
        

        createPageAndRegister(SQLStatementKeywords.INSERT,
                "The SQL INSERT statement is used to insert a single record or multiple records into a table.\n" + 
                "\n" + 
                "While inserting a row, if the columns are not specified, it means that vales are added for all of the columns of the table resulting addition of a single row. If it is required to insert values for one or more specific column(s), then it is necessary to specify the name(s) of the column(s) in the SQL query.\n" + 
                "\n" + 
                "The SQL SELECT statement can also be used to insert the rows of one table into another identical table.\n" + 
                "\n" + 
                "While inserting the values, it is needed to enclose the values with single quotes for character or date values.",
                
                syntax("INSERT INTO < table name > (col1,col2,col3...col n)\n" + 
                        "VALUES (value1,value2,value3…value n);\n"
                + "<ul>"
                + "<li>table_name - Name of the table where data will be inserted.</li>"
                + "<li>col1,col2,col3,col n - Column of the table.</li>"
                + "<li>value1,value2,value3,value n - Values against each column.</li>"
                + "</ul>You can also use another syntax to insert data. Here, you don't specify column names of the associated table. So, value1 is inserted into the first column of a table, value2 into column2 and so on.\n" + 
                "\n" + 
                "INSERT INTO < table name >\n" + 
                "VALUES (value1,value2,value3…value n);"),
                sqlInsertExample1,sqlInsertExample2);   
        
        createPageAndRegister(SQLStatementKeywords.DELETE,
                "The SQL DELETE command is used to delete rows or records from a table.",
                
                syntax("DELETE FROM { table_name | ONLY (table_name) }\n" + 
                "[{ WHERE search_condition | WHERE CURRENT OF cursor_name }]\n"
                + "<ul>"
                + "<li>table_name - Name of the table to where rows shall be removed.</li>"
                + "<li>search_condition - specify a search condition (logical expression) that has one or more conditions.</li>"
                + "</ul>"),
                sqlDeleteExample1);   
    }

    /*
     * ++++++++++++++++++++++++++++++++++++++ 
     *      data types
     * ++++++++++++++++++++++++++++++++++++++
     * 
     */
    private void createPageAndRegister(SQLKeyword keyword, String description, Example... examples) {
        createPageAndRegister(keyword, description, null, examples);
    }

    private void createPageAndRegister(SQLKeyword keyword, String description, Syntax syntax, Example... examples) {
        Page page = new Page(keyword);
        page.description = description;
        page.syntax = (syntax!=null ? syntax.getText():null);

        for (Example example : examples) {
            page.examples.add(example);
        }
        register(page);
    }

    /*
     * ++++++++++++++++++++++++++++++++++++++ 
     *     info pages
     * ++++++++++++++++++++++++++++++++++++++
     * 
     */
    private Page createUnion() {
        Page page = new Page(SQLStatementKeywords.UNION);
        page.description=
                "The SQL UNION operator combines the results of two or more queries "
                + "and makes a result set which includes fetched rows from the participating queries in the UNION.\n\n"
                + "<h5>Basic rules for combining two or more queries using UNION</h5>" + 
                "1.) number of columns and order of columns of all queries must be same.\n" + 
                "2.) the data types of the columns on involving table in each query must be same or compatible.\n" + 
                "3.) Usually returned column names are taken from the first query.\n" + 
                "\n" + 
                "By default the UNION behaves like UNION [DISTINCT] , i.e. eliminated the duplicate rows; however, using ALL keyword with UNION returns all rows, including duplicates.\n" + 
                "<h5>Difference between SQL JOIN and UNION</h5>" + 
                "1.) The columns of joining tables may be different in JOIN but in UNION the number of columns and order of columns of all queries must be same.\n" + 
                "2.) The UNION puts rows from queries after each other( puts vertically ) but JOIN puts the column from queries after each other (puts horizontally), i.e. it makes a cartesian product.\n\n"
                + "The queries are all executed independently but their output is merged. ";
        page.syntax="SELECT <column_list>t [INTO ]\n" + 
                "[FROM ]     [WHERE ]\n" + 
                "[GROUP BY ]     [HAVING ]\n" + 
                "[UNION [ALL]\n" + 
                "SELECT <column_list>\n" + 
                "[FROM ]     [WHERE ]\n" + 
                "[GROUP BY ]     [HAVING ]...]\n" + 
                "[ORDER BY ]";
        
        page.examples.add(unionExample1);
        return page;
    }
    
    private Page createJoin() {
        Page page = new Page(SQLStatementKeywords.JOIN);
        page.description="An SQL JOIN clause combines rows from two or more tables. It creates a set of rows in a temporary table. "+
        "<h4>How to Join two tables in SQL?</h4>" + 
        "A JOIN works on two or more tables if they have at least one common field and have a relationship between them.\n" + 
        "\n" + 
        "JOIN keeps the base tables (structure and data) unchanged.\n" + 
        "<h4>Join vs. Subquery</h4>" + 
        "<ol>" + 
        "    <li>JOINs are faster than a subquery and it is very rare that the opposite.</li>" + 
        "    <li>In JOINs the RDBMS calculates an execution plan, that can predict, what data should be loaded and how much it will take to processed and as a result this process save some times, unlike the subquery there is no pre-process calculation and run all the queries and load all their data to do the processing.</li>" + 
        "    <li>A JOIN is checked conditions first and then put it into table and displays; where as a subquery take separate temp table internally and checking condition.</li>" + 
        "    <li>When joins are using, there should be connection between two or more than two tables and each table has a relation with other while subquery means query inside another query, has no need to relation, it works on columns and conditions.</li>"
        + "</ol>" ; 
        return page;
    }
    
    private Page createFull() {
        Page page = new Page(SQLStatementKeywords.FULL);
        page.description="In SQL the FULL OUTER JOIN combines the results of both left and right outer joins and returns all (matched or unmatched) rows from the tables on both sides of the join clause.";
        page.syntax="SELECT * \n" + 
                "FROM table1 \n" + 
                "FULL OUTER JOIN table2 \n" + 
                "ON table1.column_name=table2.column_name;";
        page.examples.add(fullJoinExample1);
        return page;
    }
    
    private Page createInner() {
        Page page = new Page(SQLStatementKeywords.INNER);
        page.description="The INNER JOIN selects all rows from both participating tables as long as there is a match between the columns. An SQL INNER JOIN is same as JOIN clause, combining rows from two or more tables. ";
        page.examples.add(innerJoinExample1);
        page.syntax="SELECT * \n" + 
                "FROM table1 INNER JOIN table2 \n" + 
                "ON table1.column_name = table2.column_name; ";
        return page;
    }
    
    private Page createOuter() {
        Page page = new Page(SQLStatementKeywords.OUTER);
        page.description="The SQL OUTER JOIN returns all rows from both the participating tables which satisfy the join condition along with rows which do not satisfy the join condition. The SQL OUTER JOIN operator (+) is used only on one side of the join condition only.\n" + 
                "\n" + 
                "The subtypes of SQL OUTER JOIN\n" + 
                "\n" + 
                "-  LEFT OUTER JOIN or LEFT JOIN\n" + 
                "-  RIGHT OUTER JOIN or RIGHT JOIN\n" + 
                "-  FULL OUTER JOIN\n" + 
                "";
        page.syntax="Select * \n" + 
                "FROM table1, table2 \n" + 
                "WHERE conditions [+];";
        page.examples.add(innerJoinExample1);
        return page;
    }

    private Page createLeft() {
        Page page = new Page(SQLStatementKeywords.LEFT);
        page.description="The SQL LEFT JOIN (specified with the keywords LEFT JOIN and ON) joins two tables and fetches all matching rows of two tables for which the SQL-expression is true, plus rows from the frist table that do not match any row in the second table.";
        page.syntax="SELECT *\n" + 
                "FROM table1\n" + 
                "LEFT [ OUTER ] JOIN table2\n" + 
                "ON table1.column_name=table2.column_name;";
        page.examples.add(leftJoinExample1);
        return page;
    }
    
    private Page createRight() {
        Page page = new Page(SQLStatementKeywords.RIGHT);
        page.description="The SQL RIGHT JOIN, joins two tables and fetches rows based on a condition, which is matching in both the tables ( before and after the JOIN clause mentioned in the syntax below) , and the unmatched rows will also be available from the table written after the JOIN clause ( mentioned in the syntax below ).";
        page.syntax="SELECT *\n" + 
                "FROM table1\n" + 
                "RIGHT [ OUTER ] JOIN table2\n" + 
                "ON table1.column_name=table2.column_name;";
        page.examples.add(rightJoinExample1);
        return page;
    }

    
    private Page createSelect() {
        Page page = new Page(SQLStatementKeywords.SELECT);
        page.syntax = 
                "SELECT [DISTINCT] column_list FROM table-name\n" + 
                " [0..n JOIN Clause(s)]\n" + 
                " [WHERE Clause]\n" + 
                " [GROUP BY clause]\n" + 
                " [HAVING clause]\n" + 
                " [ORDER BY clause];";
        
        page.description = "SQL Select statement tells the database to fetch information from a table.\n" + 
                "\n" + 
                "A query or SELECT statement is a command which gives instructions to a database to produce certain information(s) from the table in its memory.\n" + 
                "\n" + 
                "The SELECT command starts with the keyword SELECT followed by a space and a list of comma separated columns. A * character can be used to select all the columns of a table.\n" + 
                "\n" + 
                "The table name comes after the FROM keyword and a white-space.\n\n"
                
                + "<h5>SQL SELECT statement with NULL values</h5>" + 
                "Before storing a value in any field of a table, a NULL value can be stored; later that NULL value can be replaced with the desired value. When a field value is NULL it means that the database assigned nothing (not even a zero \"0\" or blank \" \" ), in that field for that row.\n" + 
                "\n" + 
                "Since the NULL represents an unknown or inapplicable value, it can’t be compared using the AND / OR logical operators. The special operator ‘IS’ is used with the keyword ‘NULL’ to locate ‘NULL’ values. NULL can be assigned to both types of fields i.e. numeric or character type.";
        page.examples.add(innerJoinExample1);
        page.examples.add(leftJoinExample1);
        page.examples.add(rightJoinExample1);
        page.examples.add(rightJoinExample1);

        return page;
    }

    /*
     * ++++++++++++++++++++++++++++++++++++++
     *             Examples
     * ++++++++++++++++++++++++++++++++++++++
     * 
     */
    private Example createUnionExample1() {
        Example example= new Example("union",
                "SELECT c.name, c.address\n" + 
                " FROM customers c\n" + 
                "UNION\n" + 
                "SELECT o.amount, o.item\n" + 
                " FROM orders o\n"); 
        
        example.description="Here we fetch customer names and addresses in combination with amount and item names from orders.";
        
        return example;
    }
    private Example createInnerJoinExample1() {
        Example example= new Example("inner join",
                "SELECT c.name, c.address, o.amount, o.item, o.delivery\n" + 
                " FROM orders o\n" + 
                " INNER JOIN customer c\n" + 
                " ON c.customer_id = o.customer_id\n" +
                "WHERE c.customer_id=4711");
        
        example.description="Here we fetch all orders for a customer with given id 4711 and show it in a human readable way. The relation MUST exist, otherwise result will be empty.";
        
        return example;
    }
    
    private Example createRightJoinExample1() {
        Example example= new Example("right join",
                "SELECT c.name, c.address, o.amount, o.item, o.delivery\n" + 
                " FROM orders o\n" + 
                " RIGHT JOIN customer c\n" + 
                " ON c.customer_id = o.customer_id\n" 
                );
        
        example.description="Here we just fetch all customer data (right table) - even when there are no orders (left table) related to customers";
        
        return example;

    }
    
    private Example createDateDataTypesExample1() {
        Example example= new Example("Date data types",
                "CREATE TABLE date_test_table (   \n" + 
                " id    DECIMAL PRIMARY KEY,   \n" + 
                " ex_date  DATE,          -- just a date, no time inside\n" + 
                " ex_tstp  TIMESTAMP,     -- date and time' \n" + 
                " ex_tstp  TIME,          -- time but no date' \n" + 
                " ex_tstp  INTERVAL       -- interval between to dates/times' \n" + 
                ");"
                ); 
        
        example.description="The example defines a test table having some columns with date data inside.";
        
        return example;
    }
    
    
    private Example createInsertExample1() {
        Example example= new Example("Insert",
                "INSERT INTO agents\n" + 
                " (id,name,location,data)\n"+
                " VALUES (\"A001\",\"Jodi\",\"London\",\"075-1248798\");"
                ); 
        
        example.description="The example adds values'A001','Jodi','London','075-1248798' for a single row into the table 'agents'";
        return example;
    }
    private Example createInsertExample2() {
        Example example= new Example("Insert (no column names given)",
                "INSERT INTO agents\n" + 
                        "VALUES (\"A001\",\"Jodi\",\"London\",.12,\"075-1248798\");"
                ); 
        
        example.description="The example adds values'A001','Jodi','London','.12','075-1248798' for a single row into the table 'agents'.";
        return example;
    }
    
    private Example createDeleteExample1() {

        Example example= new Example("Delete",
                "DELETE FROM customer1\n" + 
                "    WHERE cust_acive=0;"
                ); 
        
        example.description="The example will delete all entries were customer is no longer active.";
        
        return example;
    }
    
    private Example createUpdateExample1() {
        Example example= new Example("Simple update",
                "UPDATE neworder\n" + 
                "SET ord_description='TBD';"
                ); 
        
        example.description="The example will sets every order description to TBD.";
        
        return example;
    }


    
    private Example createUpdateExample2() {
        Example example= new Example("Update with sub select",
                "UPDATE agent1\n" + 
                "SET commission=commission+.02\n" + 
                "WHERE 2>=(\n" + 
                "  SELECT COUNT(cust_code) FROM customer\n" + 
                "  WHERE customer.agent_code=agent1.agent_code\n" +
                ");"
                ); 
        
        example.description="The example uses a sub select to determine data used for WHERE condition of update";
        
        return example;
    }

    
    private Example createSQLTranslateExample1() {
        Example example = new Example("Translate",
                "SELECT TRANSLATE ( 'this is my string', \n" + 
                " 'abcdefghijklmnopqrstuvwxyz', \n" + 
                " 'defghijklmnopqrstuvwxyzabc' ) \n" + 
                " encode_string \n" + 
                "FROM dual ;\n" + 
                "");
        example.description="The next example can be used to get the string where all "
                + "occurrences of 'abcdefghijklmnopqrstuvwxyz' will be replaced with corresponding characters in the string 'defghijklmnopqrstuvwxyzabc'";
        return example;
    }
    
    private Example createNumericDataTypesExample1() {
        Example example= new Example("Numeric data types",
                "CREATE TABLE numeric_test_table (   \n" + 
                " id    DECIMAL PRIMARY KEY,   \n" + 
                " col0  VARCHAR(100),   -- up to 100 characters \n" + 
                " col1  DECIMAL(5,2),   -- three digits before the decimal and two behind   \n" + 
                " col2  SMALLINT,       -- no decimal point   \n" + 
                " col3  INTEGER,        -- no decimal point   \n" + 
                " col4  BIGINT,         -- no decimal point. \n" + 
                " col5  FLOAT(2),       -- two or more digits after the decimal place   \n" + 
                " col6  REAL,   \n" + 
                " col7  DOUBLE PRECISION\n" + 
                ");"); 
        
        example.description="The example defines a test table having some columns with numeric data inside";
        
        return example;
    }

    
    private Example createFullJoinExample1() {
        Example example= new Example("full join",
                "SELECT c.name, c.address, o.amount, o.item, o.delivery\n" + 
                " FROM orders o\n" + 
                " FULL JOIN customer c\n" + 
                " ON c.customer_id = o.customer_id\n" 
                );
        
        example.description="Here we just fetch all customer data (right table) - even when there are no orders (left table) related to customers";
        
        return example;

    }

    private Example createLeftJoinExample1() {
        Example example= new Example("left join",
                "SELECT c.name, c.address, o.amount, o.item, o.delivery\n" + 
                " FROM orders o\n" + 
                " LEFT JOIN customer c\n" + 
                " ON c.customer_id = o.customer_id\n");
        
        example.description="Here we just fetch all order data (left table), even when no customer data can be joined";
        
        return example;

    }

    class Example{
        String name;
        String sql;
        String description;
        private Example(String name, String sql) {
            this.name=name;
            this.sql=sql;
        }
    }
    
    class Page {
        
        Page(SQLKeyword mainKeyword){
            this.mainKeyword=mainKeyword;
        }
        
        private SQLKeyword mainKeyword;
        private List<Example> examples = new ArrayList<>();
        private String description;
        private String syntax;
        
        public String getSyntax() {
            return syntax;
        }
        public String getDescription() {
            return description;
        }
        public List<Example> getExamples() {
            return examples;
        }
        
        public SQLKeyword getMainKeyword() {
            return mainKeyword;
        }
        
    }
    
    /* @formatter:on */
}
