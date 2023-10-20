USE GymStore
GO

CREATE OR ALTER PROCEDURE AddProductRecover(@id_type int, @product_name varchar(64), @product_desc varchar(255), @price float) AS 
	SET NOCOUNT ON
	BEGIN TRAN
	BEGIN TRY
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
		COMMIT TRAN
		PRINT 'Transaction completed'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN 
		PRINT 'Transaction aborted'
		PRINT ERROR_MESSAGE()
	END CATCH
GO

CREATE OR ALTER PROCEDURE AddClientRecover(@id int,@first_name varchar(64),@last_name varchar(64), @phone char(11)) AS
	SET NOCOUNT ON
	BEGIN TRAN
	BEGIN TRY
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
		COMMIT TRAN
		PRINT 'Transaction completed'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN 
		PRINT 'Transaction aborted'
		PRINT ERROR_MESSAGE()
	END CATCH
GO

CREATE OR ALTER PROCEDURE AddReviewRecover(@prod_id int,@client_id int,@text varchar(64),@created_at varchar(64)) AS
	SET NOCOUNT ON
	BEGIN TRAN
	BEGIN TRY
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
		COMMIT TRAN
		PRINT 'Transaction completed'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN 
		PRINT 'Transaction aborted'
		PRINT ERROR_MESSAGE()
	END CATCH
GO

CREATE OR ALTER PROCEDURE BadAddRecvScenario AS
	EXEC AddProductRecover 3, 'Droage', 'Droage foarte bune', 30.5
	EXEC AddClientRecover -20, 'B', 'Macelaru', '07419814756' -- this will fail, but the item added before will still be in the database
	EXEC AddReviewRecover 5, 12, 'misto', '22-04-2021'
GO

CREATE OR ALTER PROCEDURE GoodAddRecvScenario AS
	EXEC AddProductRecover 3, 'Droage', 'Droage foarte bune', 30.5
	EXEC AddClientRecover 20, 'Bebe', 'Macelaru', '07419814756'
	EXEC AddReviewRecover 5, 12, 'misto', '22-04-2021'
GO


EXEC BadAddRecvScenario
SELECT * FROM LogTable

EXEC GoodAddRecvScenario
SELECT * FROM LogTable

DELETE FROM Product where product_name LIKE 'Droage'
DELETE FROM Client where id = 20
DELETE FROM Review where Review.prod_id = 5 and Review.client_id = 12