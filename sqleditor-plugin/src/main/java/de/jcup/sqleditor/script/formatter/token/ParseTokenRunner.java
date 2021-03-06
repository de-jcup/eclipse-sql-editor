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
