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

import static de.jcup.sqleditor.preferences.SQLEditorSyntaxColorPreferenceConstants.*;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.jcup.sqleditor.SQLEditorColorConstants;
import de.jcup.sqleditor.SQLEditorUtil;

public class SQLEditorSyntaxColorPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public SQLEditorSyntaxColorPreferencePage() {
		setPreferenceStore(SQLEditorUtil.getPreferences().getPreferenceStore());
	}
	
	@Override
	public void init(IWorkbench workbench) {
		
	}

	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		Map<SQLEditorSyntaxColorPreferenceConstants, ColorFieldEditor> editorMap = new HashMap<SQLEditorSyntaxColorPreferenceConstants, ColorFieldEditor>();
		for (SQLEditorSyntaxColorPreferenceConstants colorIdentifier: SQLEditorSyntaxColorPreferenceConstants.values()){
			ColorFieldEditor editor = new ColorFieldEditor(colorIdentifier.getId(), colorIdentifier.getLabelText(), parent);
			editorMap.put(colorIdentifier, editor);
			addField(editor);
		}
		Button restoreDarkThemeColorsButton= new Button(parent,  SWT.PUSH);
		restoreDarkThemeColorsButton.setText("Restore Defaults for Dark Theme");
		restoreDarkThemeColorsButton.setToolTipText("Same as 'Restore Defaults' but for dark themes.\n Editor makes just a suggestion, you still have to apply or cancel the settings.");
		restoreDarkThemeColorsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				/* editor colors */
				changeColor(editorMap, COLOR_NORMAL_TEXT, SQLEditorColorConstants.GRAY_JAVA);
				changeColor(editorMap, COLOR_STATEMENT_KEYWORD, SQLEditorColorConstants.MIDDLE_GREEN);
				changeColor(editorMap, COLOR_DATA_TYPE_KEYWORD, SQLEditorColorConstants.MIDDLE_YELOW);
				
				changeColor(editorMap, COLOR_SINGLE_QUOTES, SQLEditorColorConstants.MIDDLE_ORANGE);
				changeColor(editorMap, COLOR_DOUBLE_QUOTES, SQLEditorColorConstants.BRIGHT_BLUE);
				changeColor(editorMap, COLOR_COMMENT, SQLEditorColorConstants.MIDDLE_CYAN);
				changeColor(editorMap, COLOR_WHERE_BLOCK_KEYWORD, SQLEditorColorConstants.MIDDLE_GREEN);
				changeColor(editorMap, COLOR_TARGET_KEYWORD, SQLEditorColorConstants.MIDDLE_GREEN_2);
				changeColor(editorMap, COLOR_FUNCTION_KEYWORDS, SQLEditorColorConstants.MIDDLE_GREEN_2);
				
				
			}

			private void changeColor(Map<SQLEditorSyntaxColorPreferenceConstants, ColorFieldEditor> editorMap,
					SQLEditorSyntaxColorPreferenceConstants colorId, RGB rgb) {
				editorMap.get(colorId).getColorSelector().setColorValue(rgb);
			}
			
		});
			
		
	}
	
}