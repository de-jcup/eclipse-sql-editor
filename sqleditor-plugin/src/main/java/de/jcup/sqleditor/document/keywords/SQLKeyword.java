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
package de.jcup.sqleditor.document.keywords;

import de.jcup.eclipse.commons.keyword.DocumentKeyWord;

public interface SQLKeyword extends DocumentKeyWord{

    
    public boolean isHavingParameters();

    public default boolean isCommaPostFixAllowed() {
        return false;
    }
    public static boolean isIdentifiedBy(String text, SQLKeyword ... keywords) {
        if (text==null) {
            return false;
        }
        if (keywords==null || keywords.length==0) {
            return false;
        }
        String textUpper = text.toUpperCase();
        for (SQLKeyword keyword : keywords) {
            if (textUpper.equalsIgnoreCase(keyword.getText())){
                return true;
            }
        }
        return false;
    }
}