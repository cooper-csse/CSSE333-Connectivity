USE [SodaBaseandersc720]
GO
CREATE PROCEDURE [dbo].[Register]
	@Username nvarchar(50),
	@PasswordSalt varchar(50),
	@PasswordHash varchar(50)
AS
BEGIN
	if @Username is null or @Username = '' 
	BEGIN
		Print 'Username cannot be null or empty.';
		RETURN (1)
	END 
	if @PasswordSalt is null or @PasswordSalt = '' 
	BEGIN
		Print 'PasswordSalt cannot be null or empty.';
		RETURN (2)
	END 
	if @PasswordHash is null or @PasswordHash = '' 
	BEGIN
		Print 'PasswordHash cannot be null or empty.';
		RETURN (3)
	END 
	IF (SELECT COUNT(*) FROM [User]
          WHERE Username = @Username) = 1
	BEGIN
      PRINT 'ERROR: Username already exists.';
	  RETURN(4)
	END
	INSERT INTO [User](Username, PasswordSalt, PasswordHash)
	VALUES (@username, @passwordSalt, @passwordHash)
END
GO


