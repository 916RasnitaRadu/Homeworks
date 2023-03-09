DROP TABLE IF EXISTS DiscountL5;
DROP TABLE IF EXISTS ProductL5;
DROP TABLE IF EXISTS Tc;

CREATE TABLE DiscountL5(
	disc_id int not null primary key IDENTITY(1,1),
	disc_percentage int unique,
	discount_name varchar(64)
)

CREATE TABLE ProductL5(
	prod_id int not null primary key IDENTITY(1,1),
	price int,
	name_prod varchar(64)
)

CREATE TABLE Applied_DiscL5(
	adid int not null primary key IDENTITY(1,1),
	disc_id int foreign key references DiscountL5(disc_id),
	prod_id int foreign key references ProductL5(prod_id),
	descript varchar(64)
)



INSERT INTO DiscountL5 VALUES (20,'discount1'), (30, 'discount2'), (24,'discount3'), (58,'discount4'), (93, 'discount5'), (9,'discount6'), (45,'discount7')
INSERT INTO ProductL5 VALUES (60,'proteine'), (34,'ceva'), (69,'altceva'), (120,'creatina'), (90, 'cereale'), (100, 'bcaa'), (89,'manusi')
INSERT INTO Applied_DiscL5 VALUES (1,5,'gratis'), (2,4,'reducere'), (1,3,'fa-te mare'), (6,7,'te-ai facut cat dulapu cu reducerea asta'), (5,4,'omg'), (3,2,'crazy'),(2,2,'messi')

SELECT * FROM DiscountL5
SELECT * FROM ProductL5
SELECT * FROM Applied_DiscL5

-- a)
select * from DiscountL5 order by disc_id -- clustered index scan
select * from DiscountL5 where disc_id = 7 -- clustered index seek
select discount_name from DiscountL5 order by disc_percentage -- nonclustered index scan
select disc_percentage, discount_name from DiscountL5 where disc_percentage = 24 -- nonclustered index seek
select discount_name from DiscountL5 where disc_id = 4 -- key lookup

-- b)
select * from ProductL5 where price = 60 -- Estimated subtree cost = 0.0032897
CREATE NONCLUSTERED INDEX indexProductL5 on ProductL5(price, name_prod) 

select * from ProductL5 where price = 60 -- Estimated subtree cost = 0.0032831
DROP INDEX indexProductL5 on ProductL5
GO

CREATE NONCLUSTERED INDEX indexAppDisc on Applied_DiscL5(adid, prod_id)
DROP INDEX indexAppDisc on Applied_DiscL5	
GO

CREATE NONCLUSTERED INDEX indexDiscount on DiscountL5(disc_percentage)
DROP INDEX indexDiscount on DiscountL5	
GO

-- c)
CREATE OR ALTER VIEW ViewOnTables AS
	SELECT AD.adid, AD.prod_id, P.price, P.name_prod, D.disc_percentage from ProductL5 P inner join Applied_DiscL5 AD on AD.adid = P.prod_id inner join DiscountL5 D on D.disc_id = AD.adid
GO

SELECT * from ViewOnTables -- I did not see any change 
						   -- 0.0390298 without index
						   -- 0.0375483 with index

DECLARE @rows int
set @rows = 500

WHILE @rows > 0 
BEGIN
	INSERT INTO Applied_DiscL5 VALUES (5555,38641,'messi')
	set @rows = @rows - 1
END

DELETE FROM DiscountL5
DELETE FROM ProductL5
DELETE FROM Applied_DiscL5