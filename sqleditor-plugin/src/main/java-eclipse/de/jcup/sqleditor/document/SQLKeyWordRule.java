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
package de.jcup.sqleditor.document;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import de.jcup.sqleditor.document.keywords.SQLKeyword;

public class SQLKeyWordRule implements IPredicateRule {

    private IToken successToken;
    private SQLKeyword[] keywords;

    public SQLKeyWordRule(IToken successToken, SQLKeyword... keywords) {
        this.successToken = successToken;
        this.keywords = keywords;
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner) {
        return evaluate(scanner, false);
    }

    @Override
    public IToken getSuccessToken() {
        return successToken;
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner, boolean resume) {
        int column = scanner.getColumn();
        if (column>0) {
            /* we must ensure that we are NOT inside another word - which coul contain keyword parts*/
            scanner.unread();
            int before = scanner.read();
            if (! Character.isWhitespace(before)) {
                return Token.UNDEFINED;
            }
        }
        
        int c = scanner.read();
        StringBuilder sb = new StringBuilder();

        int count = 1; // first read already done
        boolean couldBeKeyword = isPartOfKeyWord(c);
        if (!couldBeKeyword) {
            return resetScannerAndReturnUndefined(scanner, count);
        }
        while (couldBeKeyword) {
            sb.append((char) c);
            c = scanner.read();
            count++;
            couldBeKeyword = isPartOfKeyWord(c);
        }
        /* check if last character is terminating keyword */
        if (c == ';' || c == ',' || c == '(' || Character.isWhitespace(c) || c == ICharacterScanner.EOF) {
            return checkKeyWordResetScannerOnDemandAndReturnDedicatedToken(sb.toString(), count, scanner);
        }

        return resetScannerAndReturnUndefined(scanner, count);
    }

    private IToken checkKeyWordResetScannerOnDemandAndReturnDedicatedToken(String string, int count, ICharacterScanner scanner) {
        if (string == null || string.isEmpty()) {
            return resetScannerAndReturnUndefined(scanner, count);
        }
        for (SQLKeyword keyword : keywords) {
            if (string.equalsIgnoreCase(keyword.getText())) {
                scanner.unread(); // we ignore the terminator character!
                return successToken;
            }
        }
        return resetScannerAndReturnUndefined(scanner, count);
    }

    private IToken resetScannerAndReturnUndefined(ICharacterScanner scanner, int count) {
        while (count > 0) {
            scanner.unread();
            count--;
        }
        return Token.UNDEFINED;
    }

    private boolean isPartOfKeyWord(int c) {
        boolean isPart = false;

        isPart = isPart || c == '_';
        isPart = isPart || Character.isAlphabetic(c);

        return isPart;
    }

}
