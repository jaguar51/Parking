-- Представления

-- на 1 таблицу: вывод короткого имени клиента
create view ClientShort
as
	select ID, Surname, Phone
	from Client

-- на несколько
create view ClientAutoNumber
as
	select client.Surname, client.Name, client.Patronymic, automobile.Plate
	from 
	Client as client
	inner join Client_Auto as cl_auto
	on client.ID = cl_auto.ClientID
	inner join Auto as automobile
	on automobile.ID = cl_auto.AutoID

-- с агрегированными данными: стоимость аренды лота
create view CostRentLots
as
	select ID, ID_Client, ID_Lot, DATEDIFF(Day, Start_date_reserve, End_date_reserve)*Price_per_day as Cost
	from Rent_lot
