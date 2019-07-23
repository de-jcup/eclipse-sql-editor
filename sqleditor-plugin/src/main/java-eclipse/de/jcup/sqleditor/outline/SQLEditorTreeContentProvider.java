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
package de.jcup.sqleditor.outline;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

import de.jcup.sqleditor.script.SQLStatement;
import de.jcup.sqleditor.preferences.SQLEditorPreferences;
import de.jcup.sqleditor.script.SQLScriptModel;

public class SQLEditorTreeContentProvider implements ITreeContentProvider {

	private static final Object[] EMPTY_ARRAY = new Object[] {};
    private static final String SQL_SCRIPT_CONTAINS_ERRORS = "SQL script contains errors.";
	private static final String SQL_SCRIPT_DOES_NOT_CONTAIN_ANY_STATEMENTS = "SQL script does not contain any elements";
	private static final Object[] RESULT_WHEN_EMPTY = new Object[] { SQL_SCRIPT_DOES_NOT_CONTAIN_ANY_STATEMENTS };
	private Object[] items;
	private Object monitor = new Object();

	SQLEditorTreeContentProvider() {
		items = RESULT_WHEN_EMPTY;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		synchronized (monitor) {
		    if (! SQLEditorPreferences.getInstance().isOutlineEnabled()) {
	            return new Object[] { "Outline disabled in preferences"};
	        }
			if (inputElement instanceof SQLScriptModel) {
			    if (items != null && items.length > 0) {
			        return items;
			    }
			    return RESULT_WHEN_EMPTY;
			    
			}
			return new Object[] { "Unsupported input element:"+inputElement };
		}
	}

	@Override
	public Object[] getChildren(Object parentElement) {
	    if (parentElement instanceof Item) {
            Item parentItem = (Item) parentElement;
            return parentItem.getChildren().toArray();
        }
		return EMPTY_ARRAY;
	}

	@Override
	public Object getParent(Object element) {
	    if (element instanceof Item) {
            Item parentItem = (Item) element;
            return parentItem.getParent();
        }
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
	    if (element instanceof Item) {
            Item parentItem = (Item) element;
            return parentItem.hasChildren();
        }
		return false;
	}

	private Item[] createItems(SQLScriptModel model) {
		List<Item> list = new ArrayList<>();
		List<SQLStatement> sqlStatements = model.getSQLStatements();
        createItems(null,list, sqlStatements);
		if (list.isEmpty()) {
			Item item = new Item();
			item.name = SQL_SCRIPT_DOES_NOT_CONTAIN_ANY_STATEMENTS;
			item.type = ItemType.META_INFO;
			item.offset = 0;
			item.length = 0;
			item.endOffset=0;
			list.add(item);
		}
		if (model.hasErrors()) {
			Item item = new Item();
			item.name = SQL_SCRIPT_CONTAINS_ERRORS;
			item.type = ItemType.META_ERROR;
			item.offset = 0;
			item.length = 0;
			item.endOffset=0;
			list.add(0, item);
		}
		return list.toArray(new Item[list.size()]);

	}

    private void createItems(Item parentItem, List<Item> list, List<SQLStatement> sqlStatements) {
        for (SQLStatement statement : sqlStatements) {
			Item item = new Item();
			item.setParent(parentItem);
			item.name = statement.getName();
			item.type = ItemType.STATEMENT;
			item.offset = statement.getPosition();
			item.length = statement.getLengthToNameEnd();
			item.endOffset=statement.getEnd();
			list.add(item);
			if (statement.hasChildren()) {
			    createItems(item, item.getChildren(),statement.getSubStatements());
			}
		}
    }

	public void rebuildTree(SQLScriptModel model) {
		synchronized (monitor) {
			if (model == null) {
				items = null;
				return;
			}
			items = createItems(model);
		}
	}

	public Item tryToFindByOffset(int offset) {
		synchronized (monitor) {
			if (items==null){
				return null;
			}
			for (Object oitem: items){
				if (!(oitem instanceof Item)){
					continue;
				}
				Item item = (Item) oitem;
				int itemStart = item.getOffset();
				int itemEnd = item.getEndOffset();
				if (offset >= itemStart && offset<=itemEnd ){
					return item;
				}
			}
			
		}
		return null;
	}

}
