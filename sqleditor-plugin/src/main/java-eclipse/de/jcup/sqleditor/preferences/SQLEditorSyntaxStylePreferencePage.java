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

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.jcup.sqleditor.SQLEditorUtil;

public class SQLEditorSyntaxStylePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public SQLEditorSyntaxStylePreferencePage() {
		setPreferenceStore(SQLEditorUtil.getPreferences().getPreferenceStore());
	}
	
	@Override
	public void init(IWorkbench workbench) {
		
	}

	@Override
	protected void createFieldEditors() {
	    SQLEditorSyntaxStyle[] allStyles = SQLEditorSyntaxStyle.values();
	    String[][] entryNamesAndValues = new String[allStyles.length][2];
        int index = 0;
        for (SQLEditorSyntaxStyle style : allStyles) {
            entryNamesAndValues[index++] = new String[] { style.getDescription(), style.getId() };
        }
		Composite parent = getFieldEditorParent();
		for (SQLEditorSyntaxStylePreferenceConstants styleIdentifier: SQLEditorSyntaxStylePreferenceConstants.values()){
            ComboFieldEditor editor = new ComboFieldEditor(styleIdentifier.getId(), styleIdentifier.getLabelText(), entryNamesAndValues, parent);
			addField(editor);
		}
		
	}
	
}