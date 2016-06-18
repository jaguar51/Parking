-- Client - Auto
select a.Surname, a.Name, c.Brand, c.Color, c.Plate
from Client as a
inner join Client_Auto as b
on a.ID = b.ClientID
inner join Auto c
on b.AutoID=c.ID

-- Employee - Position
select a.Surname, a.Name, a.Phone, b.Title
from Employee as a
inner join Position as b
on a.ID_Position = b.ID;

-- date
select datediff(DAY, Start_date_reserve, End_date_reserve) from Rent_lot;
select * from Rent_lot;

-- free place
select b.ID, b.Add_inf
from Park_car as a
right outer join Parking_lot as b
on a.ID_Lot = b.ID
where a.Start_parking is NULL;

--
DECLARE @num int=5;
EXEC getCountFreePlace @num OUTPUT
PRINT STR(@num)
