use TrainSt

-- Train, Train Types, Stations and Routes
-- one route can have one or many stations and one station may have one or many routes

-- a

DROP TABLE IF EXISTS TrainType;
DROP TABLE IF EXISTS Train;
DROP TABLE IF EXISTS Station;
DROP TABLE IF EXISTS Rout;
DROP TABLE IF EXISTS RouteHasStation;

CREATE TABLE TrainType(
	id int primary key identity(1,1),
	typeName varchar(64),
	descr varchar(100)
)

CREATE TABLE Station(
	id int primary key identity(1,1),
	stationName varchar(64) unique
)

CREATE TABLE Train(
	id int primary key identity(1,1),
	typeId int foreign key references TrainType(id),
	trainName varchar(64)
)

CREATE TABLE Rout(
	id int primary key identity(1,1),
	routeName varchar(64) unique,
	trainId int foreign key references Train(id)
)

CREATE TABLE RouteHasStation(
	routeId int foreign key references Rout(id),
	stationId int foreign key references Station(id),
	arrival time,
	departure time
)

INSERT INTO TrainType VALUES ('t1','d1'), ('t2','d2') ,('t3','d3'), ('t3','d3'), ('t4','d4') ,('t5','d5')
INSERT INTO Train VALUES (2,'n1'), (1,'n2'), (4,'n3'), (1,'n4'), (5,'n5'), (2,'n6')
INSERT INTO Station VALUES ('s1'),  ('s2'),  ('s3'),  ('s4'),  ('s5'),  ('s6'),  ('s7')
INSERT INTO Rout VALUES ('r1', 6), ('r2', 1), ('r3', 2), ('r4', 3)
INSERT INTO RouteHasStation VALUES (1, 1, '9:00am', '9:10am'), (1, 3, '10:00am', '10:10am'), (1, 2, '11:00am', '11:10am'), (1, 4, '11:00am', '11:10am'), (1, 5, '11:00am', '11:10am'),(1, 6, '11:00am', '11:10am'),(1, 7, '11:00am', '11:10am'),(2, 1, '5:00pm', '5:10pm'),(2, 3, '6:00pm', '6:10pm'),(3, 2, '10:00pm', '10:30am')

SELECT * FROM TrainType
SELECT * FROM Train
SELECT * FROM Station
SELECT * FROM Rout
SELECT * FROM RouteHasStation
GO
-- b

CREATE OR ALTER PROCEDURE StoreRoute(@routeName varchar(64), @stationName varchar(64), @arrival TIME, @departure TIME) AS
	DECLARE @idR INT = (SELECT id from Rout where Rout.routeName = @routeName)
	DECLARE @idS INT = (SELECT id from Station where Station.stationName = @stationName)

	if @idR is not null and @idS is not null
		IF EXISTS (select * from RouteHasStation R where @idR = R.routeId and @idS = R.stationId)
		BEGIN
			UPDATE RouteHasStation 
			SET arrival = @arrival, departure = @departure
			WHERE routeId = @idR and @idS = stationId
		END
		ELSE
		BEGIN
			insert into RouteHasStation values(@idR, @idS, @arrival, @departure)
		END
	else PRINT 'Incorect input!'
GO

-- c

CREATE VIEW ShowRoutesPassingAllStations AS
	SELECT r.routeName -- luam numele rutei
	from RouteHasStation rh inner join Rout r ON r.id = rh.routeId -- facem un tabel cu rutele care au statii
	GROUP BY r.id,r.routeName -- le grupam dupa nume si id
	HAVING count(*) = (SELECT count(*) from Station) -- ca sa putem numara cate au grupe au nr de elemente egal cu cate statii sunt
GO

-- d

CREATE FUNCTION ListStationsWithMoreThanRroutes(@R INT) returns Table AS
	RETURN 
	SELECT S.stationName
	from Station S inner join RouteHasStation RS ON S.id = RS.stationId
	GROUP BY S.id, S.stationName
	HAVING count(*) > @R
GO