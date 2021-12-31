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
package de.jcup.sqleditor.preferences;

import de.jcup.sqleditor.document.keywords.SQLKeyword;

public class SqlEditorCustomKeyDefinition implements SQLKeyword{
    private String identifier;
    private String tooltip;

    public SqlEditorCustomKeyDefinition() {
        this(null, null);
    }

    public SqlEditorCustomKeyDefinition(String identifier, String tooltip) {
        if (identifier == null) {
            identifier = "unknown";
        }
        if (tooltip == null) {
            tooltip = "unknown";
        }
        this.identifier = identifier;
        this.tooltip = tooltip;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
        result = prime * result + ((tooltip == null) ? 0 : tooltip.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SqlEditorCustomKeyDefinition other = (SqlEditorCustomKeyDefinition) obj;
        if (identifier == null) {
            if (other.identifier != null)
                return false;
        } else if (!identifier.equals(other.identifier))
            return false;
        return true;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getLinkToDocumentation() {
        return null;
    }

    @Override
    public String getText() {
        return identifier;
    }

    @Override
    public boolean isBreakingOnEof() {
        return true;
    }

    @Override
    public boolean isHavingParameters() {
        return false;
    }

}