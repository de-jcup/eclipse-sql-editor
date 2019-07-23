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
