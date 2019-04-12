-- *******************************
-- * Example SQL snippet to test *
-- * SELECT highlighting         *
-- *******************************
SELECT DISTINCT employees AS e FROM EMP_TABLE AS t 
	WHERE e.employed=1 
	GROUP BY e.lastname 
	HAVING Having-Cause
	ORDER BY sortattribute ASC DESC;


-- Another syntax color example:
SELECT count(ticket.id) AS Matches, ticket_custom.name, ticket.status
FROM engine.ticket
INNER JOIN engine.ticket_custom ON ticket.id = ticket_custom.ticket
WHERE ticket_custom.name='chapter' 
AND 
ticket_custom.value LIKE '%c%' AND type='New material' 
AND 
milestone='1.1.12' 
AND 
component NOT LIKE 'internal_engine' 
AND 
ticket.status='qa' AND (ticket.status IN (........))
GROUP BY ticket.id, ticket_custom.name;
