USE [SodaBaseandersc720]
GO

CREATE PROCEDURE [dbo].[AddRestaurant]
	@restName nvarchar(20), 
	@addr nvarchar(40) = null,
	@contact nvarchar(20) = null
AS
BEGIN
	if @restName is null OR @restName = ''
	BEGIN
		PRINT 'ERROR: Restaurant name cannot be null or empty';
		RETURN (1)
	END
	IF (SELECT COUNT(*) FROM Rest
          WHERE Name = @restName) = 1
	BEGIN
      PRINT 'ERROR: Restaurant name already exists.';
	  RETURN(2)
	END
	INSERT INTO Rest(name, addr, contact) 
	VALUES(@restName, @addr, @contact);
END
GO


