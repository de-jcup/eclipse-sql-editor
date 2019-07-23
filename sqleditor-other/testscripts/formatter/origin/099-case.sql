-- Maybe no the best result, but readable
SELECT
   OrderID, Quantity,CASE     WHEN Quantity > 30  THEN    "The quantity is greater than 30" WHEN    Quantity = 30  THEN    "The quantity is 30"  ELSE    "The quantity is under 30" END AS QuantityText
FROM
   OrderDetails;
