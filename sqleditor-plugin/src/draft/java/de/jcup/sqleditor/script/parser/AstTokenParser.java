package de.jcup.sqleditor.script.parser;

import java.util.List;

import de.jcup.sqleditor.script.formatter.token.ParseTokenRunner;
import de.jcup.sqleditor.script.parser.ast.Comment;
import de.jcup.sqleditor.script.parser.ast.SQLAbstractSyntaxTree;
import de.jcup.sqleditor.script.parser.ast.Select;

public class AstTokenParser {

    TokenParser tokenParser = createTokenParser();
    
    public SQLAbstractSyntaxTree parse(String sql) throws TokenParserException {
        List<ParseToken> parseTokens = tokenParser.parse(sql);
        return buildSyntaxTree(parseTokens);
    }

    TokenParser createTokenParser() {
        return new TokenParser();
    }

    SQLAbstractSyntaxTree buildSyntaxTree(List<ParseToken> parseTokens) {
        SQLAbstractSyntaxTree tree = new SQLAbstractSyntaxTree();
        buildSyntaxTree(tree,new ParseTokenRunner(parseTokens)); 
        return tree;
    }

    private void buildSyntaxTree(SQLAbstractSyntaxTree tree, ParseTokenRunner runner) {
        while (runner.hasToken()) {
           ParseToken token = runner.getToken();
           if(token.isComment()) {
               tree.elements.add(new Comment(token));
           }else if (token.isSelect()) {
               Select select = buildSelect(token, runner); 
               tree.elements.add(select);
           }
           if (runner.canForward()) {
               runner.forward();
           }else {
               break;
           }
       }
        
    }

    private Select buildSelect(ParseToken token, ParseTokenRunner runner) {
        Select select = new Select(token);
//        while (runner.hasToken()) {
//            ParseToken token = runner.getToken();
//        }
//        return null;
        return select;
    }
}
