package de.jcup.sqleditor.script.formatter;

public interface SQLFormatConfig {
    public static final SQLFormatConfig DEFAULT = builder().build();

    public boolean isBreakingSelectProjection();

    public boolean isTransformingKeywordsToUpperCase();
    
    public int getIndent();
    

    public static SQLFormatConfigBuilder builder() {
        return new SQLFormatConfigBuilder();
    }

    public class SQLFormatConfigBuilder {
        private boolean breakingSelectProjection = true;
        private int indent = 3;
        private boolean transformKeywordsToUppercase = false;

        public SQLFormatConfigBuilder setBreakingSelectProjection(boolean breakingSelectProjection) {
            this.breakingSelectProjection = breakingSelectProjection;
            return this;
        }

        public SQLFormatConfigBuilder setTransformKeywordsToUppercase(boolean transformKeywordsToUppercase) {
            this.transformKeywordsToUppercase = transformKeywordsToUppercase;
            return this;
        }

        public SQLFormatConfigBuilder setIndent(int indent) {
            this.indent = indent;
            return this;
        }

        public SQLFormatConfig build() {
            SQLFormatConfigImpl impl = new SQLFormatConfigImpl();
            impl.breakingSelectProjection = breakingSelectProjection;
            impl.indent = indent;
            impl.transformKeywordsToUppercase=transformKeywordsToUppercase;
            return impl;

        }

    }


}