USE [GymStore] GO

exec addToTables 'TestProduct'
exec addToTables 'TestProductType'
exec addToTables 'TestAppliedDiscounts'
exec addToViews 'ViewProduct'
exec addToTests 'test3'
exec putTableToTest 'TestAppliedDiscounts', 'test3', 1600, 3
exec putTableToTest 'TestProduct', 'test3', 1200, 2
exec putTableToTest 'TestProductType', 'test3', 800, 1
exec putViewToTest 'ViewProduct', 'test3'

exec runTest 'test3'

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

