-- see https://www.w3schools.com/sql/sql_ref_keywords.asp

-- This is not correct SQL but simply a copy of the list to simply find syntax highlighting problems
-- https://www.cs.utexas.edu/~mitra/csFall2015/cs329/lectures/sqlFunc.html


--Numeric Functions 

ABS ( m )-- m = value-- Absolute value of m
MOD ( m, n )----m = value, n = divisor--Remainder of m divided by n
POWER ( m, n )--m = value, n = exponent---- m raised to the nth power
ROUND ( m [, n ] )--m = value, n = number of decimal places, default 0--m rounded to the nth decimal place
TRUNC ( m [, n ] )--m = value, n = number of decimal places, default 0--m truncated to the nth decimal place
SIN ( n )-- n = angle expressed in radians--sine (n)
COS ( n )-- n = angle expressed in radians--cosine (n)
TAN ( n )-- n = angle expressed in radians--tan (n)
ASIN ( n )--n is in the range -1 to +1--arc sine of n in the range -π/2 to +π/2
ACOS ( n )--n is in the range -1 to +1--arc cosine of n in the range 0 to π
ATAN ( n )--n is unbounded--arc tangent of n in the range -π/2 to + π/2
SINH ( n )--n = value-- hyperbolic sine of n
COSH ( n )--n = value-- hyperbolic cosine of n
TANH ( n )--n = value-- hyperbolic tangent of n
SQRT ( n )--n = value-- positive square root of n
EXP ( n )-- n = value-- e raised to the power n
LN ( n )----n > 0-- natural logarithm of n
LOG ( n2, n1 )--base n2 any positive value other than 0 or 1, n1 any positive value---- logarithm of n1, base n2
CEIL ( n )--n = value-- smallest integer greater than or equal to n
FLOOR ( n )---- n = value-- greatest integer smaller than or equal to n
SIGN ( n )--n = value-- -1 if n < 0, 0 if n = 0, and 1 if n > 0 

-- Here are some examples of the use of some of these numeric functions:

select round (83.28749, 2) from dual;

select sqrt (3.67) from dual;

select power (2.512, 5) from dual;



-- String Functions 
INITCAP ( s )-- s = character string----First letter of each word is changed to uppercase and all other letters are in lower case.
LOWER ( s )---- s = character string----All letters are changed to lowercase.
UPPER ( s )---- s = character string----All letters are changed to uppercase.
CONCAT ( s1, s2 )-- s1 and s2 are character strings---- Concatenation of s1 and s2. Equivalent to s1 || s2
LPAD ( s1, n [, s2] )-- s1 and s2 are character strings and n is an integer value-- Returns s1 right justified and padded left with n characters from s2; s2 defaults to space.
RPAD ( s1, n [, s2] )-- s1 and s2 are character strings and n is an integer value-- Returns s1 left justified and padded right with n characters from s2; s2 defaults to space.
LTRIM ( s [, set ] )----s is a character string and set is a set of characters--Returns s with characters removed up to the first character not in set; defaults to space
RTRIM ( s [, set ] )----s is a character string and set is a set of characters--Returns s with final characters removed after the last character not in set; defaults to space
REPLACE ( s, search_s [, replace_s ] )--s = character string, search_s = target string, replace_s = replacement string--Returns s with every occurrence of search_s in s replaced by replace_s; default removes search_s
SUBSTR ( s, m [, n ] )--s = character string, m = beginning position, n = number of characters--Returns a substring from s, beginning in position m and n characters long; default returns to end of s.
LENGTH ( s )----s = character string----Returns the number of characters in s.
INSTR ( s1, s2 [, m [, n ] ] )--s1 and s2 are character strings, m = beginning position, n = occurrence of s2 in s1---- Returns the position of the nth occurrence of s2 in s1, beginning at position m, both m and n default to 1.

-- String / Number Conversion Functions 
NANVL ( n2, n1 ) -- n1, n2 = value --if (n2 = NaN) returns n1 else returns n2
TO_CHAR ( m [, fmt ] ) --m = numeric value, fmt = format -- Number m converted to character string as specified by the format
TO_NUMBER ( s [, fmt ] ) --s = character string, fmt = format --Character string s converted to a number as specified by the format

-- Group functions:
AVG ( [ DISTINCT | ALL ] col ) --col = column name -- The average value of that column
COUNT ( * ) -- none --Number of rows returned including duplicates and NULLs
COUNT ( [ DISTINCT | ALL ] col ) --col = column name -- Number of rows where the value of the column is not NULL
MAX ( [ DISTINCT | ALL ] col ) --col = column name -- Maximum value in the column
MIN ( [ DISTINCT | ALL ] col ) --col = column name -- Minimum value in the column
SUM ( [ DISTINCT | ALL ] col ) --col = column name -- Sum of the values in the column
CORR ( e1, e2 ) -- e1 and e2 are column names --Correlation coefficient between the two columns after eliminating nulls
MEDIAN ( col ) --col = column name -- Middle value in the sorted column, interpolating if necessary
STDDEV ( [ DISTINCT | ALL ] col ) -- col = column name -- Standard deviation of the column ignoring NULL values
VARIANCE ( [ DISTINCT | ALL ] col ) -- col = column name -- Variance of the column ignoring NULL values

-- Date and Time Functions -- 
ADD_MONTHS ( d, n ) -- d = date, n = number of months --Date d plus n months
LAST_DAY ( d ) --d = date --Date of the last day of the month containing d
MONTHS_BETWEEN ( d, e ) -- d and e are dates -- Number of months by which e precedes d
NEW_TIME ( d, a, b ) --d = date, a = time zone (char), b = time zone (char) --The date and time in time zone b when date d is for time zone a
NEXT_DAY ( d, day ) -- d = date, day = day of the week -- Date of the first day of the week after d
SYSDATE -- none --Current date and time
GREATEST ( d1, d2, ..., dn ) --d1 ... dn = list of dates -- Latest of the given dates
LEAST ( d1, d2, ..., dn ) -- d1 ... dn = list of dates -- Earliest of the given dates

-- Date Conversion Functions --
TO_CHAR ( d [, fmt ] ) --d = date value, fmt = format for string -- The date d converted to a string in the given format
TO_DATE ( s [, fmt ] ) --s = character string, fmt = format for date -- String s converted to a date value
ROUND ( d [, fmt ] ) --d = date value, fmt = format for string -- Date d rounded as specified by the format
TRUNC ( d [, fmt ] ) --d = date value, fmt = format for string -- Date d truncated as specified by the format

-- Here are some examples of the use of the Date functions:

select to_char ( sysdate, 'MON DD, YYYY' ) from dual;

select to_char ( sysdate, 'HH12:MI:SS AM' ) from dual;

select to_char ( new_time ( sysdate, 'CDT', 'GMT'), 'HH24:MI' ) from dual;

select greatest ( to_date ( 'JAN 19, 2000', 'MON DD, YYYY' ),
to_date ( 'SEP 27, 1999', 'MON DD, YYYY' ),
to_date ( '13-Mar-2009', 'DD-Mon-YYYY' ) )
from dual;

select next_day ( sysdate, 'FRIDAY' ) from dual;

select last_day ( add_months ( sysdate, 1 ) ) from dual;
