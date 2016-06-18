--end_date не может быть меньше start_date
create trigger CheckTime on Park_car
after update
as
	
	declare @startTime datetime = (select Start_parking from inserted)
	if (@startTime is null) 
	begin
		raiserror('Start_parking can not be empty', 10, 1)
		rollback
	end

	declare @endTime datetime = (select End_parking from inserted)
	if (@endTime  < @startTime) 
	begin
		raiserror('End_parking can not be lower than Start_parking', 10, 1)
		rollback
	end
	
go

--insert & update стоимость стоянки > 0
create trigger CheckPriceParking on Rent_lot
after insert, update
as
	if (select Price_per_day from inserted) < 0
	begin
		raiserror('Cost can not be lower than 0', 10, 1)
		rollback		
	end
go

--instead of delete запретить удалять из таблицы park_car
create trigger InsteadDeleteParkCar on Park_car
instead of delete
as
	raiserror('You can not delete data from the table', 10, 1)
go
