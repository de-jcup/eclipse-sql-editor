package de.jcup.sqleditor.script;

public class SQLCommand {

	private String name;
	int pos;
	int end;

	public SQLCommand(String name){
		this.name=name;
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

}
