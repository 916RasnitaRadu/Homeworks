USE [GymStore] GO

exec addToTables 'TestProduct'
exec addToTables 'TestProductType'
exec addToTables 'TestAppliedDiscounts'
exec addToViews 'ViewProduct'
exec addToViews 'ViewProductType'
exec addToTests 'test5'
exec putTableToTest 'TestAppliedDiscounts', 'test5', 2000, 3
exec putTableToTest 'TestProduct', 'test5', 1800, 2
exec putTableToTest 'TestProductType', 'test5', 1600, 1
exec putViewToTest 'ViewProduct', 'test5'
exec putViewToTest 'ViewProductType', 'test5'

exec runTest 'test5'

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
