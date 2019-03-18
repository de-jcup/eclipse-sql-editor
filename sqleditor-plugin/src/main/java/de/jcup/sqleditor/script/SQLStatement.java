package de.jcup.sqleditor.script;

import java.util.ArrayList;
import java.util.List;

public class SQLStatement {

    private String name;
    int pos;
    int end;
    List<SQLStatement> subStatements;
    private Object parent;

    public SQLStatement(String name, Object parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return pos;
    }

    public int getLengthToNameEnd() {
        return name.length();
    }

    public int getEnd() {
        return end;
    }

    public List<SQLStatement> getSubStatements() {
        if (subStatements==null) {
            subStatements=new ArrayList<>();
        }
        return subStatements;
    }

    public Object getParent() {
        return parent;
    }

    public boolean hasChildren() {
        return subStatements!=null && !subStatements.isEmpty();
    }

    public void appendToName(StringBuilder toAdd) {
        if (toAdd==null || toAdd.length()==0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(toAdd);
        this.name = sb.toString();
    }

}
