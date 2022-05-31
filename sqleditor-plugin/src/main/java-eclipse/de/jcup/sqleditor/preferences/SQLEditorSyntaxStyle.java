package de.jcup.sqleditor.preferences;

import org.eclipse.swt.SWT;

public enum SQLEditorSyntaxStyle implements PreferenceIdentifiable {

    NONE("none", "No style defined", SWT.NONE),

    BOLD("bold", "Bold", SWT.BOLD),

    ITALIC("italic", "Italic", SWT.ITALIC),
    
    BOLD_ITALIC("bold_italic", "Bold + italic", SWT.BOLD | SWT.ITALIC);

    ;

    private String id;
    private String description;
    private int swtStyle;

    private SQLEditorSyntaxStyle(String id, String description, int swtStyle) {
        this.id = id;
        this.description = description;
        this.swtStyle = swtStyle;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getSWTStyle() {
        return swtStyle;
    }
    
    public static SQLEditorSyntaxStyle fromId(String style) {
        for (SQLEditorSyntaxStyle constant: SQLEditorSyntaxStyle.values()) {
            if (constant.getId().contentEquals(style)){
                return constant;
            }
        }
        return null;
    }
}
