package de.jcup.sqleditor.script;

import java.util.ArrayList;
import java.util.List;

public class SQLScriptModel {

	List<SQLStatement> statements;
	List<SQLError> errors = new ArrayList<SQLError>();
	
	public SQLScriptModel() {
		statements=new ArrayList<>();
	}
	
	public List<SQLStatement> getSQLStatements() {
		return statements;
	}

	public boolean hasErrors(){
        return !getErrors().isEmpty();
    }
	
	public List<SQLError> getErrors() {
        return errors;
    }

    

}
