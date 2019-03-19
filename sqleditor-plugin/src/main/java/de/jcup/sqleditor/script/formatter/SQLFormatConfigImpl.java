package de.jcup.sqleditor.script.formatter;

public class SQLFormatConfigImpl implements SQLFormatConfig {
    boolean breakingSelectProjection;
    int indent;
    boolean transformKeywordsToUppercase;

    public boolean isBreakingSelectProjection() {
        return breakingSelectProjection;
    }

    @Override
    public boolean isTransformingKeywordsToUpperCase() {
        return transformKeywordsToUppercase;
    }

    @Override
    public int getIndent() {
        return indent;
    }
}