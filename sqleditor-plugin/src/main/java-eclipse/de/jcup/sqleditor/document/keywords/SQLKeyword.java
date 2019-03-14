package de.jcup.sqleditor.document.keywords;

import de.jcup.eclipse.commons.keyword.DocumentKeyWord;

public interface SQLKeyword extends DocumentKeyWord{

    
    public boolean isHavingParameters();

    public default boolean isCommaPostFixAllowed() {
        return false;
    }
}
