USE GymStore
GO

CREATE OR ALTER PROCEDURE addToTables(@tableName varchar(64)) AS
	if @tableName in (select Name from Tables)
	BEGIN
		print 'Table already in Tables'
		return
	END
	if @tableName not in (select TABLE_NAME from INFORMATION_SCHEMA.TABLES) 
	BEGIN
		print 'Table not present in database'
		return
	END
	insert into Tables(Name) values (@tableName)
GO

CREATE OR ALTER PROCEDURE addToViews(@viewName varchar(64)) AS
	if @viewName in (select Name from Views)
	BEGIN
		print 'View already in Views'
		return
	END
	if @viewName not in (select TABLE_NAME from INFORMATION_SCHEMA.VIEWS)
	BEGIN
		print 'View not present in database'
		return
	END
	insert into Views(Name) values (@viewName)
GO

CREATE OR ALTER PROCEDURE addToTests (@testName varchar(64)) AS
	if @testName in (select Name from Tests) 
	BEGIN
		print 'Test already in Tests'
		return
	END	
	insert into Tests(Name) VALUES (@testName)
GO

CREATE OR ALTER PROCEDURE putViewToTest(@viewName varchar(64), @testName varchar(64)) AS
	if @viewName not in (select Name from Views)
	BEGIN
		print 'View not present in Views'
		return
	END
	if @testName not in (select Name from Tests) 
	BEGIN
		print 'Test not present in Tests'
		return
	END
	insert into TestViews(TestID, ViewID) VALUES (
		(select TestID from Tests where Name = @testName),
		(select ViewID from Views where Name = @viewName))
GO

CREATE OR ALTER PROCEDURE putTableToTest(@tableName varchar(64), @testName varchar(64), @rows int, @position int) AS
	if @tableName not in (select Name from Tables)
	BEGIN
		print 'Table not present in Tables'
		return
	END
	if @testName not in (select Name from Tests) 
	BEGIN
		print 'Test not present in Tests'
		return
	END
	insert into TestTables(TestID, TableID, NoOfRows, Position) VALUES (
		(select TestID from Tests where Name = @testName),
		(select TableID from Tables where Name = @tableName),
		@rows,
		@position
	)
GO

CREATE OR ALTER PROCEDURE runTest(@testName varchar(64)) AS
	if @testName not in (select Name from Tests) BEGIN
		print 'Given test is not present in Tests'
		return
	END

	DECLARE @cmd varchar(64)
	DECLARE @testStartTime DATETIME
	DECLARE @startTime DATETIME
	DECLARE @endTime DATETIME
	DECLARE @tableName varchar(64)
	DECLARE @rows int
	DECLARE @position int
	DECLARE @viewName varchar(64)
	DECLARE @testID int
	DECLARE @testRunID int

	SELECT @testID = TestID from Tests where Name = @testName
	SET @testRunID = (select max(TestRunID) + 1 from TestRuns)

	if @testRunID is null
		SET @testRunID = 0

	DECLARE tableCursor CURSOR FOR 
		SELECT T1.Name, T2.NoOfRows, T2.Position from Tables T1 inner join TestTables T2 ON T1.TableID = T2.TableID
		WHERE T2.TestID = @testID
		ORDER BY T2.Position

	DECLARE viewCursor CURSOR FOR
		SELECT V.Name from Views V inner join TestViews TV ON V.ViewID = TV.ViewID
		WHERE TV.TestID = @testID

	SET @testStartTime = SYSDATETIME()

	OPEN tableCursor
	fetch last from tableCursor into @tableName, @rows, @position
	WHILE @@FETCH_STATUS = 0
	BEGIN
		exec ('DELETE FROM' + @tableName)
		fetch prior from tableCursor into @tableName, @rows, @position
	END
	CLOSE tableCursor

	OPEN tableCursor
	SET IDENTITY_INSERT TestRuns ON
	INSERT INTO TestRuns(TestRunID, Description, StartAt) VALUES (@testRunID, 'Test Results for: ' + @testName, @testStartTime)
	SET IDENTITY_INSERT TestRuns OFF

	fetch from tableCursor into @tableName, @rows, @position
	WHILE @@FETCH_STATUS = 0 
	BEGIN
		SET @cmd = 'populateTable' + @tableName

		SET @startTime = SYSDATETIME()
		exec @cmd @rows
		SET @endTime = SYSDATETIME()

		INSERT INTO TestRunTables(TestRunID, TableID, StartAt, EndAt) VALUES(
			@testRunID,
			(select TableID from Tables WHERE @tableName = Tables.Name),
			@startTime,
			@endTime)
		fetch from tableCursor into @tableName, @rows, @position
	END
	CLOSE tableCursor
	DEALLOCATE tableCursor

	OPEN viewCursor
	fetch from viewCursor into @viewName
	WHILE @@FETCH_STATUS = 0 
	BEGIN
		SET @cmd = 'select * from' + @viewName

		SET @startTime = SYSDATETIME()
		exec (@cmd)
		SET @endTime = SYSDATETIME()

		INSERT INTO TestRunViews(TestRunID, ViewID, StartAt, EndAt) VALUES (
			@testRunID,
			(select ViewID from Views WHERE Name = @viewName),
			@startTime,
			@endTime)

		fetch from viewCursor into @viewName
	END
	CLOSE viewCursor
	DEALLOCATE viewCursor

	UPDATE TestRuns SET EndAt = SYSDATETIME() where TestRunID = @testRunID
GO

CREATE OR ALTER PROCEDURE populateTableTestProductType(@rows int) AS
	WHILE @rows > 0
	BEGIN 
		INSERT INTO TestProductType(id, name_type) VALUES (@rows, 'type')
		SET @rows = @rows - 1
	END
GO

CREATE OR ALTER PROCEDURE populateTableTestProduct(@rows int) AS
	WHILE @rows > 0
	BEGIN
		INSERT INTO TestProduct(id, id_type, product_name, prod_desc, price) VALUES(@rows, 1, 'Testing_prod', 'blahblah', 69)
		SET @rows = @rows - 1
	END
GO

CREATE OR ALTER PROCEDURE populateTableTestAppliedDiscounts(@rows int) AS
	WHILE @rows > 0
	BEGIN
		INSERT INTO TestAppliedDiscounts(prod_id, discount_id, starts_at, ends_at) VALUES(@rows,@rows - 1,'12-12-2012', '13-12-2012')
		SET @rows = @rows - 1
	END
GO
