USE [GymStore] GO

exec addToTables 'TestProduct'
exec addToTables 'TestProductType'
exec addToViews 'ViewProductType'
exec addToTests 'test2'
exec putTableToTest 'TestProduct', 'test2', 1200, 2
exec putTableToTest 'TestProductType', 'test2', 800, 1
exec putViewToTest 'ViewProductType', 'test2'
GO

execute runTest 'test2'

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