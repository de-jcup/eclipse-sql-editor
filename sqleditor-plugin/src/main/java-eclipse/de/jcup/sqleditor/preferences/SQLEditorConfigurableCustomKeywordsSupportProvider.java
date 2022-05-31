/*
 * Copyright 2021 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
package de.jcup.sqleditor.preferences;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.jcup.eclipse.commons.PluginContextProvider;
import de.jcup.sqleditor.document.keywords.SQLKeyword;

public class SQLEditorConfigurableCustomKeywordsSupportProvider {

    private List<SqlEditorCustomKeyDefinition> cachedTaskTagDefinitions;
    private SQLEditorCustomKeyDefinitionConverter converter;
    private AbstractUIPlugin plugin;
    private String pluginId;

    public SQLEditorConfigurableCustomKeywordsSupportProvider(PluginContextProvider provider) {
        this.plugin = provider.getActivator();
        this.pluginId = provider.getPluginID();

        converter = new SQLEditorCustomKeyDefinitionConverter();
    }

    public void logError(String error, Throwable t) {
        plugin.getLog().log(new Status(IStatus.ERROR, pluginId, error, t));
    }

    SQLEditorCustomKeyDefinitionConverter getConverter() {
        return converter;
    }

    public List<SqlEditorCustomKeyDefinition> getTaskTagDefinitions() {
        if (cachedTaskTagDefinitions == null) {
            String string = getPreferenceStore().getString(SQLEditorCustomKeywordsPreferencePage.PREFERENCE_KEY_CUSTOM_KEYWORDS_DEFINITIONS);
            cachedTaskTagDefinitions = converter.convertStringToList(string);
        }
        return cachedTaskTagDefinitions;
    }

    protected void resetCustomKeywordDefinitions() {
        cachedTaskTagDefinitions = null;
    }

    public boolean isCustomSQLeywordSupportEnabled() {
        return getPreferenceStore().getBoolean(SQLEditorCustomKeywordsPreferencePage.PREFERENCE_KEY_CUSTOM_KEYWORDS_ENABLED);
    }

    public String getTodoTaskPluginId() {
        return pluginId;
    }

    public IPreferenceStore getPreferenceStore() {
        return plugin.getPreferenceStore();
    }

    public SQLKeyword[] getCustomKeywords() {
        List<SqlEditorCustomKeyDefinition> definitions = getTaskTagDefinitions();
        return definitions.toArray(new SQLKeyword[definitions.size()]);
    }

}
