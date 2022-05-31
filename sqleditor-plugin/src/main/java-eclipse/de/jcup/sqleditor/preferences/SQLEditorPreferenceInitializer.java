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
 

import static de.jcup.sqleditor.SQLEditorColorConstants.*;
import static de.jcup.sqleditor.SQLEditorUtil.*;
import static de.jcup.sqleditor.preferences.SQLEditorPreferenceConstants.*;
import static de.jcup.sqleditor.preferences.SQLEditorSyntaxColorPreferenceConstants.*;
import static de.jcup.sqleditor.preferences.SQLEditorSyntaxStylePreferenceConstants.*;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class SQLEditorPreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		SQLEditorPreferences preferences = getPreferences();
		IPreferenceStore store = preferences.getPreferenceStore();
		
		/* Outline */
		store.setDefault(P_LINK_OUTLINE_WITH_EDITOR.getId(), true);
		store.setDefault(P_ENABLE_OUTLINE.getId(), true);
		
		/* ++++++++++++ */
		/* + Brackets + */
		/* ++++++++++++ */
		/* bracket rendering configuration */
		store.setDefault(P_EDITOR_MATCHING_BRACKETS_ENABLED.getId(), true); // per default matching is enabled, but without the two other special parts
		store.setDefault(P_EDITOR_HIGHLIGHT_BRACKET_AT_CARET_LOCATION.getId(), false);
		store.setDefault(P_EDITOR_ENCLOSING_BRACKETS.getId(), false);
		store.setDefault(P_EDITOR_AUTO_CREATE_END_BRACKETSY.getId(), true);
		
		/* bracket color */
		preferences.setDefaultColor(P_EDITOR_MATCHING_BRACKETS_COLOR, GRAY_JAVA);
		
		/* +++++++++++++++++++ */
		/* + Code Assistence + */
		/* +++++++++++++++++++ */
		store.setDefault(P_TOOLTIPS_ENABLED.getId(), true);
		store.setDefault(P_CODE_ASSIST_ADD_KEYWORDS.getId(), true);
		store.setDefault(P_CODE_ASSIST_ADD_SIMPLEWORDS.getId(), true);
		
		/* +++++++++++++++++ */
		/* + Editor Colors + */
		/* +++++++++++++++++ */
		preferences.setDefaultColor(COLOR_NORMAL_TEXT, BLACK);

		preferences.setDefaultColor(COLOR_STATEMENT_KEYWORD, KEYWORD_DEFAULT_PURPLE);
		
		preferences.setDefaultColor(COLOR_SINGLE_QUOTES, ROYALBLUE);
		preferences.setDefaultColor(COLOR_DOUBLE_QUOTES, DARK_BLUE);
		preferences.setDefaultColor(COLOR_COMMENT, GREEN_JAVA);
		
		preferences.setDefaultColor(COLOR_DATA_TYPE_KEYWORD, TASK_DEFAULT_RED);
		preferences.setDefaultColor(COLOR_CUSTOM_KEYWORDS, BROWN);
		preferences.setDefaultColor(COLOR_WHERE_BLOCK_KEYWORD, TASK_DEFAULT_RED);
		preferences.setDefaultColor(COLOR_TARGET_KEYWORD, DARK_GRAY);
		preferences.setDefaultColor(COLOR_FUNCTION_KEYWORDS, DARK_BLUE);
		
		/* +++++++++++++++++ */
        /* + Editor Styles + */
        /* +++++++++++++++++ */
		preferences.setDefaultStyle(STYLE_DATA_TYPE_KEYWORD, SQLEditorSyntaxStyle.NONE);
		preferences.setDefaultStyle(STYLE_STATEMENT_KEYWORD, SQLEditorSyntaxStyle.BOLD);
		preferences.setDefaultStyle(STYLE_TARGET_KEYWORD, SQLEditorSyntaxStyle.BOLD);
		preferences.setDefaultStyle(STYLE_WHERE_BLOCK_KEYWORD, SQLEditorSyntaxStyle.BOLD);
		preferences.setDefaultStyle(STYLE_FUNCTION_KEYWORDS, SQLEditorSyntaxStyle.NONE);
		preferences.setDefaultStyle(STYLE_DOUBLE_QUOTES, SQLEditorSyntaxStyle.NONE);
		preferences.setDefaultStyle(STYLE_SINGLE_QUOTES, SQLEditorSyntaxStyle.NONE);
		preferences.setDefaultStyle(STYLE_COMMENT, SQLEditorSyntaxStyle.NONE);
		preferences.setDefaultStyle(STYLE_CUSTOM_KEYWORDS, SQLEditorSyntaxStyle.BOLD);
		
	}
	
	
	
}
