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

import static de.jcup.sqleditor.script.parser.AssertParseTokens.assertThat;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TokenParserTest {
    private TokenParser parserToTest;

    @Before
    public void before() {
        parserToTest = new TokenParser();
    }
    @Test
    public void a_comment_has_only_its_text_as_text() throws Exception {
        /* @formatter:off*/
        List<ParseToken> result = parserToTest.parse("a -- bla1\nb -- bla bla");
        Iterator<ParseToken> iterator = result.iterator();
        iterator.next();
        ParseToken second = iterator.next();
        assertEquals("-- bla1",second.getText());
        /* @formatter:on*/
    }

    @Test
    public void a_comment_ends_with_line_break() throws Exception {
        /* @formatter:off*/
        assertParsing("a -- bla bla\nb -- bla bla").
        resultsIn("a","-- bla bla","b","-- bla bla");
        /* @formatter:on*/
    }

    @Test
    public void a_comment_ends_with_line_break_2() throws Exception {
        /* @formatter:off*/
        assertParsing(" project_name varchar(60) not null, -- we accept 60 (3x20)\n" + 
                      " project_description varchar(512), -- description fields always 512 chars\n").
        resultsIn("project_name","varchar","(","60",")","not","null",",","-- we accept 60 (3x20)","project_description","varchar","(","512",")",",","-- description fields always 512 chars");
        /* @formatter:on*/
    }

    @Test
    public void select_count_parsing() throws Exception {
        /* @formatter:off*/
        assertParsing("select count(*)/2 from emp").
        resultsIn("select","count","(","*",")","/2","from","emp");
        /* @formatter:on*/
    }

    @Test
    public void bracket_space_select_star_table1() throws Exception {
        assertParsing("( select * from table1").resultsIn("(", "select", "*", "from", "table1");
    }

    @Test
    public void bracket_select_star_table1() throws Exception {
        assertParsing("(select * from table1").resultsIn("(", "select", "*", "from", "table1");
    }

    @Test
    public void bracket_select_star_table1_bracket() throws Exception {
        assertParsing("(select * from table1)").resultsIn("(", "select", "*", "from", "table1", ")");
    }

    @Test
    public void bracket_select_star_table1_space_bracket() throws Exception {
        assertParsing("( select * from table1 )").resultsIn("(", "select", "*", "from", "table1", ")");
    }

    @Test
    public void select_star_table1() throws Exception {
        assertParsing("select * from table1").resultsIn("select", "*", "from", "table1");
    }

    @Test
    public void select_star_table1_newline_where_value1_is_zero() throws Exception {
        assertParsing("select * from table1\n   WHERE value1=0").resultsIn("select", "*", "from", "table1", "WHERE", "value1=", "0");
    }

    @Test
    public void select_star_table1_seperator_select_start_table2() throws Exception {
        assertParsing("select * from table1;select * from table2").resultsIn("select", "*", "from", "table1", ";", "select", "*", "from", "table2");
    }

    @Test
    public void alter_table_more_complex() throws Exception {
        assertParsing("ALTER TABLE adm_project_whitelist_uri ADD   CONSTRAINT c04_adm_projectwhitelist_projectname FOREIGN KEY (project_project_name) REFERENCES adm_project (project_name);")
                .resultsIn("ALTER", "TABLE", "adm_project_whitelist_uri", "ADD", "CONSTRAINT", "c04_adm_projectwhitelist_projectname", "FOREIGN", "KEY", "(", "project_project_name", ")", "REFERENCES",
                        "adm_project", "(", "project_name", ")", ";");
    }

    @Test
    public void a_comment_is_one_token() throws Exception {
        List<ParseToken> tokens = parserToTest.parse("-- i am a comment");

        assertThat(tokens).containsTokens("-- i am a comment");
        assertTrue(tokens.get(0).isComment());
    }

    @Test
    public void a_string_with_comment_inside_is_only_a_string() throws Exception {
        List<ParseToken> tokens = parserToTest.parse("'-- i am not a comment'");

        assertThat(tokens).containsTokens("'-- i am not a comment'");
        assertFalse(tokens.get(0).isComment());
        assertTrue(tokens.get(0).isSingleString());
    }

    /* -------------------------------------------------------------------- */
    /* --------------------------- Helpers -------------------------------- */
    /* -------------------------------------------------------------------- */
    private class AssertTokenParser {
        private String code;

        public AssertTokenParser(String code) {
            this.code = code;
        }

        public void resultsIn(String... expectedTokens) throws TokenParserException {
            List<ParseToken> tokens = parserToTest.parse(code);

            assertThat(tokens).containsTokens(expectedTokens);
        }

        public void simplyDoesNotFail() throws TokenParserException {
            parserToTest.parse(code);
        }

    }

    private AssertTokenParser assertParsing(String code) {
        return new AssertTokenParser(code);
    }

}
