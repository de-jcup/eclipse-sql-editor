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

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

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
    public void test_all_scripts_inside_formater_folder_from_origin_to_formatted() throws Exception{
        
        List<String> originFileNames = TestScriptLoader.fetchAllTestScriptNames("formatter/origin");
        for (String name: new TreeSet<>(originFileNames)) {
            testSingleOne(name);
        }
        
    }
    
    @Test
    public void test_exact_one_script() throws Exception{
        
       testSingleOne("001-simple-create.sql");
        
    }
    

    private void testSingleOne(String name) throws IOException, SQLFormatException {
        String original= read("origin/"+name);
        
        String expected = read("expected-after-formatter/"+name);
        
        /* execute*/
        String formatted = formatterToTest.format(original, SQLFormatConfig.DEFAULT);
        
        /* test*/
        if (expected.contentEquals(formatted)) {
            return;
        }
        System.out.println("Formatted:\n"+formatted);
        
        assertEquals("File:"+name+" problem", expected, formatted);
    }

    private String read(String path) throws IOException {
        return TestScriptLoader.loadScriptFromTestScripts("formatter/"+path);
    }
    
}
