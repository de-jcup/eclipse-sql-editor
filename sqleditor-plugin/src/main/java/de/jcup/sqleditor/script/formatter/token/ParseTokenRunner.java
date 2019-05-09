package de.jcup.sqleditor.script.formatter.token;

import java.util.List;

import de.jcup.sqleditor.script.parser.ParseToken;

public class ParseTokenRunner {

    private List<ParseToken> tokens;
    private int tokenNumber;

    public ParseTokenRunner(List<ParseToken> tokens) {
        this.tokens=tokens;
        this.tokenNumber=0;
    }
    
    public void forward() {
        tokenNumber++;
    }
    
    public boolean canForward() {
        return canGotoTokenNumber(tokenNumber+1);
    }
    
    public boolean canGotoTokenNumber(int tokenNumber) {
        return tokens.size()>tokenNumber && tokenNumber>=0;
    }
    
    public void gotoTokenNumber(int tokenNumber) {
        this.tokenNumber=tokenNumber;
    }
    
    public int getTokenNumber() {
        return tokenNumber;
    }
    
    public boolean hasToken() {
        return tokens.size()>tokenNumber;
    }
    public ParseToken getToken() {
        ParseToken token = tokens.get(tokenNumber);
        return token;
    }

    
}
