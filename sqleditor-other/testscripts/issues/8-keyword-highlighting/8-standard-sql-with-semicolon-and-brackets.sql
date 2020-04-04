-- Following is not correct SQL but shows highlighting problems:

INSERT INTO myTable -- INTO must be highlighted

INTEGER; -- INTEGER; must be highlighted
INTEGER -- this was already befoer highlighted

INSERT INTO tableName(column1,column2 ...)
VALUES(value1,value2,...) -- VALUES( must be highlihted

INSERT INTO tableName(column1,column2 ...)
VALUES (value1,value2,...) -- VALUES ( was already highlighted
