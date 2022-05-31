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
public enum SQLEditorSyntaxStylePreferenceConstants implements PreferenceIdentifiable, PreferenceLabeled{
	
	STYLE_STATEMENT_KEYWORD("styleStatement", "Statements"),
	
	STYLE_FUNCTION_KEYWORDS("styleFunctions","Functions"),
	
	STYLE_TARGET_KEYWORD("styleTargets","Targets"),
	
	STYLE_SINGLE_QUOTES("styleSingleQuotes", "Single quotes"),
	
	STYLE_DOUBLE_QUOTES("styleDoubleQuotes", "Double quotes"),
	
	STYLE_COMMENT("styleComments", "Comments"),
	
	STYLE_WHERE_BLOCK_KEYWORD("styleWhereBlock","Where block"),
	
	STYLE_DATA_TYPE_KEYWORD("styleDataType","Data types"),
	
    STYLE_CUSTOM_KEYWORDS("styleCustomKeywords","Custom keywords");
	
	;

	private String id;
	private String labelText;

	private SQLEditorSyntaxStylePreferenceConstants(String id, String labelText) {
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
