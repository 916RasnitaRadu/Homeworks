USE GymStore
GO

INSERT INTO Client VALUES(21,'Biciclentiu','Ceausescu','07415632147')
BEGIN TRAN
WAITFOR DELAY '00:00:06'
UPDATE Client SET first_name='Basescu'
WHERE id = 21
COMMIT TRAN

delete from Client where id = 21