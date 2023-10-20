USE GymStore
go

-- Product(id: 2, id_type: 2, product_name: Proamino 390g, .., price: 113)
-- Client(id: 12, first_name: Alex, last_name: Pop, ...)

BEGIN TRAN
UPDATE Product SET price=113 WHERE id = 2
WAITFOR DELAY '00:00:10'
UPDATE Client SET last_name='Hornatiu' WHERE id =12
COMMIT TRAN

SELECT * FROM Product
 Select * from Client
