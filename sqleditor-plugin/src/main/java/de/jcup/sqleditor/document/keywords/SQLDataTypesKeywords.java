/*
 * Copyright 2019 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an"AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
package de.jcup.sqleditor.document.keywords;

import de.jcup.eclipse.commons.keyword.TooltipTextSupport;

//see https://en.wikibooks.org/wiki/Windows_SQL_Scripting
//https://en.wikibooks.org/wiki/Structured_Query_Language/Data_Types
public enum SQLDataTypesKeywords implements SQLKeyword {
    
    NULL(false),
    
    /* Character Types */
    CHAR(true),
    
    VARCHAR(true),
    
    CLOB(false),
    
    /* Binary Types */
    BINARY(false),
    
    VARBINARY(false),
    
    BLOB(false),
    
    /* Numeric Types - exact */
    NUMERIC(false),

    DECIMAL(false),
    
    SMALLINT(false),
       
    INTEGER(false),
    
    BIGINT(false),
    
    /* Numeric Types - approximate */
    FLOAT(false), 

    REAL(false), 
    
    DOUBLE(false), PRECISION(false),
    
    /* datetime */
    DATE(false), 
    
    TIME(false), 
    
    TIMESTAMP(false),
    
    
    /* OTHER */
    INTERVAL(false),
    
    BOOLEAN(false),
    
    XML(false),
    
    TEXT(false),
    
    UUID(false),
    
	;

	private String text;
    private String linkToDocumentation;
    private boolean needsParameters;

	SQLDataTypesKeywords(boolean needsParameters) {
		this(needsParameters, null, null);
	}

	SQLDataTypesKeywords(boolean needsParameters, String linkToDocumentation) {
		this(needsParameters, null, linkToDocumentation);
	}

	SQLDataTypesKeywords(boolean needsParameters, String text, String linkToDocumentation) {
		this.needsParameters=needsParameters;
	    this.text = text;
		this.linkToDocumentation=linkToDocumentation;
	}

	@Override
	public String getText() {
		if (text == null) {
			text = name().toLowerCase();
		}
		return text;
	}

	@Override
	public boolean isBreakingOnEof() {
		return true;
	}

    @Override
    public String getLinkToDocumentation() {
        if (linkToDocumentation!=null) {
            return linkToDocumentation;
        }
        return "https://en.wikipedia.org/wiki/SQL_syntax";
    }

    @Override
    public String getTooltip() {
        return TooltipTextSupport.getTooltipText(name().toLowerCase());
    }

    @Override
    public boolean isHavingParameters() {
        return needsParameters;
    }
    
    @Override
    public boolean isCommaPostFixAllowed() {
        return true;
    }
}
