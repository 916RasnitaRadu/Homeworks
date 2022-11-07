DROP TABLE IF EXISTS Shipment;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Payment_Details;
DROP TABLE IF EXISTS Address_of_Client;
DROP TABLE IF EXISTS Review;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Applied_Discount;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Discount;
DROP TABLE IF EXISTS Product_Type;

-- SHIPMENT { id, type, address, payment_Details }
-- PAYMENT TYPE
-- PAYMENT DETAILS

CREATE TABLE Product_Type (
	id int primary key not null,
	name_type varchar(100),
);

CREATE TABLE Discount ( 
	id int not null primary key,
	discount_name varchar(64),
	discount_desc varchar(100),
	disc_percentage decimal,
	active bit not null
);

CREATE TABLE Product (
	id int not null,
	id_type int foreign key references Product_Type(id) not null,
	product_name varchar(64) not null,
	prod_desc varchar(255),
	price float,
	PRIMARY KEY(id)
);

CREATE TABLE Applied_Discount (
	prod_id int not null foreign key references Product(id),
	discount_id int not null foreign key references Discount(id),
	PRIMARY KEY(prod_id, discount_id),
	starts_at date not null,
	ends_at date not null
);

CREATE TABLE Client ( 
	id int not null,
	first_name varchar(64) not null,
	last_name varchar(64) not null,
	phone char(11)
	PRIMARY KEY (id)
);

CREATE TABLE Review (
	id int primary key not null,
	prod_id int not null,
	client_id int not null,
	review_text varchar(255),
	created_at timestamp,
);

ALTER TABLE Review
ADD CONSTRAINT FK_Review_Prod_id FOREIGN KEY(prod_id) REFERENCES Product(id);

ALTER TABLE Review
ADD CONSTRAINT FK_Review_Client_id FOREIGN KEY(client_id) REFERENCES Client(id);

CREATE TABLE Orders (
	id int primary key not null,
	client_id int not null foreign key references Client(id),
	payment_details_id int not null,
	prod_id int not null foreign key references Product(id),
	quantity int not null,
);

CREATE TABLE Address_of_Client (
	id int primary key not null,
	client_id int not null foreign key references Client(id),
	city_name varchar(100) not null,
	address_details varchar(255) not null,
);

CREATE TABLE Payment_Details (
	id int primary key not null,
	payment_type varchar(100) not null,
	client_name varchar(100) not null,
	delivery_cost float,
	order_cost float,
	final_cost float,
);

ALTER TABLE Orders
ADD CONSTRAINT FK_Orders_PaymentDetails_id FOREIGN KEY(payment_details_id) REFERENCES Payment_Details(id);

CREATE TABLE Shipment (
	id int primary key not null,
	shipment_type varchar(100) not null,
	address_id int foreign key references Address_of_Client(id) not null,
	order_id int foreign key references	Orders(id)
);
