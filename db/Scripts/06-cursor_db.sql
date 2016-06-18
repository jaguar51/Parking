--вывод фи + моб. клиента
declare clients Cursor local forward_only read_only for
	select Surname, Name, Phone from Client

declare @surname varchar(35)
declare @name varchar(35)
declare @phone varchar(20)

open clients
fetch next from clients into @surname, @name, @phone
while @@FETCH_STATUS = 0
begin
	print(@surname + ' ' + @name + ' ' + @phone)
	fetch next from clients into @surname, @name, @phone
end
close clients
deallocate clients

--печатаем номера свободных боксов
declare freeLots Cursor local scroll read_only for
	select b.id
	from Rent_lot as a
	right outer join Parking_lot as b
	on a.ID_Lot = b.ID
	where a.End_date_reserve < GETDATE() or a.End_date_reserve is null
	group by b.ID

declare @num int

open freeLots
fetch next from freeLots into @num
while @@FETCH_STATUS = 0
begin
	print (@num)
	fetch next from freeLots into @num
end
close freeLots
deallocate freeLots

--Обновление цены например.
declare rentLots Cursor local scroll for
	select ID, Start_date_reserve, End_date_reserve, Price_per_day
	from Rent_lot
	for update of End_date_reserve, Price_per_day

declare @id int
declare @startDate datetime
declare @endDate datetime
declare @price money

open rentLots
fetch next from rentlots into @id, @startDate, @endDate, @price
while @@FETCH_STATUS = 0
begin
	if @id = 2
	begin
		update Rent_lot
		set Price_per_day = 50
		where current of rentLots
	end
	fetch next from rentlots into @id, @startDate, @endDate, @price
end
close rentLots
deallocate rentLots
