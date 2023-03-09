USE [GymStore] GO

exec addToTables 'TestProduct'
exec addToViews 'ViewProduct'
exec addToTests 'test1'
exec putTableToTest 'TestProduct', 'test1', 1000, 1
exec putViewToTest 'ViewProduct', 'test1' 


execute runTest 'test1'

SELECT * FROM Tests
SELECT * FROM Tables
SELECT * FROM Views

DELETE FROM Tests
DELETE FROM Views
DELETE FROM Tables
GO


SELECT * FROM TestRuns
SELECT * FROM TestRunViews
SELECT * FROM TestRunTables
Select * from TestTables
SELECT * FROM TestViews
SELECT * FROM Product

DELETE FROM TestRunTables
DELETE FROM TestRunViews
DELETE FROM TestRuns
delete from TestViews