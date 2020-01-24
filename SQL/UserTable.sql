USE [SodaBaseandersc720]
GO

CREATE TABLE [dbo].[User](
	[Username] [nvarchar](50) NOT NULL,
	[PasswordSalt] [varchar](50) NOT NULL,
	[PasswordHash] [varchar](50) NOT NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


