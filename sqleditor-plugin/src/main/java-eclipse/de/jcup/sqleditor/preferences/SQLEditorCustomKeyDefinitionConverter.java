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

import de.jcup.eclipse.commons.preferences.AbstractPreferenceValueConverter;

class SQLEditorCustomKeyDefinitionConverter extends AbstractPreferenceValueConverter<SqlEditorCustomKeyDefinition>{

	@Override
	protected void write(SqlEditorCustomKeyDefinition oneEntry,
			de.jcup.eclipse.commons.preferences.PreferenceDataWriter writer) {
		writer.writeString(oneEntry.getIdentifier());
		writer.writeString(oneEntry.getTooltip());
	}

	@Override
	protected SqlEditorCustomKeyDefinition read(
			de.jcup.eclipse.commons.preferences.PreferenceDataReader reader) {
		SqlEditorCustomKeyDefinition definition = new SqlEditorCustomKeyDefinition();
		definition.setIdentifier(reader.readString());
		definition.setTooltip(reader.readString());
		return definition;
	}
	

}