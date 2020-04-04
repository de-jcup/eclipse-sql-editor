package de.jcup.sqleditor.preferences;
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

/**
 * Constant definitions for plug-in preferences
 */
public enum SQLEditorSyntaxColorPreferenceConstants implements PreferenceIdentifiable, PreferenceLabeled{
	COLOR_NORMAL_TEXT("colorNormalText","Normal tokenText color"),
	COLOR_STATEMENT_KEYWORD("colorStatement", "Statements"),
	COLOR_FUNCTION_KEYWORDS("colorFunctions","Functions"),
	COLOR_TARGET_KEYWORD("colorTargets","Targets"),
	COLOR_SINGLE_QUOTES("colorSingleQuotes", "Single quotes"),
	COLOR_DOUBLE_QUOTES("colorDoubleQuotes", "Double quotes"),
	COLOR_COMMENT("colorComments", "Comments"),
	COLOR_WHERE_BLOCK_KEYWORD("colorWhereBlock","Where block"),
	COLOR_DATA_TYPE_KEYWORD("colorDataType","Data types");
	
	;

	private String id;
	private String labelText;

	private SQLEditorSyntaxColorPreferenceConstants(String id, String labelText) {
		this.id = id;
		this.labelText=labelText;
	}

	public String getLabelText() {
		return labelText;
	}
	
	public String getId() {
		return id;
	}

}
