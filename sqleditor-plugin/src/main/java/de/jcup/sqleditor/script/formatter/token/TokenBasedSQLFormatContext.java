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