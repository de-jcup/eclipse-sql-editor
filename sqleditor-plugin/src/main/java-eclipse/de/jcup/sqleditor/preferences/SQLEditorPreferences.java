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

import static de.jcup.sqleditor.preferences.SQLEditorPreferenceConstants.*;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import de.jcup.eclipse.commons.ui.EclipseUtil;
import de.jcup.sqleditor.ColorUtil;
import de.jcup.sqleditor.SQLEditor;
import de.jcup.sqleditor.SQLEditorActivator;

public class SQLEditorPreferences {

	private static SQLEditorPreferences INSTANCE = new SQLEditorPreferences();
	private IPreferenceStore store;

	private SQLEditorPreferences() {
		store = new ScopedPreferenceStore(InstanceScope.INSTANCE, SQLEditorActivator.PLUGIN_ID);
		store.addPropertyChangeListener(new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event == null) {
					return;
				}
				String property = event.getProperty();
				if (property == null) {
					return;
				}
				ChangeContext context = new ChangeContext();
				for (SQLEditorSyntaxColorPreferenceConstants c : SQLEditorSyntaxColorPreferenceConstants.values()) {
					if (property.equals(c.getId())) {
						context.colorChanged = true;
						break;
					}
				}
				updateColorsInSQLEditors(context);

			}

			private void updateColorsInSQLEditors(ChangeContext context) {
				if (!context.hasChanges()) {
					return;
				}
				/* inform all SQL editors about color changes */
				IWorkbenchPage activePage = EclipseUtil.getActivePage();
				if (activePage == null) {
					return;
				}
				IEditorReference[] references = activePage.getEditorReferences();
				for (IEditorReference ref : references) {
					IEditorPart editor = ref.getEditor(false);
					if (editor == null) {
						continue;
					}
					if (!(editor instanceof SQLEditor)) {
						continue;
					}
					SQLEditor geditor = (SQLEditor) editor;
					if (context.colorChanged){
						geditor.handleColorSettingsChanged();
					}
					if (context.validationChanged){
						geditor.rebuildOutline();
					}
				}
			}
		});

	}

	private class ChangeContext {
		private boolean colorChanged = false;
		private boolean validationChanged = false;

		private boolean hasChanges() {
			boolean changedAtAll = colorChanged;
			changedAtAll = changedAtAll || validationChanged;
			return changedAtAll;
		}
	}

	public String getStringPreference(SQLEditorPreferenceConstants id) {
		String data = getPreferenceStore().getString(id.getId());
		if (data == null) {
			data = "";
		}
		return data;
	}

	public boolean getBooleanPreference(SQLEditorPreferenceConstants id) {
		boolean data = getPreferenceStore().getBoolean(id.getId());
		return data;
	}

	public void setBooleanPreference(SQLEditorPreferenceConstants id, boolean value) {
		getPreferenceStore().setValue(id.getId(), value);
	}

	public boolean isLinkOutlineWithEditorEnabled() {
		return getBooleanPreference(P_LINK_OUTLINE_WITH_EDITOR);
	}
	
	public boolean isOutlineEnabled() {
        return getBooleanPreference(P_ENABLE_OUTLINE);
    }
	
	public IPreferenceStore getPreferenceStore() {
		return store;
	}

	public boolean getDefaultBooleanPreference(SQLEditorPreferenceConstants id) {
		boolean data = getPreferenceStore().getDefaultBoolean(id.getId());
		return data;
	}

	public RGB getColor(PreferenceIdentifiable identifiable) {
		RGB color = PreferenceConverter.getColor(getPreferenceStore(), identifiable.getId());
		return color;
	}

	/**
	 * Returns color as a web color in format "#RRGGBB"
	 * 
	 * @param identifiable
	 * @return web color string
	 */
	public String getWebColor(PreferenceIdentifiable identifiable) {
		RGB color = getColor(identifiable);
		if (color == null) {
			return null;
		}
		String webColor = ColorUtil.convertToHexColor(color);
		return webColor;
	}

	public void setDefaultColor(PreferenceIdentifiable identifiable, RGB color) {
		PreferenceConverter.setDefault(getPreferenceStore(), identifiable.getId(), color);
	}

	public static SQLEditorPreferences getInstance() {
		return INSTANCE;
	}

	

}
