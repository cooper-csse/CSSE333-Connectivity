USE [SodaBaseandersc720]
GO
CREATE PROCEDURE [dbo].[AddSells]
	@sodaName nvarchar(20), 
	@restName nvarchar(20),
	@price money
AS
BEGIN
	if @sodaName is null OR @sodaName = ''
	BEGIN
		PRINT 'ERROR: Soda name cannot be null or empty.';
		RETURN (1)
	END
	if @restName is null OR @restName = ''
	BEGIN
		PRINT 'ERROR: Rest name cannot be null or empty.';
		RETURN (2)
	END
	if @price is null OR @price = ''
	BEGIN
		PRINT 'ERROR: Price cannot be null or empty.';
		RETURN (3)
	END
	IF (SELECT COUNT(*) FROM Soda
          WHERE Name = @sodaName) = 0
	BEGIN
      PRINT 'ERROR: Given soda does not exist.';
	  RETURN(4)
	END
	IF (SELECT COUNT(*) FROM Rest
          WHERE Name = @restName) = 0
	BEGIN
      PRINT 'ERROR: Given restaurant name does not exist.';
	  RETURN(5)
	END
	IF (SELECT COUNT(*) FROM Sells
          WHERE rest = @restName and soda = @sodaName) = 1
	BEGIN
		Update Sells
		SET Price = @price
		WHERE rest = @restName and soda = @sodaName;
	END
	ELSE
	BEGIN
		INSERT INTO Sells(rest, soda, price) 
		VALUES(@restName, @sodaName, @price);
	END
END
GO


