

-- a. modify the type of a column;

CREATE PROCEDURE setStartsAtFromAppliedDiscToVarChar AS
	ALTER TABLE AppliedDiscount ALTER COLUMN starts_at VARCHAR(64)
GO

-- reverse
CREATE PROCEDURE setStartsAtFromAppliedDiscToVarDate AS
	ALTER TABLE AppliedDiscount ALTER COLUMN starts_at DATE
GO

-- b. add / remove a column;

CREATE PROCEDURE addColumnCountryToAddress AS
	ALTER TABLE Address_of_Client ADD country_name VARCHAR(100)
GO

-- reverse
CREATE PROCEDURE dropColumnCountryToAddress AS
	ALTER TABLE Address_of_Client drop column country_name
GO

-- c. add / remove a DEFAULT constraint;

CREATE PROCEDURE setActiveDiscountTo0 AS
	ALTER TABLE Discount ADD CONSTRAINT DEFAULT0 default(0) for active
GO

-- reverse
CREATE PROCEDURE dropConstraintForActiveInDiscount AS
	ALTER TABLE Discount DROP CONSTRAINT DEFAULT0
GO

-- g. create / drop a table.

CREATE PROCEDURE createFeedbackTable AS
	CREATE TABLE Feedback(
		id int not null,
		rating float,
		got_at varchar(64) not null,
		feedback_text varchar(100),
		order_id int not null
		constraint FEEDBACK_PRIMARY_KEY primary key(id)
	)
GO

-- reverse
CREATE PROCEDURE dropTableFeedback AS
	DROP TABLE IF EXISTS Feedback
GO

-- d. add / remove a primary key;
CREATE PROCEDURE addGotAtPrimaryKey AS
	ALTER TABLE Feedback DROP CONSTRAINT FEEDBACK_PRIMARY_KEY
	ALTER TABLE Feedback ADD CONSTRAINT FEEDBACK_PRIMARY_KEY primary key(id, got_at)
GO

-- reverse
CREATE PROCEDURE removeGotAtPrimaryKey AS
	ALTER TABLE Feedback DROP CONSTRAINT FEEDBACK_PRIMARY_KEY
	ALTER TABLE Feedback ADD CONSTRAINT FEEDBACK_PRIMARY_KEY primary key(id)
GO

-- e. add / remove a candidate key;
CREATE PROCEDURE addCandidateKey AS
	ALTER TABLE Feedback ADD CONSTRAINT FEEDBACK_CANDIDATE_KEY unique (id, got_at, order_id)
GO

-- reverse
CREATE PROCEDURE removeCandidateKey AS
	ALTER TABLE Feedback DROP CONSTRAINT FEEDBACK_CANDIDATE_KEY
GO

--f. add / remove a foreign key;
CREATE PROCEDURE addOrderIdAsFK AS
	ALTER TABLE Feedback ADD CONSTRAINT FEEDBACK_FOREIGN_KEY foreign key(order_id) REFERENCES Orders(id)
GO

-- reverse
CREATE PROCEDURE removeOrderIdAsFK AS
	ALTER TABLE Feedback DROP CONSTRAINT FEEDBACK_FOREIGN_KEY
GO

-- Create a new table that holds the current version of the database schema. Simplifying assumption: the version is an integer number.

CREATE TABLE versionTable( versiune int)

INSERT INTO versionTable values(1)
GO
-- Write a stored procedure that receives as a parameter a version number and brings the database to that version.

-- first we create a procedures table where we will insert all the procedures yay :/

CREATE TABLE proceduresTable(
	from_version int,
	to_version int,
	proc_name varchar(100),
	primary key(from_version, to_version)
)

-- now we insert the procedures

insert into proceduresTable values(1,2,'setStartsAtFromAppliedDiscToVarChar')
insert into proceduresTable values(2,1,'setStartsAtFromAppliedDiscToVarDate')
insert into proceduresTable values(2,3,'addColumnCountryToAddress')
insert into proceduresTable values(3,2,'dropColumnCountryToAddress')
insert into proceduresTable values(3,4,'setActiveDiscountTo0')
insert into proceduresTable values(4,3,'dropConstraintForActiveInDiscount')
insert into proceduresTable values(4,5,'createFeedbackTable')
insert into proceduresTable values(5,4,'dropTableFeedback')
insert into proceduresTable values(5,6,'addGotAtPrimaryKey')
insert into proceduresTable values(6,5,'removeGotAtPrimaryKey')
insert into proceduresTable values(6,7,'addCandidateKey')
insert into proceduresTable values(7,6,'removeCandidateKey')
insert into proceduresTable values(7,8,'addOrderIdAsFK')
insert into proceduresTable values(8,7,'removeOrderIdAsFK')
GO

-- creating procedure

CREATE PROCEDURE go_to_version(@wantedVersion int) AS
	BEGIN
		DECLARE @currentVersion int
		select @currentVersion=versiune from versionTable
		DECLARE @proc varchar(100)

		if @wantedVersion < 0 OR @wantedVersion > (select MAX(to_version) from proceduresTable)
			BEGIN
				PRINT 'Bad version..'
				RETURN
			END
		else
			BEGIN
				WHILE @currentVersion < @wantedVersion 
					BEGIN
						SELECT @proc=proc_name from proceduresTable WHERE from_version=@currentVersion AND to_version=@currentVersion+1
						exec (@proc)
						set @currentVersion=@currentVersion+1
					END

				WHILE @currentVersion > @wantedVersion
					BEGIN
						SELECT @proc=proc_name from proceduresTable WHERE from_version=@currentVersion AND to_version=@currentVersion-1
						exec (@proc)
						set @currentVersion=@currentVersion-1
					END
			END

			UPDATE versionTable SET versiune=@wantedVersion
	END
GO

execute go_to_version 1