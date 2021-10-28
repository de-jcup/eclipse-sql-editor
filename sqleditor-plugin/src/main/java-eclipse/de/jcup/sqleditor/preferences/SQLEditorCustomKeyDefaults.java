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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SQLEditorCustomKeyDefaults {
	private static List<SqlEditorCustomKeyDefinition> defaults = new ArrayList<>();
	static{
//		defaults.add(new SqlEditorCustomKeyDefinition("exception", "Raise an exc.NORMAL));
	}
	/**
	 * @return unmodifiable list of default definitions
	 */
	public static List<SqlEditorCustomKeyDefinition> get() {
		return Collections.unmodifiableList(defaults);
	}

}
