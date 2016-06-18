-- Positions
insert into Position(Title) values ('Охранник');

-- Employees
insert into Employee(Surname, Name, Patronymic, Phone, ID_Position)
values ('Петров', 'Андрей', 'Евгеньевич', '+79212802524', 1);
insert into Employee(Surname, Name, Patronymic, Phone, ID_Position)
values ('Иванов', 'Вячеслав', 'Андреевич', '+79212802524', 1);
insert into Employee(Surname, Name, Patronymic, Phone, ID_Position)
values ('Байдан', 'Сергей', 'Евгеньевич', '+79212802524', 1);
insert into Employee(Surname, Name, Patronymic, Phone, ID_Position)
values ('Хорошавин', 'Андрей', 'Евгеньевич', '+79212802524', 1);
insert into Employee(Surname, Name, Patronymic, Phone, ID_Position)
values ('Симонов', 'Роман', 'Анатольевич', '+79212802524', 1);

-- Update Employee
update Employee
set Phone = '+79643975557'
where ID = 5

-- Clients
insert Client(Surname, Name, Patronymic, Phone)
values ('Самсонов' , 'Юрий', 'Александрович', '+79315397375');
insert Client(Surname, Name, Patronymic, Phone)
values ('Афонин' , 'Роман', 'Геннадьевич', '+79311279453');
insert Client(Surname, Name, Patronymic, Phone)
values ('Орлов' , 'Роман', 'Роман', '+79213678940');
insert Client(Surname, Name, Patronymic, Phone)
values ('Гриценко' , 'Анна', 'Игорьевна', '+79314130179');
insert Client(Surname, Name, Patronymic, Phone)
values ('Трофимова' , 'Анастасия', 'Михайловна', '+79317431687');

-- Auto
insert into Auto (Brand, Plate, Color)
values ('BMW X5M', 'B333XB78', 'Золотой');
insert into Auto (Brand, Plate, Color)
values ('BMW M5', 'T122EH78', 'Красный');
insert into Auto (Brand, Plate, Color)
values ('Nissan X-Trail', 'A868KE51', 'Серебристый');
insert into Auto (Brand, Plate, Color)
values ('Volvo S60', 'A124BA178', 'Черный');
insert into Auto (Brand, Plate, Color)
values ('Toyota Land Cruiser Prado 120', 'A124BA51', 'Черный');
insert into Auto (Brand, Plate, Color)
values ('Toyota Land Cruiser Prado 120', 'A124BA98', 'Черный');

-- Client_Auto
insert into Client_Auto (ClientID, AutoID) values (1, 1);
insert into Client_Auto (ClientID, AutoID) values (1, 2);
insert into Client_Auto (ClientID, AutoID) values (2, 5);
insert into Client_Auto (ClientID, AutoID) values (3, 3);
insert into Client_Auto (ClientID, AutoID) values (4, 6);
insert into Client_Auto (ClientID, AutoID) values (5, 4);

-- Parking_lot
insert into Parking_lot (Add_inf) values ('Наличие разетки');
insert into Parking_lot (Add_inf) values (null);
insert into Parking_lot (Add_inf) values (null);
insert into Parking_lot (Add_inf) values (null);
insert into Parking_lot (Add_inf) values (null);
insert into Parking_lot (Add_inf) values (null);
insert into Parking_lot (Add_inf) values (null);
insert into Parking_lot (Add_inf) values (null);
insert into Parking_lot (Add_inf) values (null);
insert into Parking_lot (Add_inf) values (null);

-- Rent_lot
insert into Rent_lot (ID_Client, ID_Employee, ID_Lot, Price_per_day, Start_date_reserve, End_date_reserve)
values (1, 1, 1, 100, GETDATE(), GETDATE() + DAY(5));
insert into Rent_lot (ID_Client, ID_Employee, ID_Lot, Price_per_day, Start_date_reserve, End_date_reserve)
values (1, 5, 2, 100, GETDATE(), GETDATE() + DAY(25));

-- Park-car
insert into Park_car (ID_Client, ID_Employee, ID_Auto, ID_Lot, Start_parking)
values (1, 1, 1, 1, GETDATE());
insert into Park_car (ID_Client, ID_Employee, ID_Auto, ID_Lot, Start_parking)
values (1, 1, 2, 1, GETDATE() + DAY(2));

-- insert into Park_car (ID_Client, ID_Employee, ID_Auto, ID_Lot, End_parking)
-- values (1, 1, 1, 1, GETDATE() + DAY(2));
