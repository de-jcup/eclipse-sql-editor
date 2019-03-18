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
