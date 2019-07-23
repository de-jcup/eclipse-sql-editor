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
