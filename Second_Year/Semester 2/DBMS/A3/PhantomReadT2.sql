USE GymStore
GO

-- part 2: we have a phantom read - because T1 has generated a new row while T2 executing a query, and we will get an extra row in the second select
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ
--SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
BEGIN TRAN
-- inserted value does not exist yet
SELECT * FROM Product WHERE price > 100
WAITFOR DELAY '00:00:06'
-- we can see inserted value during the second read
SELECT * FROM Product WHERE price > 100
COMMIT TRAN