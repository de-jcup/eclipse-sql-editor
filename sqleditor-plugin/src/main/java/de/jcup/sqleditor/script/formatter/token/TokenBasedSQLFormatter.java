package de.jcup.sqleditor.script.formatter.token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jcup.sqleditor.document.keywords.SQLKeyWords;
import de.jcup.sqleditor.document.keywords.SQLKeyword;
import de.jcup.sqleditor.document.keywords.SQLStatementKeywords;
import de.jcup.sqleditor.document.keywords.SQLWhereBlockKeyWords;
import de.jcup.sqleditor.script.formatter.JustTrimRightEachLineFormatter;
import de.jcup.sqleditor.script.formatter.SQLFormatConfig;
import de.jcup.sqleditor.script.formatter.SQLFormatException;
import de.jcup.sqleditor.script.parser.ParseToken;
import de.jcup.sqleditor.script.parser.TokenParser;
import de.jcup.sqleditor.script.parser.TokenParserException;

public class TokenBasedSQLFormatter {

    private static final int INFINITE_LOOP_PREVENTION = 400_000;
    private JustTrimRightEachLineFormatter justTrimRightFormatter = new JustTrimRightEachLineFormatter();

    public String format(String sql) throws SQLFormatException {
        return format(sql, null);
    }

    public String format(String sql, SQLFormatConfig config) throws SQLFormatException {
        if (sql == null || sql.isEmpty()) {
            return sql;
        }
        if (config == null) {
            config = SQLFormatConfig.DEFAULT;
        }

        TokenParser p = new TokenParser();
        try {
            List<ParseToken> tokens = p.parse(sql);
            String formatted = createSQL(tokens, config);
            return justTrimRightFormatter.format(formatted);
        } catch (TokenParserException e) {
            throw new SQLFormatException("Was not able to parse SQL", e);
        }

    }

    protected String createSQL(List<ParseToken> tokens, SQLFormatConfig config) throws SQLFormatException {
        TokenBasedSQLFormatContext context = new TokenBasedSQLFormatContext();
        context.config = config;

        context.tokenRunner = new ParseTokenRunner(tokens);
        int loop = 0;
        do {
            loop++;
            if (!context.tokenRunner.hasToken()) {
                break;
            }
            ParseToken token = context.tokenRunner.getToken();
            if (!context.visitNextToken(token)) {
                context.tokenRunner.forward();
                continue;
            }

            if (SQLKeyword.isIdentifiedBy(context.text, SQLKeyWords.getAll())) {
                handleKeyword(context);
            } else {
                handleNoKeyword(context);
            }
            appendSpaceIfNotWhitespaceAtEnd(context);
            context.tokenRunner.forward();

        } while (loop < INFINITE_LOOP_PREVENTION);
        return context.sb.toString().trim();
    }

    private void appendNewLineAndReplaceLastwhitespacesIfAtEnd(TokenBasedSQLFormatContext context) {
        StringBuilder sb = context.sb;
        int length = sb.length();
        if (length > 0) {
            char c = sb.charAt(length - 1);
            if (Character.isWhitespace(c)) {
                sb.replace(length - 1, length, "\n");
                return;
            }
        }
        sb.append("\n");
    }

    private void appendSpaceAndReplaceLastwhitespacesIfAtEnd(TokenBasedSQLFormatContext context) {
        StringBuilder sb = context.sb;
        int length = sb.length();
        if (length > 0) {
            char c = sb.charAt(length - 1);
            if (Character.isWhitespace(c)) {
                sb.replace(length - 1, length, " ");
                return;
            }
        }
        sb.append(" ");
    }

    private void appendSpaceIfNotWhitespaceAtEnd(TokenBasedSQLFormatContext context) {
        StringBuilder sb = context.sb;
        int length = sb.length();
        if (length > 0) {
            char c = sb.charAt(length - 1);
            if (Character.isWhitespace(c)) {
                return;
            }
        }
        sb.append(" ");
    }

    private void handleKeyword(TokenBasedSQLFormatContext context) {
        String text = context.text;
        String keyword = text;
        boolean keywordAddded = false;

        if (context.config.isTransformingKeywordsToUpperCase()) {
            keyword = text.toUpperCase();
        }

        if (SQLKeyword.isIdentifiedBy(text, SQLStatementKeywords.AS)) {

            /* destroy last newline if there is one */
            appendSpaceAndReplaceLastwhitespacesIfAtEnd(context);
            context.sb.append(text);
            return;
        }

        // @formatter:off
        if (SQLKeyword.isIdentifiedBy(text, new SQLKeyword[] { 
                SQLWhereBlockKeyWords.ORDER, 
                SQLWhereBlockKeyWords.GROUP, 
                SQLWhereBlockKeyWords.HAVING, 
                })) {

            appendNewLineAndReplaceLastwhitespacesIfAtEnd(context);
            context.statementIndentActive=true;
            
        } else if (SQLKeyword.isIdentifiedBy(text, new SQLKeyword[] {
                SQLStatementKeywords.WHERE,
                SQLStatementKeywords.OR,
                SQLStatementKeywords.AND,
                })) {
            appendNewLineAndReplaceLastwhitespacesIfAtEnd(context);
            context.statementIndentActive=true;
        } else if (SQLKeyword.isIdentifiedBy(text, new SQLKeyword[] {
                SQLStatementKeywords.FROM,
                })) {
            appendNewLineAndReplaceLastwhitespacesIfAtEnd(context);
            context.statementIndentActive=true;
        } else if (SQLKeyword.isIdentifiedBy(text, new SQLKeyword[] {
                SQLStatementKeywords.JOIN,
                SQLStatementKeywords.INNER,
                SQLStatementKeywords.LEFT,
                SQLStatementKeywords.OUTER
                })) {
            appendNewLineAndReplaceLastwhitespacesIfAtEnd(context);
            context.reset();
        }else if (SQLKeyword.isIdentifiedBy(text, SQLWhereBlockKeyWords.BY)) {
            appendSpaceIfNotWhitespaceAtEnd(context);
        }

        if (!keywordAddded) {
            context.sb.append(keyword);
        }
    }
    // @formatter:on

    private void handleNoKeyword(TokenBasedSQLFormatContext context) throws SQLFormatException {
        if (!context.createBlockInside) {
            BlockResult result = tryToFindBracketBlock(context);
            if (result.isBlockFound()) {
                if (result.hasOnlyOneElement()) {
                    context.sb.append("(").append(result.getBlockContent()).append(")");
                } else {
                    appendFoundBlockByAnotherFormatter(context, result);
                }
                return;
            }
        }
        String text = context.text;
        if (text.startsWith("--")) {
            /* comment special! */
            appendNewLineAndReplaceLastwhitespacesIfAtEnd(context);
            context.sb.append(getIndent(context));
            context.sb.append(text);
            context.sb.append("\n");
            return;
        }
        if (text.equals(",")) {
            handleCommaSeperator(context);
        } else if (text.equals(";")) {
            handleStatementSeperator(context);
        } else if (context.selectBlockActive) {
            if (text.equals("*")) {
                /* in this case we do not split */
                context.sb.append(text);
            } else {
                handleNoKeywordButSelectProjectionBlock(context);
            }
        } else if (context.statementIndentActive) {
            handleNoKeywordButStatementIndentActive(context);
        } else {
            if (context.createBlock) {
                handleCreateBlock(context);
            } else {
                context.sb.append(text);
            }
        }

    }

    private void handleCommaSeperator(TokenBasedSQLFormatContext context) {
        StringBuilder sb = context.sb;
        // replace " " by "\n" because we got a space at the end..
        sb.replace(sb.length() - 1, sb.length(), ",\n");
        
    }

    private void appendFoundBlockByAnotherFormatter(TokenBasedSQLFormatContext context, BlockResult blockResult) throws SQLFormatException {
        /*
         * a block does always start with a ( and ends with ) - so to prevent stack
         * overflow /endless loop we must NOT parse the first ( and last )!
         */

        context.sb.append("\n");
        context.sb.append(getIndent(context));
        context.sb.append("(\n");
        TokenBasedSQLFormatter innerFormatter = new TokenBasedSQLFormatter();

        String appendMe = innerFormatter.format(blockResult.getBlockContent(), context.config);
        String[] lines = appendMe.split("\n");
        context.indentRight();
        for (String line : lines) {
            context.sb.append(getIndent(context)).append(line).append("\n");
        }
        context.indentLeft();
        context.sb.append(getIndent(context)).append(")\n");
    }

    private class BlockResult {

        public String block;
        public boolean onlyOneElement;

        public boolean isBlockFound() {
            return block != null && block.startsWith("(") && block.endsWith(")");
        }

        public boolean hasOnlyOneElement() {
            return onlyOneElement;
        }

        /**
         * @return block but without leading ( and trailing )
         */
        public String getBlockContent() {
            if (!isBlockFound()) {
                return "";
            }
            return block.substring(1, block.length() - 2);
        }

    }

    private BlockResult tryToFindBracketBlock(TokenBasedSQLFormatContext context) {
        BlockResult result = new BlockResult();
        int tokenNumberBefore = context.tokenRunner.getTokenNumber();
        int countOpening = 0;
        int countClosing = 0;
        boolean firstStart = true;
        int amountOfTokensInBlock = 0;
        StringBuilder sb = new StringBuilder();
        boolean takeNextToken = false;
        while ((firstStart || countOpening != countClosing) && context.tokenRunner.hasToken()) {
            if (takeNextToken) {
                if (context.tokenRunner.canForward()) {
                    context.tokenRunner.forward();
                } else {
                    break;
                }
            }
            takeNextToken = true;

            ParseToken token = context.tokenRunner.getToken();
            firstStart = false;
            String text = token.getText();
            if (text.contentEquals("(")) {
                sb.append("(");
                countOpening++;
            } else if (text.contentEquals(")")) {
                sb.append(")");
                countClosing++;
            } else {
                amountOfTokensInBlock++;
                sb.append(token.getText());
                sb.append(" ");
            }

        }
        result.onlyOneElement = amountOfTokensInBlock < 2;
        String block = sb.toString().trim();
        if (block.startsWith("(")) {
            if (countOpening != countClosing) {
                /* hm strange - normally this is an error! */
                return result;
            }
            result.block = block;
        }
        if (!result.isBlockFound()) {
            context.tokenRunner.gotoTokenNumber(tokenNumberBefore);
        }
        return result;
    }

    private void handleCreateBlock(TokenBasedSQLFormatContext context) {
        String text = context.text;
        if (text.equals("(")) {
            appendNewLineAndReplaceLastwhitespacesIfAtEnd(context);
            context.sb.append("(\n");
            context.createBlockInside = true;
            return;
        } else if (text.equals(")")) {
            appendNewLineAndReplaceLastwhitespacesIfAtEnd(context);
            context.sb.append(")\n");
            context.createBlockInside = false;
            return;
        }
        if (context.createBlockInside) {
            context.sb.append(getIndentLastToken(context));
            context.sb.append(text);
            if (text.endsWith(",")) {
                context.sb.append("\n");
            }
        } else {
            context.sb.append(text);
        }
    }

    private void handleStatementSeperator(TokenBasedSQLFormatContext context) {
        StringBuilder sb = context.sb;
        // replace " " by "\n" because we got a space at the end..
        sb.replace(sb.length() - 1, sb.length(), ";\n");
        context.reset();
    }

    private void handleNoKeywordButSelectProjectionBlock(TokenBasedSQLFormatContext context) {
        String text = context.text;
        StringBuilder sb = context.sb;
        if (!context.config.isBreakingSelectProjection()) {
            sb.append(text);
            return;
        }
        if (!context.selectProjectionBlockHandled) {
            appendNewLineAndReplaceLastwhitespacesIfAtEnd(context);
            context.selectProjectionBlockHandled = true; // only one \n interesting when not :"a,b,c" but "a, b , c"
        }
        String indent = getIndentLastToken(context);

        String[] parts = text.split(",");
        if (parts.length == 1) {
            /* only one part, so keep as is - when a commis is inside get the comma! */
            sb.append(indent);
            sb.append(text);
            sb.append("\n");
        } else {
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                sb.append(indent);
                sb.append(part.trim());
                if (i < parts.length - 1) {
                    sb.append(",");
                }
                sb.append("\n");
            }
        }
    }

    private void handleNoKeywordButStatementIndentActive(TokenBasedSQLFormatContext context) {
        StringBuilder sb = context.sb;
        context.indentRight();
        String indent = getIndentLastToken(context);
        sb.append("\n");
        sb.append(indent);
        sb.append(context.text);
        context.indentLeft();
        context.statementIndentActive = false;
    }

    private Map<Integer, String> indentMap = new HashMap<>();

    private String getIndent(TokenBasedSQLFormatContext context) {
        return getIndentStringTokenAware(context, null);
    }

    private String getIndentLastToken(TokenBasedSQLFormatContext context) {
        ParseToken lastToken = context.lastToken;
        return getIndentStringTokenAware(context, lastToken);
    }

    private String getIndentStringTokenAware(TokenBasedSQLFormatContext context, ParseToken lastToken) {
        if (lastToken != null) {
            if (lastToken.isAs() || lastToken.isNot()) {
                return "";
            }
        }
        int indent = context.getIndent();
        int chars = context.config.getIndent() * indent;
        String spaces = calculateIndentString(chars);
        return spaces;
    }

    private String calculateIndentString(int count) {
        String spaces = indentMap.get(count);
        if (spaces == null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count; i++) {
                sb.append(" ");
            }
            indentMap.put(count, sb.toString());
            spaces = indentMap.get(count);
        }
        return spaces;
    }
}