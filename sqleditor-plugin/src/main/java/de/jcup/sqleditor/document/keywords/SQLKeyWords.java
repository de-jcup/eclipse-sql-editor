/*
 * Copyright 2018 Albert Tregnaghi
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
package de.jcup.sqleditor.document.keywords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.jcup.eclipse.commons.keyword.DocumentKeyWord;

public class SQLKeyWords {
	private static final SQLKeyword[] ALL_KEYWORDS = createAllKeywords();
	
	public static SQLKeyword[] getAllDefaultKeywords(){
		return ALL_KEYWORDS;
	}
	
	private static SQLKeyword[] createAllKeywords() {
		List<DocumentKeyWord> list = new ArrayList<>();
		list.addAll(Arrays.asList(SQLDataTypesKeywords.values()));
		list.addAll(Arrays.asList(SQLStatementKeywords.values()));
		list.addAll(Arrays.asList(SQLWhereBlockKeyWords.values()));
		list.addAll(Arrays.asList(SQLStatementTargetKeyWords.values()));
		list.addAll(Arrays.asList(SQLSchemaKeywords.values()));
		list.addAll(Arrays.asList(SQLFunctionKeywords.values()));
		
		return list.toArray(new SQLKeyword[list.size()]);
	}
	 
}
