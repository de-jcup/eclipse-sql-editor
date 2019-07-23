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
package de.jcup.sqleditor.script.formatter.token;

import de.jcup.sqleditor.document.keywords.SQLKeyword;
import de.jcup.sqleditor.document.keywords.SQLStatementKeywords;
import de.jcup.sqleditor.script.formatter.SQLFormatConfig;
import de.jcup.sqleditor.script.parser.ParseToken;

class TokenBasedSQLFormatContext {
    public ParseTokenRunner tokenRunner;
    public boolean createBlockInside;
    boolean selectBlockActive;
    boolean statementIndentActive;
    SQLFormatConfig config;
    StringBuilder sb = new StringBuilder();
    boolean selectProjectionBlockHandled;
    boolean createBlock;
    String tokenText;
    private ParseToken nextLastToken;
    ParseToken token;
    ParseToken lastToken;
    private int indent;

    public void reset() {
        selectBlockActive = false;
        selectProjectionBlockHandled = false;
        statementIndentActive = false;
        createBlock = false;
    }

    public int getIndent() {
        return indent;
    }

    public void indentRight() {
        indent++;
    }

    public void indentLeft() {
        if (indent == 0) {
            return;
        }
        indent--;

    }

    public boolean visitNextToken(ParseToken token) {
        this.lastToken = nextLastToken;
        this.token=token;
        this.nextLastToken = token;
        String tokenText = token.getText();
        if (tokenText == null) {
            return false;
        }
        this.tokenText = tokenText;
        if (SQLKeyword.isIdentifiedBy(tokenText, SQLStatementKeywords.CREATE)) {
            createBlock = true;
        }
        if (SQLKeyword.isIdentifiedBy(tokenText, SQLStatementKeywords.SELECT)) {
            enterSelectBlock();
        } else if (SQLKeyword.isIdentifiedBy(tokenText, SQLStatementKeywords.FROM)) {
            leaveSelectBlockByFrom();
        }
        return true;

    }

    private void enterSelectBlock() {
        indentRight();
        selectBlockActive = true;
    }

    private void leaveSelectBlockByFrom() {
        selectBlockActive = false;
        selectProjectionBlockHandled = false;
        indentLeft();
    }
    
    @Override
    public String toString() {
        return sb.toString();
    }

}