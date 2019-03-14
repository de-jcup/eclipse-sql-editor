package de.jcup.sqleditor.script;

import java.util.ArrayList;
import java.util.List;

public class SQLScriptModel {

	private List<SQLCommand> labels;
	
	public SQLScriptModel() {
		labels=new ArrayList<>();
	}
	
	public List<SQLCommand> getLabels() {
		return labels;
	}

	public boolean hasErrors() {
		return false;
	}

}
