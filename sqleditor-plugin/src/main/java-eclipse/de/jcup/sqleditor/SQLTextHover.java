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
package de.jcup.sqleditor;

import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Shell;

import de.jcup.eclipse.commons.SimpleStringUtils;
import de.jcup.eclipse.commons.WordEndDetector;
import de.jcup.eclipse.commons.keyword.DocumentKeyWord;
import de.jcup.eclipse.commons.keyword.TooltipTextSupport;
import de.jcup.eclipse.commons.ui.EclipseUtil;
import de.jcup.eclipse.commons.ui.ReducedBrowserInformationControl;
import de.jcup.sqleditor.document.keywords.SQLKeyWords;
import de.jcup.sqleditor.preferences.SQLEditorPreferenceConstants;
import de.jcup.sqleditor.preferences.SQLEditorPreferences;
import de.jcup.sqleditor.preferences.SQLEditorSyntaxColorPreferenceConstants;

public class SQLTextHover implements ITextHover, ITextHoverExtension {

    private IInformationControlCreator creator;
    private String bgColor;
    private String fgColor;
    private String commentColorWeb;
    private WordEndDetector wordEndDetector = new SQLStatementWordEndDetector();

    private class SQLStatementWordEndDetector implements WordEndDetector {

        @Override
        public boolean isWordEnd(char c) {
            return Character.isWhitespace(c) || c == '(' || c == ';' || c == ',';
        }

    }

    @Override
    public IInformationControlCreator getHoverControlCreator() {
        if (creator == null) {
            creator = new GradleTextHoverControlCreator();
        }
        return creator;
    }

    @Override
    public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
        SQLEditorPreferences preferences = SQLEditorPreferences.getInstance();
        boolean tooltipsEnabled = preferences.getBooleanPreference(SQLEditorPreferenceConstants.P_TOOLTIPS_ENABLED);
        if (!tooltipsEnabled) {
            return null;
        }

        if (bgColor == null || fgColor == null) {

            StyledText textWidget = textViewer.getTextWidget();
            if (textWidget != null) {

                EclipseUtil.getSafeDisplay().syncExec(new Runnable() {

                    @Override
                    public void run() {
                        bgColor = ColorUtil.convertToHexColor(textWidget.getBackground());
                        fgColor = ColorUtil.convertToHexColor(textWidget.getForeground());
                    }
                });
            }

        }
        if (commentColorWeb == null) {
            commentColorWeb = preferences.getWebColor(SQLEditorSyntaxColorPreferenceConstants.COLOR_COMMENT);
        }

        IDocument document = textViewer.getDocument();
        if (document == null) {
            return "";
        }
        String text = document.get();
        if (text == null) {
            return "";
        }
        int offset = hoverRegion.getOffset();

        String word = SimpleStringUtils.nextReducedVariableWord(text, offset, wordEndDetector);
        if (word.isEmpty()) {
            return "";
        }

        for (DocumentKeyWord keyword : SQLKeyWords.getAllDefaultKeywords()) {
            if (word.equalsIgnoreCase(keyword.getText())) {
                return buildHoverInfo(keyword);
            }
        }
        if (preferences.isCustomKeywordSupportEnabled()) {
            for (DocumentKeyWord keyword : preferences.getCustomKeywords()) {
                if (word.equalsIgnoreCase(keyword.getText())) {
                    return buildHoverInfo(keyword);
                }
            }

        }

        return "";
    }

    private String buildHoverInfo(DocumentKeyWord keyword) {
        String link = keyword.getLinkToDocumentation();
        String tooltip = keyword.getTooltip();

        if (isEmpty(tooltip) && isEmpty(link)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<style>");
        sb.append(TooltipTextSupport.getTooltipCSS());
        addCSStoBackgroundTheme(sb);
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body>");
        if (!isEmpty(link)) {
            sb.append("Detailed information available at: <a href='" + link + "' target='_blank'>" + link + "</a><br><br>");
        }

        sb.append("<u>Offline description:</u>");
        if (isEmpty(tooltip)) {
            sb.append("<b>Not available</b>");
        } else {
            if (TooltipTextSupport.isHTMLToolTip(tooltip)) {
                /* it's already a HTML variant - so just keep as is */
                sb.append(tooltip);
            } else {
                /* plain tokenText */
                sb.append("<pre class='preWrapEnabled'>");
                sb.append(tooltip);
                sb.append("</pre>");
            }

        }
        sb.append("</body>");
        return sb.toString();
    }

    private void addCSStoBackgroundTheme(StringBuilder sb) {
        if (bgColor == null) {
            return;
        }
        if (fgColor == null) {
            return;
        }
        sb.append("body {");
        sb.append("background-color:").append(bgColor).append(";");
        sb.append("color:").append(fgColor).append(";");
        sb.append("}");

    }

    private boolean isEmpty(String string) {
        if (string == null) {
            return true;
        }
        return string.isEmpty();
    }

    @Override
    public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
        return new Region(offset, 0);
    }

    private class GradleTextHoverControlCreator implements IInformationControlCreator {

        @Override
        public IInformationControl createInformationControl(Shell parent) {
            if (ReducedBrowserInformationControl.isAvailableFor(parent)) {
                ReducedBrowserInformationControl control = new ReducedBrowserInformationControl(parent);
                return control;
            } else {
                return new DefaultInformationControl(parent, true);
            }
        }
    }

}
