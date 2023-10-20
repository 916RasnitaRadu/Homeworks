USE GymStore
GO

-- part 1

BEGIN TRAN
WAITFOR DELAY '00:00:06'
INSERT INTO Product VALUES(4,'Drogoase','O descriere sa fie bine sa nu fie rau', 150)
COMMIT TRAN

-- select * from Product
-- DELETE FROM Product where product_name LIKE 'Drogoase'