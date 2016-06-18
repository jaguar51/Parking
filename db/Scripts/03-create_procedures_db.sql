-- без параметров. печатает количество автомобилей на стоянке.
create procedure getCountParkCar 
as

	select count(*) 
	from Park_car
	where Park_car.End_parking is null;

go

-- с параметрами. печатает автомобили принадлежащие человеку с ФИО.
create procedure getAuto @surname varchar(35), @name varchar(35), @patronymic varchar(35)
as

	select Auto.Plate, Auto.Brand
	from Client
	inner join Client_Auto
	on Client.ID = Client_Auto.ClientID
	inner join Auto
	on Client_Auto.AutoID = Auto.ID
	where Client.Surname = @surname and Client.Name = @name and Client.Patronymic = @patronymic;

go

-- возврат параметров получение количества свободных боксов
create procedure getCountFreePlace @count int = 0 output
as

	select @count = COUNT(*)
	from (
		select * 
		from (
			select b.id, max(a.End_date_reserve) as max_date
			from Rent_lot as a
			right outer join Parking_lot as b
			on a.ID_Lot = b.ID
			group by b.ID
		) as t
		where t.max_date < GETDATE() or t.max_date is null
	) as table1;
	return

go
-- НЕВЕРНО
-- create procedure getCountFreePlace @count int = 0 output
-- as

-- 	select @count = COUNT(*)
-- 	from (
-- 		select b.id, max(a.End_date_reserve) as max_val
-- 		from Rent_lot as a
-- 		right outer join Parking_lot as b
-- 		on a.ID_Lot = b.ID
-- 		where a.End_date_reserve < GETDATE() or a.End_date_reserve is null
-- 		group by b.ID
-- 	) as table1;
-- 	return

-- go

/*
create procedure printAutoRegexp @regexp varchar(30)
as
	select Brand from Auto
	where brand like '%'+@regexp+'%';
go
*/
