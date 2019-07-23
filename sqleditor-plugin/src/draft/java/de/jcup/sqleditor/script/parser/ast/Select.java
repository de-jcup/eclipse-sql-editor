package de.jcup.sqleditor.script.parser.ast;

import java.util.ArrayList;
import java.util.List;

import de.jcup.sqleditor.script.parser.ParseToken;

public class Select extends AbstractAstElement implements Statement{

    public Select(ParseToken token) {
        super(token);
    }

    public List<Column> columns= new ArrayList<Column>();

    public From from = new From();
    
    public Where where=new Where();
}
