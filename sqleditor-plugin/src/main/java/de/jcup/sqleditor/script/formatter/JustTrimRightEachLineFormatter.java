package de.jcup.sqleditor.script.formatter;

import de.jcup.eclipse.commons.SimpleStringUtils;

public class JustTrimRightEachLineFormatter {

    public String format(String sql) {
        if (sql == null) {
            return null;
        }
        String[] lines = sql.split("\n");
        StringBuilder sb = new StringBuilder();
        int lineNumber = 0;
        for (String line : lines) {
            lineNumber++;
    
            sb.append(SimpleStringUtils.trimRight(line));
            boolean notLastLine = lineNumber < lines.length;
            if (notLastLine) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
