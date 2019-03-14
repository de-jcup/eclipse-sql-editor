package de.jcup.sqleditor.script;

public class SQLScriptModelBuilder {

	private class SQLscriptContext {
		int pos = 0;
		int labelStart = 0;
		int posAtLine = 0;
	}

	public SQLScriptModel build(String text) {
		SQLScriptModel model = new SQLScriptModel();
		if (text == null || text.trim().length() == 0) {
			return model;
		}
		String inspect = text + "\n";// a simple workaround to get the last
										// label accessed too, if there is no
										// next line...
		/*
		 * very simple approach: a label is identified by being at first
		 * position of line
		 */
		StringBuilder labelSb = null;
		SQLscriptContext context = new SQLscriptContext();
		for (char c : inspect.toCharArray()) {
			if (c == '\n' || c == '\r') {
				/* terminate search - got the label or none*/
				addLabelDataWhenExisting(model, labelSb, context);
				labelSb = null;
				continue;
			}
			if (c == ':') {
				if (context.posAtLine == 0) {
					labelSb = new StringBuilder();
					context.labelStart = context.pos;
				} else {
					/* :: detected - reset */
					labelSb = null;
				}
			} else {
				if (labelSb != null) {
					if (Character.isWhitespace(c)) {
						addLabelDataWhenExisting(model, labelSb, context);
						labelSb = null;
						continue;
					} else {
						labelSb.append(c);
					}
				}
			}
			context.pos++;
			context.posAtLine++;
		}

		return model;
	}

	protected void addLabelDataWhenExisting(SQLScriptModel model, StringBuilder labelSb, SQLscriptContext context) {
		if (labelSb != null) {
			String labelName = labelSb.toString().trim();
			if (! labelName.isEmpty()){

				SQLCommand label = new SQLCommand(labelName);
				label.pos = context.labelStart + 1;
				label.end = context.pos - 1;
				
				model.getLabels().add(label);
			}
		}
		context.pos++;
		context.posAtLine = 0;
	}

}
