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

import de.jcup.sqleditor.SQLTooltipModel.Example;
import de.jcup.sqleditor.SQLTooltipModel.Page;
import de.jcup.sqleditor.document.keywords.SQLKeyword;
import de.jcup.sqleditor.document.keywords.SimpleTooltipTextHolder;

public class SQLTooltipGenerator {

    public SQLTooltipGenerator(SQLTooltipModel model) {
        this.model = model;
    }

    private SQLTooltipModel model;

    String createDescription(SQLKeyword keyword) {
        Page result = model.map.get(keyword);
        if (result == null) {
            return createFallbackForkeyWord(keyword);
        }
        String html = mainHeadLine(keyword.getText().toUpperCase());
        if (result.getDescription() != null) {
            html += result.getDescription() + "\n\n";
        }

        if (result.getSyntax() != null) {
            html += "<b>Syntax:</b>\n";
            html += result.getSyntax() + "\n\n";
        }
        if (result.getExamples().size() > 0) {
            html += examplesMainHeadLine();
        }
        int nr = 1;
        for (Example example : result.getExamples()) {
            html += example(example, nr++);
        }
        return html;
    }

    private String createFallbackForkeyWord(SQLKeyword keyword) {
        if (keyword instanceof SimpleTooltipTextHolder) {
            SimpleTooltipTextHolder stth = (SimpleTooltipTextHolder) keyword;
            return stth.getSimpleTooltipText();
        }
        return null;
    }

    private String mainHeadLine(String text) {
        return headline(3, text);
    }

    private String examplesMainHeadLine() {
        return "<hr/>" + headline(4, "Examples");
    }

    private String example(Example example, int nr) {
        return examplesHeadLine(example, nr) + "" + example.sql + "\n\n" + example.description;
    }

    private String examplesHeadLine(Example example, int nr) {
        return headline(5, "Example " + nr + ":" + example.name);
    }

    private String headline(int number, String text) {
        return "<h" + number + ">" + text + "</h" + number + ">";
    }
}
