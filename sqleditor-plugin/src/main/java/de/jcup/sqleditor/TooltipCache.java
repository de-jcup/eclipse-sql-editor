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
