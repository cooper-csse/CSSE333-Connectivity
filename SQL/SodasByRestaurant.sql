USE [SodaBaseandersc720]
GO
CREATE View [dbo].[SodasByRestaurant]
AS
SELECT rest as Restaurant, soda as Soda, manf as Manufacturer, contact as RestaurantContact, price as Price
FROM Sells s
JOIN rest r on s.rest = r.name
JOIN soda so on s.soda = so.name
GO


