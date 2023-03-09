-- insert values in tables

INSERT INTO Discount(id,discount_name, discount_desc, disc_percentage, active) VALUES(1,'marius5','5% reducere', 5, 1);
INSERT INTO Discount(id,discount_name, discount_desc, disc_percentage, active) VALUES(2,'nicolas','15.5% reducere',15.5,0);
INSERT INTO Discount(id,discount_name, discount_desc, disc_percentage, active) VALUES(3,'lorena10','10% reducere',10,0);
INSERT INTO Discount(id,discount_name, discount_desc, disc_percentage, active) VALUES(4,'truewhey30','30% reducere la proteina true whey',30,1);
INSERT INTO Discount(id,discount_name, discount_desc, disc_percentage, active) VALUES(5,'fatemare15','15% reducere ca sa te faci mare',15,1);
INSERT INTO Discount(id,discount_name, discount_desc, disc_percentage, active) VALUES(6,'daddynoel30','30% reducere de la Daddy Noel',30.7,0);
INSERT INTO Discount(id,discount_name, discount_desc, disc_percentage, active) VALUES(7,'tonysaik25','25% reducere',25,1);
INSERT INTO Discount(id,discount_name, discount_desc, disc_percentage, active) VALUES(8,'accesoriinebunii','20% reducere la accesorii',20,0);
INSERT INTO Discount(id,discount_name, discount_desc, disc_percentage, active) VALUES(9,'creatina12','12% reducere ca sa te faci mare',12,1);
INSERT INTO Discount(id,discount_name, discount_desc, disc_percentage, active) VALUES(10,'cbum30','30% reducere de la CBum',30,1);

INSERT INTO Applied_Discount(prod_id, discount_id, starts_at,ends_at) VALUES(22,4,'2022-04-15','2022-10-15');
INSERT INTO Applied_Discount(prod_id, discount_id, starts_at,ends_at) VALUES(23,2,'2022-02-10','2022-08-15');
INSERT INTO Applied_Discount(prod_id, discount_id, starts_at,ends_at) VALUES(24,7,'2022-05-10','2022-12-10');
INSERT INTO Applied_Discount(prod_id, discount_id, starts_at,ends_at) VALUES(22,10,'2022-01-01','2022-06-06');
INSERT INTO Applied_Discount(prod_id, discount_id, starts_at,ends_at) VALUES(31,8,'2022-03-20','2022-10-31');

INSERT INTO Product(id, id_type, product_name, prod_desc, price) VALUES(50, 11, 'Creatina 500g', 'Creatina ca sa te faci mare', 59.99); -- query that violates integrity constraints

INSERT INTO Review(id, client_id, prod_id, review_text, created_at) VALUES(7,12,22, 'super','2022-04-06');
INSERT INTO Review(id, client_id, prod_id, review_text, created_at) VALUES(8,13,32,'naspa','2021-04-04');
INSERT INTO Review(id, client_id, prod_id, review_text, created_at) VALUES(9,16,26,'nu mai iau','2020-02-07');
INSERT INTO Review(id, client_id, prod_id, review_text, created_at) VALUES(10,17,27,'blana bomba','2022-03-08');
INSERT INTO Review(id, client_id, prod_id, review_text, created_at) VALUES(11,191,30,'imi place','2022-10-09');
INSERT INTO Review(id, client_id, prod_id, review_text, created_at) VALUES(12,19,29,'nu imi place','2021-05-15');

------------------- Update
UPDATE Product 
SET price = price + 30.5
WHERE id_type >= 4 AND id_type < 7;

UPDATE Client
SET phone = '0741981475'
WHERE first_name LIKE 'Alex%';

UPDATE Product_Type
SET name_type = 'Accesorii'
WHERE id = 9 OR id = 5;

UPDATE Product
SET price = price + 50
WHERE price BETWEEN 60 AND 100;

-------------------- Delete
DELETE FROM Address_of_Client
WHERE address_details IS NULL; 

DELETE FROM Product
WHERE id_type IN(2,4,8);

-------------------------- Select

----------------- UNION/OR
select c.id, c.first_name from Client c where c.phone IN('0756444321','0708602567')
union
select adr.client_id, adr.city_name from Address_of_Client adr where adr.client_id = 12;

select * from Review r where r.prod_id = 31 OR r.prod_id = 23;

----------------- INTERSECT/IN
select c.id from Client c 
intersect
select DISTINCT r.client_id from Review r where r.client_id BETWEEN 12 and 20;  -- DISTINCT

select id, product_name from Product where id_type IN(1,6,7,10);

---  EXCEPT/NOT IN
select p.id from Product p 
except
select DISTINCT r.prod_id from Review r; -- DISTINCT 

select pt.id from Product_Type pt 
where pt.id NOT IN(select p.id_type from Product p where p.price < 30);

---- subqueries in FROM
select d.id_type, d.prod_desc from (select * from Product p where p.product_name LIKE 'Proteine%') d;

select r.client_id from (select * from Address_of_Client a where a.address_details not in('Sancel', 'Sona', 'Tandarei')) r;

----- Joins
-- query with 3 tables join
select c.id, c.first_name, c.last_name, ac.city_name, r.review_text from Client c 
inner join Address_of_Client ac ON ac.client_id = c.id
inner join Review r ON r.client_id = c.id;

-- 2 m:n
-- left join
select c.id, c.first_name, r.id, r.review_text from Client c 
left join Review r ON r.client_id = c.id
ORDER BY c.first_name;	--  ORDER BY

--right join
select TOP 3 p.id, p.product_name, p.price, ap.discount_id, ap.starts_at, ap.ends_at from Product p 
right outer join Applied_Discount ap ON ap.prod_id = p.id;

--full outer join
select TOP 5 c.first_name, c.last_name, ac.address_details from Client c  -- TOP
full outer join Address_of_Client ac ON ac.client_id = c.id
ORDER BY c.last_name; -- ORDER BY

-- 2 queries using the IN operator to introduce a subquery in the WHERE clause; in at least one query, the subquery should include a subquery 
--in its own WHERE clause;
select pt.id, pt.name_type from Product_Type pt where pt.id in (select p.id_type from Product p where p.price > 100);

select r.review_text
from Review r
where r.prod_id IN (select p.id from Product p where p.id_type IN (
	select pt.id from Product_Type pt where pt.id IN(5,6,7,8,9)));

-- 2 queries with EXISTS

-- select the name of the client with id 12 if he applied a review
select c.first_name, c.last_name from Client c
where EXISTS(select * from Review r where c.id = 12);

-- select the products that have no discounts applied
select p.id, p.product_name from Product p
where NOT EXISTS(select ad.prod_id from Applied_Discount ad where p.id = ad.prod_id);

--  4 queries with the GROUP BY clause, 3 of which also contain the HAVING clause; 2 of the latter will also have a subquery in the HAVING clause; 
-- use the aggregation operators: COUNT, SUM, AVG, MIN, MAX;

select p.id_type, MAX(p.price)
from Product p
GROUP BY p.id_type;

select p.id_type, MAX(price) MaxPrice from Product p
GROUP BY p.id_type
HAVING MAX(price) >= 50;

select p.id_type, SUM(p.price) SumOnType
from Product p
GROUP BY p.id_type
HAVING SUM(p.price) > (select MIN(p2.price + 40) from Product p2);

select pt.name_type from Product p
inner join Product_Type pt ON p.id_type = pt.id
GROUP BY pt.name_type
HAVING AVG(p.price) > (select AVG(p2.price) from Product p2);

-- 4 queries using ANY and ALL to introduce a subquery in the WHERE clause; 2 of them should be rewritten with aggregation operators, 
-- while the other 2 should also be expressed with [NOT] IN.

select c.id, c.first_name from Client c
where c.id = ANY ( SELECT ac.client_id from Address_of_Client ac);

--- rewrite this with IN

select c.id, c.first_name from Client c
where c.id IN ( SELECT ac.client_id from Address_of_Client ac);

-----------------------

select * from Product_Type pt
where pt.id <> ALL ( SELECT p.id_type from Product p);

--- rewrite this with NOT IN

select * from Product_Type pt
where pt.id NOT IN( SELECT p.id_type from Product p);

-----------------------

select p.id, p.product_name from Product p
where p.id <> ALL ( select r.prod_id from Review r);

--- rewrite with aggregation operator

select p.id, p.product_name from Product p
where 0 = (
	select COUNT(*) from Review r where p.id = r.prod_id)

-----------------------

select p.product_name, p.price from Product p
where p.price = ANY ( SELECT p2.price from Product p2 where p2.price > 6.9);

--- rewrite this with aggregation operator

select p.product_name, p.price from Product p
where p.price > (SELECT MIN(p2.price) from Product p2);


-- another query with DISTINCT

select DISTINCT p.id_type, pt.name_type
from Product p
inner join Product_Type pt ON pt.id = p.id_type
where p.price > 80;