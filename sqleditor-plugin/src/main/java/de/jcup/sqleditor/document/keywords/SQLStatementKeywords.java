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
public enum SQLStatementKeywords implements SQLKeyword {
    
    ALTER,
    
    BLOCK,
    
    COMMENT,
    
    CREATE,
    
    DELETE,
    
    DROP,
    
    EXECUTE,
    
    INSERT,
    
    MERGE,
    
    REPLACE,
    
    SELECT,
    
    DISTINCT,
    
    FROM,
    
    SET,
    
    SHOW,
    
    TRUNCATE,
    
    UPDATE,
    
    VALUES,
    
    UPSERT,
    
    WHERE,
    
    ADD,
    
    AND,
    
    OR,
    
    JOIN,
    
    INNER,
    
    OUTER,
    
    LEFT,
    
    ON,
    
    AS,
    
    /* transaction aprts*/
    COMMIT,
    
    
    ROLBACK,
    
    
    BEGIN,
    
    WORK,
    
    
    START,
    
    SAVE,
    
    TRANSACTION,
    
    SAVEPOINT,
    

    /* operators - currently also managed here*/
    
    IF,
    
    WHEN,
    
    ELSE,
    

    NULLIF,
    
    CASE,
    
    END, // added, necessary e.g for CASE
    
    NOT,
    
    
    LIKE,
    
    IS,
    
    TRUE,
    
    FALSE,
    
    IN,
    
    EXISTS,
    
    THEN,
    
    BETWEEN,
    
	;

	private String text;
    private String linkToDocumentation;

	SQLStatementKeywords() {
		this(null, null);
	}

	SQLStatementKeywords(String linkToDocumentation) {
		this(null, linkToDocumentation);
	}

	SQLStatementKeywords(String text, String linkToDocumentation) {
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
        return false;
    }
   
}
