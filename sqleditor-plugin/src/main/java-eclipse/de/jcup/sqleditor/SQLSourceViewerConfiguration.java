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

import static de.jcup.sqleditor.SQLEditorUtil.*;
import static de.jcup.sqleditor.document.SQLDocumentIdentifiers.*;
import static de.jcup.sqleditor.preferences.SQLEditorSyntaxColorPreferenceConstants.*;
import static de.jcup.sqleditor.preferences.SQLEditorSyntaxStylePreferenceConstants.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.URLHyperlinkDetector;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.quickassist.IQuickAssistAssistant;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import de.jcup.sqleditor.document.SQLDocumentIdentifier;
import de.jcup.sqleditor.document.SQLDocumentIdentifiers;
import de.jcup.sqleditor.preferences.SQLEditorPreferences;
import de.jcup.sqleditor.preferences.SQLEditorSyntaxColorPreferenceConstants;
import de.jcup.sqleditor.preferences.SQLEditorSyntaxStylePreferenceConstants;
import de.jcup.sqleditor.presentation.PresentationSupport;
import de.jcup.sqleditor.presentation.SQLDefaultTextScanner;

/**
 * 
 * @author Albert Tregnaghi
 *
 */
public class SQLSourceViewerConfiguration extends TextSourceViewerConfiguration {

    private SQLDefaultTextScanner scanner;
    private ColorManager colorManager;

    private TextAttribute defaultTextAttribute;
    private SQLEditorAnnotationHoover annotationHoover;
    private IAdaptable adaptable;
    private ContentAssistant contentAssistant;
    private SQLEditorSimpleWordContentAssistProcessor contentAssistProcessor;

    /**
     * Creates configuration by given adaptable
     * 
     * @param adaptable must provide {@link ColorManager} and {@link IFile}
     */
    public SQLSourceViewerConfiguration(IAdaptable adaptable) {
        IPreferenceStore generalTextStore = EditorsUI.getPreferenceStore();
        this.fPreferenceStore = new ChainedPreferenceStore(new IPreferenceStore[] { getPreferences().getPreferenceStore(), generalTextStore });

        Assert.isNotNull(adaptable, "adaptable may not be null!");
        this.annotationHoover = new SQLEditorAnnotationHoover();

        this.contentAssistant = new ContentAssistant();
        contentAssistProcessor = new SQLEditorSimpleWordContentAssistProcessor();
        contentAssistant.enableColoredLabels(true);

        contentAssistant.setContentAssistProcessor(contentAssistProcessor, IDocument.DEFAULT_CONTENT_TYPE);
        for (SQLDocumentIdentifier identifier : SQLDocumentIdentifiers.values()) {
            contentAssistant.setContentAssistProcessor(contentAssistProcessor, identifier.getId());
        }

        contentAssistant.addCompletionListener(contentAssistProcessor.getCompletionListener());

        this.colorManager = adaptable.getAdapter(ColorManager.class);
        Assert.isNotNull(colorManager, " adaptable must support color manager");
        this.defaultTextAttribute = new TextAttribute(colorManager.getColor(getColor(COLOR_NORMAL_TEXT)));
        this.adaptable = adaptable;
    }

    public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
        return contentAssistant;
    }

    @Override
    public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
        if (SQLDocumentIdentifiers.isContaining(contentType)) {
            return new SQLTextHover();
        } else {
            return super.getTextHover(sourceViewer, contentType);
        }
    }

    @Override
    public IQuickAssistAssistant getQuickAssistAssistant(ISourceViewer sourceViewer) {
        /*
         * currently we avoid the default quick assistence parts (spell checking etc.)
         */
        return null;
    }

    public IReconciler getReconciler(ISourceViewer sourceViewer) {
        /*
         * currently we avoid the default reconciler mechanism parts (spell checking
         * etc.)
         */
        return null;
    }

    @Override
    public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
        return annotationHoover;
    }

    private class SQLEditorAnnotationHoover extends DefaultAnnotationHover {
        @Override
        protected boolean isIncluded(Annotation annotation) {
            if (annotation instanceof MarkerAnnotation) {
                return true;
            }
            /* we do not support other annotations */
            return false;
        }
    }

    @Override
    public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
        /* @formatter:off */
		return allIdsToStringArray( 
				IDocument.DEFAULT_CONTENT_TYPE);
		/* @formatter:on */
    }

    @Override
    public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
        if (sourceViewer == null)
            return null;

        return new IHyperlinkDetector[] { new URLHyperlinkDetector(), new SQLHyperlinkDetector(adaptable) };
    }

    private RGB getColor(SQLEditorSyntaxColorPreferenceConstants constant) {
        return getPreferences().getColor(constant);
    }

    private int getStyle(SQLEditorSyntaxStylePreferenceConstants constant) {
        return getPreferences().getStyle(constant);
    }

    @Override
    public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
        PresentationReconciler reconciler = new PresentationReconciler();

        addDefaultPresentation(reconciler);

        addPresentation(reconciler, DATA_TYPE_KEYWORDS.getId(), getColor(COLOR_DATA_TYPE_KEYWORD), getStyle(STYLE_DATA_TYPE_KEYWORD));
        addPresentation(reconciler, STATEMENT_KEYWORD.getId(), getColor(COLOR_STATEMENT_KEYWORD), getStyle(STYLE_STATEMENT_KEYWORD));
        addPresentation(reconciler, STATEMENT_TARGET_KEYWORD.getId(), getColor(COLOR_TARGET_KEYWORD),  getStyle(STYLE_TARGET_KEYWORD));
        addPresentation(reconciler, WHERE_BLOCK_KEYWORD.getId(), getColor(COLOR_WHERE_BLOCK_KEYWORD), getStyle(STYLE_WHERE_BLOCK_KEYWORD));
        addPresentation(reconciler, SCHEMA_KEYWORD.getId(), getColor(COLOR_TARGET_KEYWORD), getStyle(STYLE_TARGET_KEYWORD));
        addPresentation(reconciler, FUNCTION_KEYWORDS.getId(), getColor(COLOR_FUNCTION_KEYWORDS), getStyle(STYLE_FUNCTION_KEYWORDS));

        addPresentation(reconciler, DOUBLE_STRING.getId(), getColor(COLOR_DOUBLE_QUOTES), getStyle(STYLE_DOUBLE_QUOTES));
        addPresentation(reconciler, SINGLE_STRING.getId(), getColor(COLOR_SINGLE_QUOTES), getStyle(STYLE_SINGLE_QUOTES));
        addPresentation(reconciler, COMMENT.getId(), getColor(COLOR_COMMENT), getStyle(STYLE_COMMENT));

        if (SQLEditorPreferences.getInstance().isCustomKeywordSupportEnabled()) {
            addPresentation(reconciler, CUSTOM_KEYWORDS.getId(), getColor(COLOR_CUSTOM_KEYWORDS), getStyle(STYLE_CUSTOM_KEYWORDS));
        }
        return reconciler;
    }

    private void addDefaultPresentation(PresentationReconciler reconciler) {
        DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getGradleDefaultTextScanner());
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
    }

    private IToken createColorToken(RGB rgb) {
        Token token = new Token(new TextAttribute(colorManager.getColor(rgb)));
        return token;
    }

    private void addPresentation(PresentationReconciler reconciler, String id, RGB rgb, int style) {
        TextAttribute textAttribute = new TextAttribute(colorManager.getColor(rgb), defaultTextAttribute.getBackground(), style);
        PresentationSupport presentation = new PresentationSupport(textAttribute);
        reconciler.setDamager(presentation, id);
        reconciler.setRepairer(presentation, id);
    }

    private SQLDefaultTextScanner getGradleDefaultTextScanner() {
        if (scanner == null) {
            scanner = new SQLDefaultTextScanner(colorManager);
            updateTextScannerDefaultColorToken();
        }
        return scanner;
    }

    public void updateTextScannerDefaultColorToken() {
        if (scanner == null) {
            return;
        }
        RGB color = getColor(COLOR_NORMAL_TEXT);
        scanner.setDefaultReturnToken(createColorToken(color));
    }

}