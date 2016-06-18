--простой: на время окончания аренды лота
create nonclustered index EndDateRentLot
on Rent_lot(End_date_reserve)

--
create nonclustered index EndDateParkCar
on Park_car(End_parking)

--сложный: на ФИО клиента
create unique nonclustered index FullNameClient
on Client(Surname, Name, Patronymic)
with fillfactor=90
