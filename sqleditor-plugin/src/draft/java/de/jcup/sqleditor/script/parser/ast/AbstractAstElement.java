package de.jcup.sqleditor.script.parser.ast;

import de.jcup.sqleditor.script.parser.ParseToken;

public abstract class AbstractAstElement implements AstElement{

    private ParseToken token;

    public AbstractAstElement(ParseToken token) {
        this.token=token;
    }
    
    public ParseToken getParseToken() {
        return token;
    }
}
