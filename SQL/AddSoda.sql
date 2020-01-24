USE [SodaBaseandersc720]
GO
CREATE PROCEDURE [dbo].[AddSoda]
	@sodaName nvarchar(20), 
	@manf nvarchar(20) = null
AS
BEGIN
	if @sodaName is null OR @sodaName = ''
	BEGIN
		PRINT 'ERROR: Soda name cannot be null or empty.';
		RETURN (1)
	END
	IF (SELECT COUNT(*) FROM Soda
          WHERE Name = @sodaName) = 1
	BEGIN
      PRINT 'ERROR: Soda name already exists.';
	  RETURN(2)
	END
	INSERT INTO Soda(name, manf) 
	VALUES(@sodaName, @manf);
END
GO


