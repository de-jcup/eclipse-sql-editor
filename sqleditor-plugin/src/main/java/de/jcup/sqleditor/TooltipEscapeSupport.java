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

public class TooltipEscapeSupport {
    private static final String ESCAPE_COMMA = "_§_";

    public String escapeCommasInTooltip(String tooltip) {
        if (tooltip == null) {
            return "";
        }
        return tooltip.replaceAll(",", ESCAPE_COMMA);
    }

    public String fetchOriginTooltipFromEscapedString(String escapedTooltip) {
        if (escapedTooltip == null) {
            return "";
        }
        return escapedTooltip.replaceAll(ESCAPE_COMMA, ",");
    }
}
