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
package de.jcup.sqleditor.document.keywords;

import de.jcup.eclipse.commons.keyword.TooltipTextSupport;

/*
 * 
 * SQL build in functions.
 * Sources: https://www.cs.utexas.edu/~mitra/csFall2015/cs329/lectures/sqlFunc.html, 
 * https://en.wikipedia.org/wiki/SQL_syntax
 */
public enum SQLFunctionKeywords implements SQLKeyword {

    /* numeric */
    ABS(null, "ABS(m), absoute value of m", null), 
    
    MOD(null,"MOD(m,n) , Remainder of m divided by n, m = value, n = divisor", null),
    
    POWER(null,"POWER(m,n), m raised to the nth power, m = value, n = exponent", null),
    
    ROUND(null,
            "ROUND(m[,n]), m rounded to the nth decimal place, m = value, n = number of decimal places, default 0\n\nDate variant:\n"
                    + "ROUND ( d [, fmt ] ), d = date value, fmt = format for string,  Date d rounded as specified by the format",
            null),
    
    TRUNC(null,
            "TRUNC(m[,n]), m truncated to the nth decimal place, m = value, n = number of decimal places, default 0\n\nDate variant:\n"
                    + "TRUNC ( d [, fmt ] ), d = date value, fmt = format for string,  Date d truncated as specified by the format",
            null),
    SIN(null, "SIN(n) sine(n), n = angle expressed in radians", null), 
    
    COS(null, "COS(n) cosine(n), n = angle expressed in radians", null), 
    
    TAN(null,"TAN(n) tan(n), n = angle expressed in radians", null),
    
    ASIN(null, "ASIN(n) arc sine of n in the range -π/2 to +π/2, n is in the range -1 to +1", null), 
    
    ACOS(null,"ACOS(n) arc cosine of n in the range -π/2 to +π/2, n is in the range -1 to +1", null),
    
    ATAN(null, "ATAN(n) arc tangent of n in the range -π/2 to + π/2, n is unbounded", null), 
    
    SINH(null,"SINH(n) hyperbolic sine of n", null), 
    
    COSH(null,"COSH(n) hyperbolic cosine of n", null),
    
    TANH(null, "TANH(n) hyperbolic tan of n", null), 
    
    SQRT(null,"SQRT(n) positive square root of n",null), 
    
    EXP(null,"EXP(n) e raised to the power n",null), 
    
    LN(null,"LN(n) natural logarithm of n",null),
    
    LOG(null, "LOG(n2,n1)logarithm of n1, base n2, base n2 any positive value other than 0 or 1, n1 any positive value",null), 
    
    CEIL(null,"CEIL(n) smallest integer greater than or equal to n",null),
    
    FLOOR(null, "FLOOR(n) greatest integer smaller than or equal to n", null), 
    
    SIGN(null,"SIGN(n) returns -1 if n < 0, 0 if n = 0, and 1 if n > 0 ",null),

    /* string functions */
    INITCAP(null, "INITCAP (s) s = character string, First letter of each word is changed to uppercase and all other letters are in lower case.", null),
    
    LOWER(null, "LOWER ( s ) s = character string, All letters are changed to lowercase.", null), 
    
    UPPER(null,"UPPER ( s ) s = character string, All letters are changed to uppercase.", null),
    
    CONCAT(null, "CONCAT ( s1, s2 ) s1 and s2 are character strings, Concatenation of s1 and s2. Equivalent to s1 || s2", null),
    
    LPAD(null, "LPAD ( s1, n [, s2] ), s1 and s2 are character strings and n is an integer value, Returns s1 right justified and padded left with n characters from s2; s2 defaults to space.", null),
    
    RPAD(null, "RPAD ( s1, n [, s2] ), s1 and s2 are character strings and n is an integer value, Returns s1 left justified and padded right with n characters from s2; s2 defaults to space.", null),
    
    LTRIM(null, "LTRIM ( s [, set ] ), s is a character string and set is a set of characters, Returns s with characters removed up to the first character not in set; defaults to space", null),
    
    RTRIM(null, "RTRIM ( s [, set ] ), s is a character string and set is a set of characters, Returns s with final characters removed after the last character not in set; defaults to space", null),
    
    REPLACE(null,
            "REPLACE ( s, search_s [, replace_s ] ), s = character string, search_s = target string, replace_s = replacement string, Returns s with every occurrence of search_s in s replaced by replace_s; default removes search_s",
            null),
    
    SUBSTR(null,
            "SUBSTR ( s, m [, n ] ), s = character string, m = beginning position, n = number of characters, Returns a substring from s, beginning in position m and n characters long; default returns to end of s.",
            null),
    
    LENGTH(null, "LENGTH ( s ), s = character string, Returns the number of characters in s.", null),
    
    INSTR(null,
            "INSTR ( s1, s2 [, m [, n ] ] ), s1 and s2 are character strings, m = beginning position, n = occurrence of s2 in s1, Returns the position of the nth occurrence of s2 in s1, beginning at position m, both m and n default to 1.",
            null),

    /* String / Number Conversion Functions */
    NANVL(null, "NANVL ( n2, n1 ) , n1, n2 = value, if (n2 = NaN) returns n1 else returns n2", null),
    
    TO_CHAR(null,
            "TO_CHAR ( m [, fmt ] ), m = numeric value, fmt = format,  Number m converted to character string as specified by the format\n\nDate variant:\n"
                    + "TO_CHAR ( d [, fmt ] ), d = date value, fmt = format for string,  The date d converted to a string in the given format",
            null),
    TO_NUMBER(null, "TO_NUMBER ( s [, fmt ] ), s = character string, fmt = format, Character string s converted to a number as specified by the format", null),

    /* Group functions */
    AVG(null, "AVG ( [ DISTINCT | ALL ] col ), col = column name,  The average value of that column", null),
    
    COUNT(null,
            "COUNT ( * ),  none, Number of rows returned including duplicates and NULLs\n"
                    + "COUNT [ DISTINCT | ALL ] col ), col = column name,  Number of rows where the value of the column is not NULL",
            null),
    MAX(null, "MAX ( [ DISTINCT | ALL ] col ), col = column name,  Maximum value in the column", null), 
    
    MIN(null,"MIN ( [ DISTINCT | ALL ] col ), col = column name,  Minimum value in the column", null),
    
    SUM(null, "SUM ( [ DISTINCT | ALL ] col ), col = column name,  Sum of the values in the column", null),
    
    CORR(null, "CORR ( e1, e2 ),  e1 and e2 are column names, Correlation coefficient between the two columns after eliminating nulls", null),
    
    MEDIAN(null, "MEDIAN ( col ), col = column name,  Middle value in the sorted column, interpolating if necessary", null),
    
    STDDEV(null, "STDDEV ( [ DISTINCT | ALL ] col ),  col = column name,  Standard deviation of the column ignoring NULL values", null),
    
    VARIANCE(null, "VARIANCE ( [ DISTINCT | ALL ] col ),  col = column name,  Variance of the column ignoring NULL values", null),

    /* Date and Time Functions */
    ADD_MONTHS(null, "ADD_MONTHS ( d, n ),  d = date, n = number of months, Date d plus n months", null),
    
    LAST_DAY(null, "LAST_DAY ( d ), d = date, Date of the last day of the month containing d", null),
    
    MONTHS_BETWEEN(null, "MONTHS_BETWEEN ( d, e ),  d and e are dates,  Number of months by which e precedes d", null),
    
    NEW_TIME(null, "NEW_TIME ( d, a, b ), d = date, a = time zone (char), b = time zone (char), The date and time in time zone b when date d is for time zone a", null),
    
    NEXT_DAY(null, "NEXT_DAY ( d, day ),  d = date, day = day of the week,  Date of the first day of the week after d", null), 
    
    SYSDATE(null, "SYSDATE,  none, Current date and time", null),
    
    GREATEST(null, "GREATEST ( d1, d2, ..., dn ), d1 ... dn = list of dates,  Latest of the given dates", null),
    
    LEAST(null, "LEAST ( d1, d2, ..., dn ),  d1 ... dn = list of dates,  Earliest of the given dates", null),

    /* Date Conversion Functions */
    TO_DATE(null, "TO_DATE ( s [, fmt ] ), s = character string, fmt = format for date,  String s converted to a date value", null),

    ;
    private String text;
    private String linkToDocumentation;
    private String tooltip;

    SQLFunctionKeywords() {
        this(null, null,null);
    }

//    SQLFunctionKeywords(String linkToDocumentation) {
//        this(null, linkToDocumentation);
//    }
//
//    SQLFunctionKeywords(String text, String linkToDocumentation) {
//        this(text, null, linkToDocumentation);
//    }

    SQLFunctionKeywords(String text, String tooltip, String linkToDocumentation) {
        this.text = text;
        this.tooltip = tooltip;
        this.linkToDocumentation = linkToDocumentation;
    }

    @Override
    public String getText() {
        if (text == null) {
            text = name().toLowerCase();
        }
        return text;
    }

    @Override
    public boolean isBreakingOnEof() {
        return true;
    }

    @Override
    public String getLinkToDocumentation() {
        if (linkToDocumentation != null) {
            return linkToDocumentation;
        }
        return "https://en.wikipedia.org/wiki/SQL_syntax";
    }

    @Override
    public String getTooltip() {
        if (tooltip != null) {
            return tooltip;
        }
        return TooltipTextSupport.getTooltipText(name().toLowerCase());
    }

    @Override
    public boolean isHavingParameters() {
        return true;
    }
}
