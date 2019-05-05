package de.jcup.sqleditor.script.formatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jcup.sqleditor.document.keywords.SQLKeyWords;
import de.jcup.sqleditor.document.keywords.SQLKeyword;
import de.jcup.sqleditor.document.keywords.SQLStatementKeywords;
import de.jcup.sqleditor.document.keywords.SQLWhereBlockKeyWords;
import de.jcup.sqleditor.script.parser.ParseToken;
import de.jcup.sqleditor.script.parser.TokenParser;
import de.jcup.sqleditor.script.parser.TokenParserException;

public class TokenBasedSQLFormatter {
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
            return createSQL(tokens, config);
        } catch (TokenParserException e) {
            throw new SQLFormatException("Was not able to parse SQL", e);
        }

    }

    protected String createSQL(List<ParseToken> tokens, SQLFormatConfig config) throws SQLFormatException{
        Context context = new Context();
        context.config = config;

        context.tokenRunner = new ParseTokenRunner(tokens);
        do {
            if (! context.tokenRunner.hasToken()) {
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
            
        }while(true);
        return context.sb.toString().trim();
    }

    private void appendNewLineAndReplaceLastwhitespacesIfAtEnd(Context context) {
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
    
    private void appendSpaceAndReplaceLastwhitespacesIfAtEnd(Context context) {
        StringBuilder sb = context.sb;
        int length = sb.length();
        if (length > 0) {
            char c = sb.charAt(length - 1);
            if (Character.isWhitespace(c)) {
                sb.replace(length - 1, length, " ");
                return;
            }
        }
        sb.append("\n");
    }

    private void appendSpaceIfNotWhitespaceAtEnd(Context context) {
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

    private void handleKeyword(Context context) {
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
            context.whereBlockNextStatementIndentActive=false;
            
        } else if (SQLKeyword.isIdentifiedBy(text, new SQLKeyword[] {
                SQLStatementKeywords.WHERE,
                SQLStatementKeywords.OR,
                SQLStatementKeywords.AND
                })) {
            appendNewLineAndReplaceLastwhitespacesIfAtEnd(context);
            context.whereBlockNextStatementIndentActive=true;
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

    private void handleNoKeyword(Context context) throws SQLFormatException {
        BlockData blockData = tryToFindBracketBlock(context);
        if (blockData.isBlockFound()) {
            /* a block does always start with a ( and ends with ) - so to prevent
             * stack overflow /endless loop we must NOT parse the first ( and last
             * )!*/
            
            int indent = context.config.getIndent();
            context.sb.append("\n").append(getIndentString(indent)).append("(\n");;
            
            TokenBasedSQLFormatter f2 = new TokenBasedSQLFormatter();
            String appendMe = f2.format(blockData.getBlockContent(),context.config);
            String[] lines = appendMe.split("\n");
            for (String line: lines) {
                context.sb.append(getIndentString(indent+2)).append(line).append("\n");
            }
            context.sb.append(getIndentString(indent)).append(")\n");
            return;
        }
        String text = context.text;
        if (text.startsWith("--")) {
            /* comment special! */
            appendNewLineAndReplaceLastwhitespacesIfAtEnd(context);
            if (context.createBlockInside || context.whereBlockNextStatementIndentActive) {
                context.sb.append(indent(context));
            }
            context.sb.append(text);
            context.sb.append("\n");
            return;
        }
        if (text.equals(";")) {
            handleStatementSeperator(context);
        } else if (context.selectBlockActive) {
            if (text.equals("*")) {
                /* in this case we do not split */
                context.sb.append(text);
            } else {
                handleNoKeywordButSelectProjectionBlock(context);
            }
        } else if (context.whereBlockNextStatementIndentActive) {
            handleNoKeywordButWhereBlock(context);
        } else {
            if (context.createBlock) {
                handleCreateBlock(context);
            } else {
                context.sb.append(text);
            }
        }

    }
    
    private class BlockData{

        public String block;
        public boolean isBlockFound() {
            return block!=null && block.startsWith("(") && block.endsWith(")");
        }
        
        /**
         * @return block but without leading ( and trailing ) 
         */
        public String getBlockContent() {
            if (!isBlockFound()) {
                return "";
            }
            return block.substring(1,block.length()-2);
        }
        
    }
    
    private BlockData tryToFindBracketBlock(Context context){
        BlockData result = new BlockData();
        int tokenNumberBefore = context.tokenRunner.getTokenNumber();
        int countOpening = 0;
        int countClosing = 0;
        boolean firstStart=true;
        StringBuilder sb = new StringBuilder();
        while((firstStart || countOpening!=countClosing) && context.tokenRunner.hasToken()){
            ParseToken token = context.tokenRunner.getToken();
            firstStart=false;
            String text = token.getText();
            if(text.contentEquals("(")) {
                sb.append("( ");
                countOpening++;
            }else if (text.startsWith("(")) {
                countOpening++;
                sb.append("( ");
                sb.append(text.substring(1));
                sb.append(" ");
            }else if (text.contentEquals(")")) {
                sb.append(") ");
                countClosing++;
            }else if (text.endsWith(")")) {
                sb.append(text.substring(0,text.length()-2));
                sb.append(" ) ");
                countClosing++;
            }else {
                sb.append(token.getText());
                sb.append(" ");
            }
            if (context.tokenRunner.canForward()) {
                context.tokenRunner.forward();
            }else {
                break;
            }
            
        }

        String block = sb.toString().trim();
        if (block.startsWith("(")) {
            if (countOpening!=countClosing) {
                /* hm strange - normally this is an error!*/
                return result;
            }
            result.block=block;
        }
        if (! result.isBlockFound()) {
            context.tokenRunner.gotoTokenNumber(tokenNumberBefore);
        }
        return result;
    }

    private void handleCreateBlock(Context context) {
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
            context.sb.append(indent(context));
            context.sb.append(text);
            if (text.endsWith(",")) {
                context.sb.append("\n");
            }
        } else {
            context.sb.append(text);
        }
    }

    private void handleStatementSeperator(Context context) {
        StringBuilder sb = context.sb;
        // replace " " by "\n" because we got a space at the end..
        sb.replace(sb.length() - 1, sb.length(), ";\n");
        context.reset();
    }

    private void handleNoKeywordButSelectProjectionBlock(Context context) {
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
        String indent = indent(context);

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

    private void handleNoKeywordButWhereBlock(Context context) {
        StringBuilder sb = context.sb;
        String indent = indent(context);
        sb.append("\n");
        sb.append(indent);
        sb.append(context.text);
        context.whereBlockNextStatementIndentActive = false;
    }

    private Map<Integer, String> indentMap = new HashMap<>();

    private String indent(Context context) {
        ParseToken lastToken = context.lastToken;
        if (lastToken!=null) {
            if (lastToken.isAs() || lastToken.isNot()) {
                return "";
            }
        }
        int indent =context.config.getIndent();
        String spaces = getIndentString(indent);
        return spaces;
    }

    private String getIndentString(int indent) {
        String spaces = indentMap.get(indent);
        if (spaces == null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < indent; i++) {
                sb.append(" ");
            }
            indentMap.put(indent, sb.toString());
            spaces = indentMap.get(indent);
        }
        return spaces;
    }

    private class Context {
        public ParseTokenRunner tokenRunner;
        public boolean createBlockInside;
        private boolean selectBlockActive;
        private boolean whereBlockNextStatementIndentActive;
        private SQLFormatConfig config;
        private StringBuilder sb = new StringBuilder();
        private boolean selectProjectionBlockHandled;
        private boolean createBlock;
        private String text;
        private ParseToken nextLastToken;
        private ParseToken lastToken;

        public void reset() {
            selectBlockActive = false;
            selectProjectionBlockHandled = false;
            whereBlockNextStatementIndentActive = false;
            createBlock = false;
        }

        public boolean visitNextToken(ParseToken token) {
            this.lastToken=nextLastToken;
            this.nextLastToken=token;
            String text = token.getText();
            if (text == null) {
                return false;
            }
            this.text=text;
            if (SQLKeyword.isIdentifiedBy(text, SQLStatementKeywords.CREATE)) {
                createBlock = true;
            }
            if (SQLKeyword.isIdentifiedBy(text, SQLStatementKeywords.SELECT)) {
                selectBlockActive = true;
            } else if (SQLKeyword.isIdentifiedBy(text, SQLStatementKeywords.FROM)) {
                selectBlockActive = false;
                selectProjectionBlockHandled = false;
            }
            return true;

        }
    }
}
