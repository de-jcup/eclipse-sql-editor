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