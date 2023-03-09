USE [Airport] 

-- Airplane - model number, registr nr - uniq, capacity
-- Flight - flight number unique, departure airport, destination airport, departure & arrival datetime
-- each flight is carried by a single airplane
-- Passenger - first name, last name, email address unique
-- many to many passenger <-> flight
-- Reservations - passenger, flight, payment details
-- Payments - amount, datetime, type
-- a reserv can have only one payment

DROP TABLE IF EXISTS Airplane;
DROP TABLE IF EXISTS Flight;
DROP TABLE IF EXISTS Passenger;
DROP TABLE IF EXISTS Payments;
DROP TABLE IF EXISTS Reservations;
DROP TABLE IF EXISTS PassengerFlights;
GO

CREATE TABLE Airplane(
	registration_number int primary key identity(1,1),
	capacity int,
	model_number int
)

CREATE TABLE Flight(
	flight_nr int primary key identity(1,1),
	departure_airport varchar(64),
	destination_airport varchar(64),
	departure datetime,
	arrival datetime
)

CREATE TABLE Passenger(
	email varchar(100) primary key,
	first_name varchar(100),
	last_name varchar(100)
)

CREATE TABLE Payments(
	id int primary key identity(1,1),
	amount float, 
	payment_datetime datetime,
	payment_type varchar(15)
)

CREATE TABLE Reservations(
	reservation_id int primary key identity(1,1),
	passenger_email varchar(100) foreign key references Passenger(email),
	payment_details int foreign key references Payments(id),
	flight_nr int foreign key references Flight(flight_nr)
)

CREATE TABLE PassengerFlights(
	flight_nr int foreign key references Flight(flight_nr),
	passenger_email varchar(100)foreign key references Passenger(email),
	PRIMARY KEY(flight_nr, passenger_email)
)
go


-- b

CREATE OR ALTER PROCEDURE AddPaymentToReservation(@paymentDetails int, @reservation int) AS
	IF NOT EXISTS (SELECT * from Reservations WHERE Reservations.reservation_id = @reservation)
	BEGIN
		PRINT 'ERROR: The given reservation is not in the table'
		RETURN
	END
	IF EXISTS (SELECT payment_details from Reservations WHERE Reservations.reservation_id = @reservation)
	BEGIN
		PRINT 'ERROR: The given reservation already has the payment details inserted'
		RETURN
	END
	UPDATE Reservations SET payment_details = @paymentDetails WHERE @reservation = reservation_id
GO

-- c

CREATE OR ALTER VIEW PassengerWithReservationDepartMadrid AS
	SELECT P.first_name, p.last_name
	from Passenger P 
	inner join Reservations R ON P.email = R.passenger_email
	inner join Flight F ON R.flight_nr = F.flight_nr
	WHERE F.departure_airport = 'Madrid'
	
GO

-- d 

CREATE OR ALTER FUNCTION ListFlightsWithValidReservations(@X int, @start_time datetime, @end_time datetime) RETURNS Table AS
	RETURN 
		SELECT flight_nr, COUNT(*) as r
		from Reservations R where payment_details is not null
		group by R.flight_nr
		having COUNT(*) > @X
GO