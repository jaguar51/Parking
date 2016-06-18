--без параметров: возвращает количество автомобилей на стоянке
create function GetCountFreeLot()
returns int
 begin
  declare @count int
  select @count = count(*) 
  from Park_car
  where Park_car.End_parking is null;
  return @count
 end
-- пример запуска
-- select dbo.GetFreeLot()

--с параметрами: цена на время стоимость стоянки
create function GetCost(@start date, @end date, @dayCost money)
returns money
 begin  
  return DATEDIFF(Day, @start, @end) * @dayCost
 end

--возвращающая нестандартный тип данных свободные боксы
CREATE FUNCTION GetFreeLot()
RETURNS TABLE
AS
 RETURN 
 (
	select * 
	from (
		select b.id, max(a.End_date_reserve) as max_date
		from Rent_lot as a
		right outer join Parking_lot as b
		on a.ID_Lot = b.ID
		group by b.ID
	) as t
	where t.max_date < GETDATE() or t.max_date is null
	)


-- неверно
-- CREATE FUNCTION GetFreeLot()
-- RETURNS TABLE
-- AS
--  RETURN 
--  (
-- 	select b.id, max(a.End_date_reserve) as max_date
-- 	from Rent_lot as a
-- 	right outer join Parking_lot as b
-- 	on a.ID_Lot = b.ID
-- 	where a.End_date_reserve < GETDATE() or a.End_date_reserve is null
-- 	group by b.ID
--  )

 --пример запуска
 --select * from dbo.GetFreeLot()
 