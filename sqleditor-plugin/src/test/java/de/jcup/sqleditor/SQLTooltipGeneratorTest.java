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
package de.jcup.sqleditor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.jcup.sqleditor.document.keywords.SQLDataTypesKeywords;
import de.jcup.sqleditor.document.keywords.SQLFunctionKeywords;
import de.jcup.sqleditor.document.keywords.SQLKeyword;
import de.jcup.sqleditor.document.keywords.SQLSchemaKeywords;
import de.jcup.sqleditor.document.keywords.SQLStatementKeywords;
import de.jcup.sqleditor.document.keywords.SQLStatementTargetKeyWords;
import de.jcup.sqleditor.document.keywords.SQLWhereBlockKeyWords;

public class SQLTooltipGeneratorTest {
    
    private static final float MININUM_ACCEPTED_PERCENTAGE_FOR_TOOLTIP_DESCRIPTIONS= 80f;
    
    @Test
    public void smoke_test_with_output_about_missing_descriptions_but_at_least_mini_percent_must_be_described() {
        SQLTooltipGenerator generator = new SQLTooltipGenerator(new SQLTooltipModel());
        List<SQLKeyword> notDescribed= new ArrayList<>();
        
        List<SQLKeyword> keywordsToInspect = new ArrayList<>();
        
        keywordsToInspect.addAll(Arrays.asList(SQLDataTypesKeywords.values()));
        keywordsToInspect.addAll(Arrays.asList(SQLFunctionKeywords.values()));
        keywordsToInspect.addAll(Arrays.asList(SQLSchemaKeywords.values()));
        keywordsToInspect.addAll(Arrays.asList(SQLStatementKeywords.values()));
        keywordsToInspect.addAll(Arrays.asList(SQLStatementTargetKeyWords.values()));
        keywordsToInspect.addAll(Arrays.asList(SQLWhereBlockKeyWords.values()));

        System.out.println("-------------------------------------------------------------------");
        System.out.println("----- Described:");
        System.out.println("-------------------------------------------------------------------");
        System.out.println();
        int max = keywordsToInspect.size();
        int described = 0;
        for (SQLKeyword keyword: keywordsToInspect) {
            
            String description = generator.createDescription(keyword);
            if (description==null) {
                notDescribed.add(keyword);
                continue;
            }
            described++;
            System.out.println("-------------------------------------------------------------------");
            System.out.println(keyword.getClass().getSimpleName()+": "+keyword.getText());
            System.out.println("-------------------------------------------------------------------");
            System.out.println(description);
            System.out.println();
        }
        
        System.out.println("-------------------------------------------------------------------");
        System.out.println("----- Not described:");
        System.out.println("-------------------------------------------------------------------");
        for (SQLKeyword keyword: notDescribed) {
            System.out.println(keyword.getClass().getSimpleName()+": "+keyword.getText());
        }
        
        float onePercent = max/100;
        float takenPercent = described/onePercent;
        System.out.println("Percent taken: "+takenPercent+" %");
        assertTrue("Percent taken percentage: "+takenPercent+" is not exceeding minimum: "+MININUM_ACCEPTED_PERCENTAGE_FOR_TOOLTIP_DESCRIPTIONS, takenPercent>MININUM_ACCEPTED_PERCENTAGE_FOR_TOOLTIP_DESCRIPTIONS);
    }
    

}
