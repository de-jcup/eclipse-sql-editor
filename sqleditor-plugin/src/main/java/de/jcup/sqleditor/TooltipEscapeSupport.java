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
