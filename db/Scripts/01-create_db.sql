CREATE TABLE Employee (
	ID int IDENTITY(1,1) NOT NULL, 
	Surname nvarchar(35) NULL, 
	Name nvarchar(35) NOT NULL, 
	Patronymic nvarchar(35) NULL, 
	Phone nvarchar(20) NOT NULL, 
	ID_Position int NOT NULL,
	PRIMARY KEY (ID)
);
CREATE TABLE Position (
	ID int IDENTITY(1,1) NOT NULL, 
	Title nvarchar(50) NOT NULL, 
	PRIMARY KEY (ID)
);
CREATE TABLE Client (
	ID int IDENTITY(1,1) NOT NULL, 
	Surname nvarchar(35) NULL, 
	Name nvarchar(35) NOT NULL, 
	Patronymic nvarchar(35) NULL, 
	Phone nvarchar(20) NOT NULL, 
	PRIMARY KEY (ID)
);
CREATE TABLE Parking_lot (
	ID int IDENTITY(1,1) NOT NULL, 
	Add_inf nvarchar(255) NULL, 
	PRIMARY KEY (ID)
);
CREATE TABLE Auto (
	ID int IDENTITY(1,1) NOT NULL,
	Plate nvarchar(15) NOT NULL,
	Brand nvarchar(30) NOT NULL,
	Color nvarchar(20) NOT NULL,
	Add_inf nvarchar(255) NULL,
	PRIMARY KEY (ID)
);
CREATE TABLE Rent_lot (
	ID int IDENTITY(1,1) NOT NULL, 
	ID_Client int NOT NULL, 
	ID_Lot int NOT NULL, 
	ID_Employee int NOT NULL, 
	Start_date_reserve date NOT NULL, 
	End_date_reserve date NOT NULL, 
	Price_per_day money NOT NULL, 
	PRIMARY KEY (ID)
);
CREATE TABLE Park_car (
	ID int IDENTITY(1,1) NOT NULL, 
	ID_Client int NOT NULL, 
	ID_Employee int NOT NULL, 
	ID_Auto int NOT NULL, 
	ID_Lot int NOT NULL, 
	Start_parking datetime NULL, 
	End_parking datetime NULL, 
	PRIMARY KEY (ID)
);
CREATE TABLE Client_Auto (
	ClientID int NOT NULL, 
	AutoID int NOT NULL,
	PRIMARY KEY (ClientID, AutoID)
);


ALTER TABLE Employee ADD CONSTRAINT EmployeeFK FOREIGN KEY (ID_Position) REFERENCES Position (ID);

ALTER TABLE Client_Auto ADD CONSTRAINT Client_AutFKClient FOREIGN KEY (ClientID) REFERENCES Client (ID);
ALTER TABLE Client_Auto ADD CONSTRAINT Client_AutFKAuto FOREIGN KEY (AutoID) REFERENCES Auto (ID);

ALTER TABLE Rent_lot ADD CONSTRAINT Rent_lotFKEmployee FOREIGN KEY (ID_Employee) REFERENCES Employee (ID);
ALTER TABLE Rent_lot ADD CONSTRAINT Rent_lotFKClient FOREIGN KEY (ID_Client) REFERENCES Client (ID);
ALTER TABLE Rent_lot ADD CONSTRAINT Rent_lotFKParking_lot FOREIGN KEY (ID_Lot) REFERENCES Parking_lot (ID);

ALTER TABLE Park_car ADD CONSTRAINT Park_carFKEmployee FOREIGN KEY (ID_Employee) REFERENCES Employee (ID);
ALTER TABLE Park_car ADD CONSTRAINT Park_carFKClient FOREIGN KEY (ID_Client) REFERENCES Client (ID);
ALTER TABLE Park_car ADD CONSTRAINT Park_carFKParking_lot FOREIGN KEY (ID_Lot) REFERENCES Parking_lot (ID);
ALTER TABLE Park_car ADD CONSTRAINT Park_carFKAuto FOREIGN KEY (ID_Auto) REFERENCES Auto (ID);
