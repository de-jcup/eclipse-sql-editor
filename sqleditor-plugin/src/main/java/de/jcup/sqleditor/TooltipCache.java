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

import java.util.HashMap;
import java.util.Map;

import de.jcup.sqleditor.document.keywords.SQLKeyword;

public class TooltipCache {
    
    // only used for development - means absolute no caching, but always recreate all objects again. 
    private static boolean developmentMode = Boolean.getBoolean("de.jcup.sqleditor.tooltipcache.developmentmode"); 
    
    public static TooltipCache INSTANCE = new TooltipCache();
    
    SQLTooltipGenerator generator;
    private Map<SQLKeyword,String> cache = new HashMap<>();
    
    public TooltipCache() {
        init();
    }

    public String getTooltip(SQLKeyword keyword) {
        if (developmentMode) {
            init();
            return createEntry(keyword);
        }
        return cache.computeIfAbsent(keyword, this::createEntry);
    }
    
    private void init() {
        generator = new SQLTooltipGenerator(new SQLTooltipModel());
    }
    
    
    private String createEntry(SQLKeyword keyword) {
        String description = generator.createDescription(keyword);
        if (description==null) {
            return "";
        }
        return description;
    }


}
