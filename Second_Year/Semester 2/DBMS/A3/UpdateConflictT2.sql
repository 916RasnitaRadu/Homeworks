USE GymStore
GO

SET TRANSACTION ISOLATION LEVEL SNAPSHOT
BEGIN TRAN
WAITFOR DELAY '00:00:06'
-- T1 has now updated and obtained a lock on this table
-- trying to update the same row will result in a error (process is blocked)
UPDATE Client SET first_name='Isaila2' WHERE id =22
COMMIT TRAN

