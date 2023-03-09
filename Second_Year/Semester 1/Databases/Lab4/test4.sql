USE [GymStore] GO

exec addToTables 'TestProduct'
exec addToTables 'TestProductType'
exec addToViews 'ViewProduct'
exec addToViews 'ViewProductType'
exec addToTests 'test4'
exec putTableToTest 'TestProduct', 'test4', 1800, 2
exec putTableToTest 'TestProductType', 'test4', 1400, 1
exec putViewToTest 'ViewProduct', 'test4'
exec putViewToTest 'ViewProductType', 'test4'

exec runTest 'test4'

SELECT * FROM Tests
SELECT * FROM Tables
SELECT * FROM Views

DELETE FROM Views
DELETE FROM Tables
GO


SELECT * FROM TestRuns
SELECT * FROM TestRunViews
SELECT * FROM TestRunTables
Select * from TestTables
SELECT * FROM TestViews
SELECT * FROM TestProduct
SELECT * FROM TestProductType
SELECT * FROM TestAppliedDiscounts
DELETE FROM TestProduct
DELETE FROM TestProductType
DELETE FROM TestAppliedDiscounts

