USE GymStore
GO

INSERT INTO Client VALUES (22, 'Ionescu', 'Johnescu', '0756980432')

WAITFOR DELAY '00:00:06'
BEGIN TRAN 
UPDATE Client SET first_name='Isaila' WHERE id=22
WAITFOR DELAY '00:00:06'
COMMIT TRAN


select * from Client
delete from client where id=22