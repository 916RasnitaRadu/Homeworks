USE GymStore
GO

-- part 1
BEGIN TRAN
UPDATE Client
SET first_name = 'Bogdanescu'
WHERE id = 19
WAITFOR DELAY '00:00:06'
ROLLBACK TRAN
