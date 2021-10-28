/*
 * Copyright 2021 Albert Tregnaghi
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
package de.jcup.sqleditor.preferences;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

class SQLEditorCustomKeyDialog extends TitleAreaDialog {

	private Text txtIdentifier;
	private Text txtTooltip;


	private SqlEditorCustomKeyDefinition definition;

	public SQLEditorCustomKeyDialog(Shell parentShell, SqlEditorCustomKeyDefinition definition) {
		super(parentShell);
		if (definition==null){
			definition = new SqlEditorCustomKeyDefinition("YourSQLKeyword", "define a tooltip...");
		}
		this.definition=definition;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Define a custom SQL keyword");
		setMessage("Create or change your custom keyword definition", IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createIdentifierTextfields(container);
		createTooltipTextfields(container);

		return area;
	}

	private void createIdentifierTextfields(Composite container) {
		Label lblIdentifier = new Label(container, SWT.NONE);
		lblIdentifier.setText("Identifier");

		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;

		txtIdentifier = new Text(container, SWT.BORDER);
		txtIdentifier.setLayoutData(data);
		txtIdentifier.setText(definition.getIdentifier());
	}
	

    private void createTooltipTextfields(Composite container) {
        Label lblTooltip = new Label(container, SWT.NONE);
        lblTooltip.setText("Tooltip");

        GridData data = new GridData();
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;

        txtTooltip = new Text(container, SWT.BORDER);
        txtTooltip.setLayoutData(data);
        txtTooltip.setText(definition.getTooltip());
    }

	@Override
	protected boolean isResizable() {
		return true;
	}

	private void saveInput() {
		String identifier = txtIdentifier.getText();
		definition.setIdentifier(identifier);
		
		String tooltip = txtTooltip.getText();
        definition.setTooltip(tooltip);
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public SqlEditorCustomKeyDefinition getDefinition() {
		return definition;
	}
}