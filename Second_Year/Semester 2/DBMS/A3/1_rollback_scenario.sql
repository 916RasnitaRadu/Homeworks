USE GymStore
GO

DROP TABLE IF EXISTS LogTable
CREATE TABLE LogTable(
	lid int identity primary key, 
	type_operation varchar(64),
	table_operation varchar(64),
	execution_date datetime
)
GO
-- use m:n relation Customer - Review -Product

CREATE OR ALTER FUNCTION ValidateString (@str VARCHAR(30)) RETURNS INT 
AS
BEGIN
	DECLARE @return INT
	SET @return=1
	IF (@str IS NULL OR LEN(@str) < 2 OR LEN(@str) > 64)
	BEGIN 
		SET @return = 0
	END
	RETURN @return
END
GO

CREATE OR ALTER FUNCTION ufValidateInt (@int int)
RETURNS INT
AS
BEGIN
	DECLARE @return INT
	SET @return = 1
	IF(@int < 0)
	BEGIN
		SET @return = 0
	END
	RETURN @return
END
GO

CREATE OR ALTER FUNCTION ufValidateFloat (@nr float)
RETURNS INT
AS
BEGIN
	DECLARE @return INT
	SET @return = 1
	IF(@nr < 0)
	BEGIN
		SET @return = 0
	END
	RETURN @return
END
GO

CREATE OR ALTER PROCEDURE AddProduct(@id_type int, @product_name varchar(64), @product_desc varchar(255), @price float) AS 
	SET NOCOUNT ON
	IF (dbo.ValidateString(@product_name) <> 1)
	BEGIN
		RAISERROR('Product Name is invalid', 14,1)
	END
	IF (dbo.ufValidateFloat(@price) <> 1)
	BEGIN
		RAISERROR('Price can not be null',14,1)
	END
	IF (dbo.ufValidateInt(@id_type) <> 1)
	BEGIN
		RAISERROR('The type of the product is invalid',14,1)
	END
	INSERT INTO Product VALUES(@id_type, @product_name,@product_desc,@price)
	INSERT INTO LogTable VALUES('add','product',GETDATE())
GO

CREATE OR ALTER PROCEDURE AddClient(@id int,@first_name varchar(64),@last_name varchar(64), @phone char(11)) AS
	SET NOCOUNT ON
	IF (dbo.ufValidateInt(@id) <> 1) OR EXISTS (SELECT * from Client C where C.id = @id)
	BEGIN
		RAISERROR('The id is not valid',14,1)
	END 
	IF (dbo.ValidateString(@first_name) <> 1)
	BEGIN
		RAISERROR('The first name is invalid',14,1)
	END
	IF (dbo.ValidateString(@first_name) <> 1)
	BEGIN
		RAISERROR('The last name is invalid',14,1)
	END
	INSERT INTO Client VALUES(@id,@first_name,@last_name, @phone)
	INSERT INTO LogTable VALUES('add','client',GETDATE())
GO

CREATE OR ALTER PROCEDURE AddReview(@prod_id int,@client_id int,@text varchar(64),@created_at varchar(64)) AS
	SET NOCOUNT ON
	IF (dbo.ufValidateInt(@prod_id) <> 1)
	BEGIN
		RAISERROR('Id of the product is not valid',14,1)
	END
	IF (dbo.ufValidateInt(@client_id) <> 1)
	BEGIN
		RAISERROR('Id of the client is not valid',14,1)
	END
	IF (dbo.ValidateString(@text) <> 1)
	BEGIN
		RAISERROR('The review text is not valid',14,1)
	END
	IF (dbo.ValidateString(@created_at) <> 1)
	BEGIN
		RAISERROR('The date is not valid',14,1)
	END
	IF NOT EXISTS (SELECT * FROM Product P WHERE @prod_id = P.id)
	BEGIN 
		RAISERROR('The product is not in the database',14,1)
	END
	IF NOT EXISTS (SELECT * FROM Client P WHERE @client_id = P.id)
	BEGIN 
		RAISERROR('The client is not in the database',14,1)
	END
	INSERT INTO Review VALUES(@prod_id,@client_id,@text,@created_at)
	INSERT INTO LogTable VALUES('add','review',GETDATE())
GO

CREATE OR ALTER PROCEDURE AddCommitScenario AS
	BEGIN TRAN 
	BEGIN TRY
		EXEC AddProduct 2, 'Droage', 'Droage foarte bune', 22.5
		EXEC AddClient 20,'Bebe', 'Macelaru', '07419814756'
		EXEC AddReview 3, 12, 'foarte tare frate', '22-05-2020'
		COMMIT TRAN
		PRINT 'Transaction completed'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		PRINT 'Transaction aborted'
		PRINT ERROR_MESSAGE()
		RETURN
	END CATCH
GO

CREATE OR ALTER PROCEDURE AddRollbackScenario AS
	BEGIN TRAN 
	BEGIN TRY
		EXEC AddProduct 2, 'Droage', 'Droage foarte bune', -22.5
		EXEC AddClient 70,'B', 'M', '07419814756'  -- this will fail due validation, so everything fails
		EXEC AddReview 3, 12, 'foarte tare frate', '22-05-2020'
		COMMIT TRAN
		PRINT 'Transaction completed'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		PRINT 'Transaction aborted'
		PRINT ERROR_MESSAGE()
		RETURN
	END CATCH
GO

EXEC AddCommitScenario
EXEC AddRollbackScenario

SELECT * from LogTable

SELECT * from Client
SELECT * FROM Product
SELECT * FROM Review

DELETE FROM Product WHERE product_name LIKE 'Droage'
DELETE FROM Client where id = 20
DELETE FROM Review where review_text LIKE 'foarte tare frate'