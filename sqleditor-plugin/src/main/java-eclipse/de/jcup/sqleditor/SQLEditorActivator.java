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
package de.jcup.sqleditor;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.jcup.eclipse.commons.PluginContextProvider;
import de.jcup.eclipse.commons.keyword.TooltipTextSupport;
import de.jcup.eclipse.commons.resource.EclipseResourceInputStreamProvider;
import de.jcup.sqleditor.preferences.SQLEditorConfigurableCustomKeywordsSupportProvider;
import de.jcup.sqleditor.preferences.SQLEditorPreferences;

/**
 * The activator class controls the plug-in life cycle
 */
public class SQLEditorActivator extends AbstractUIPlugin implements PluginContextProvider {

    // The plug-in COMMAND_ID
    public static final String PLUGIN_ID = "de.jcup.sqleditor"; //$NON-NLS-1$

    // The shared instance
    private static SQLEditorActivator plugin;
    private ColorManager colorManager;

    /**
     * The constructor
     */
    public SQLEditorActivator() {
        colorManager = new ColorManager();
        TooltipTextSupport.setTooltipInputStreamProvider(new EclipseResourceInputStreamProvider(PLUGIN_ID));
    }

    public ColorManager getColorManager() {
        return colorManager;
    }

    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        SQLEditorPreferences.register(new SQLEditorConfigurableCustomKeywordsSupportProvider(this));
    }

    public void stop(BundleContext context) throws Exception {
        plugin = null;
        colorManager.dispose();
        super.stop(context);
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static SQLEditorActivator getDefault() {
        return plugin;
    }

    @Override
    public AbstractUIPlugin getActivator() {
        return this;
    }

    @Override
    public String getPluginID() {
        return PLUGIN_ID;
    }


}
