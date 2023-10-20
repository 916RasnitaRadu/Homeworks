select * from Client 
select * from Product

DELETE FROM Review
INSERT INTO Review VALUES (2,12,'text1','22-04-2021'),
(3,12,'text2','21-02-2020'),
(4,13,'text3','20-01-2022'),
(5,14,'text4','14-11-2020'),
(6,15,'text5','05-02-2023'),
(7,16,'text6','24-10-2020'),
(9,17,'text7','31-05-2019'),
(3,19,'text8','23-11-2023'),
(2,18,'text9','21-02-2020'),
(1,12,'text10','12-11-2022'),
(1,13,'text11','15-10-2021'),
(8,15,'text12','05-01-2021'),
(4,10,'text13','04-02-2022'),
(5,18,'text14','01-03-2023'),
(2,12,'text15','10-05-2022'),
(1,14,'text16','22-05-2020')

CREATE TABLE Product (
	id int primary key IDENTITY(1,1),
	id_type int foreign key references Product_Type(id),
	product_name varchar(64) not null,
	prod_desc varchar(64),
	price float
);

CREATE TABLE Client(
	id int primary key IDENTITY(10,1),
	first_name varchar(64) not null,
	last_name varchar(64) not null,
	phone char(11)
);

CREATE TABLE Review(
	id int primary key IDENTITY(1,1),
	prod_id int foreign key references Product(id) not null,
	client_id int foreign key references Client(id) not null,
	review_text varchar(64),
	created_at varchar(64) not null
);


