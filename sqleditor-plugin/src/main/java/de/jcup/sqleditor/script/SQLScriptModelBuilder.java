package de.jcup.sqleditor.script;

import java.util.Iterator;
import java.util.List;

import de.jcup.sqleditor.script.parser.ParseToken;
import de.jcup.sqleditor.script.parser.TokenParser;
import de.jcup.sqleditor.script.parser.TokenParserException;

public class SQLScriptModelBuilder {

    private static final int MAXIMUM_SELECT_PROJECTION_SIZE_SHOWN = 8+1;//+1 because of space always added in logic

    public SQLScriptModel build(String sql) {
        SQLScriptModel model = new SQLScriptModel();
        if (sql == null || sql.trim().length() == 0) {
            return model;
        }
        TokenParser parser = new TokenParser();
        try {
            List<ParseToken> tokens = parser.parse(sql);
            boolean newStatement = true;
            for (Iterator<ParseToken> it = tokens.iterator(); it.hasNext();) {

                /* start */
                if (it.hasNext()) {
                    ParseToken token = it.next();
                    if (token.isEndOfStatement()) {
                        newStatement = true;
                        continue;
                    }
                    if (token.isComment()) {
                        /* ignore in any case */
                        continue;
                    }
                    if (!newStatement) {
                        /* just ignore */
                        continue;
                    }
                    SQLStatement statement = new SQLStatement(token.getText(),model);
                    token = handleStatement(model.statements, statement, token, it);

                    newStatement = token.isEndOfStatement();

                }

            }

        } catch (TokenParserException e) {
            int start = 0;
            int end = 0;
            SQLError error = new SQLError(start, end, "Was not able to parse statements:" + e.getMessage());
            model.errors.add(error);
            System.err.println("Error:" + error);
        }
        return model;
    }

    private ParseToken handleStatement(List<SQLStatement> substatements, SQLStatement statement, ParseToken statementToken, Iterator<ParseToken> it) {
        /* guard closes */
        if (statement == null) {
            return statementToken;
        }
        if (it == null) {
            return statementToken;
        }
        if (statementToken == null) {
            return statementToken;
        }
        /* end of statements are ignored at all */
        if (statementToken.isEndOfStatement()) {
            return statementToken;
        }
        /* otherwise... */
        statement.pos = statementToken.getStart();
        statement.end = statementToken.getEnd();
        substatements.add(statement);

        if (!it.hasNext()) {
            return statementToken;
        }
        ParseToken scanned = statementToken;
        StringBuilder intermediateSb = new StringBuilder();
        do {
            scanned = it.next();
            if (scanned.isEndOfStatement()) {
                break;
            }
            if (scanned.isComment()) {
                break;
            }
            if(scanned.isBracketStart()) { // e.g. '(test1234',')' or '(','test1234',')'
                while( it.hasNext() && ! scanned.isBracketEnd()) {
                    scanned=it.next();
                }
                if (scanned.isBracketEnd()) {
                    break;
                }
            }
            if (scanned.isCandidateForSubStatement()) {
                SQLStatement newParent = new SQLStatement(scanned.getText(),statement);
                scanned = handleStatement(statement.getSubStatements(), newParent, scanned, it);
            }else {
                if(statementToken.isSelect()) {
                    if (scanned.isWhere()) {
                        break;
                    }
                    if (scanned.isFrom()) {
                        if (intermediateSb.length()>MAXIMUM_SELECT_PROJECTION_SIZE_SHOWN) {
                            intermediateSb=new StringBuilder();
                            intermediateSb.append(" {..}");
                        }
                    }
                }
                intermediateSb.append(" ");
                intermediateSb.append(scanned.getText());
                
            }
            
        } while (it.hasNext() && !scanned.isEndOfStatement());

        statement.appendToName(intermediateSb);
        
        return scanned;
    }

}
