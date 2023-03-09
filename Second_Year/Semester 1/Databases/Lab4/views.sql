-- The tables Product, Product_Type and Applied_Discount were used
-- Product => primary key and foreign key
-- Product_Type => primary key
-- Applied_Discount => a pair primary key

USE [GymStore] GO

CREATE OR ALTER VIEW ViewProduct AS
	SELECT * from Product
GO

CREATE OR ALTER VIEW ViewProductType AS
	SELECT pt.id, p.product_name from Product_Type pt inner join Product p ON p.id_type = pt.id
GO

CREATE OR ALTER VIEW ViewAppliedDiscounts AS
	select pt.name_type from Product p inner join Product_Type pt 
	ON p.id_type = pt.id
	GROUP BY pt.name_type 
GO

CREATE TABLE TestProductType(
	id int primary key not null,
	name_type varchar(100),
)

CREATE TABLE TestProduct(
	id int primary key not null,
	id_type int foreign key references TestProductType(id) not null,
	product_name varchar(64) not null,
	prod_desc varchar(255),
	price float,
)

CREATE TABLE TestAppliedDiscounts(
	prod_id int not null,
	discount_id int not null,
	PRIMARY KEY(prod_id, discount_id),
	starts_at date not null,
	ends_at date not null
)




