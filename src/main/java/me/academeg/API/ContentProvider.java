package me.academeg.API;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ContentProvider {

    private static Connection connection = null;
    private static String username = "user";
    private static String password = "1234";
    private static String URL = "jdbc:jtds:sqlserver://localhost:1433;databaseName=parking";

    public void open() throws SQLException {
        DriverManager.registerDriver(new net.sourceforge.jtds.jdbc.Driver());
        //Загружаем драйвер
        connection = DriverManager.getConnection(URL, username, password);
    }

    public void close() throws SQLException {
        if(connection != null) {
            connection.close();
        }
    }

    public int getFreeParkingLotCount() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("declare @num int=5; exec getCountFreePlace @num output; select str(@num) as count;");
        rs.next();
        int res =rs.getInt(1);
        rs.close();
        st.close();
        return res;
    }

    public ArrayList<ParkingLot> getFreeParkingLot() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("select * from dbo.GetFreeLot()");
        ArrayList<ParkingLot> res = new ArrayList<>();
        while(rs.next()){
            res.add(new ParkingLot(rs.getInt(1), rs.getString(2)));
        }
        rs.close();
        st.close();
        return res;
    }

    public ArrayList<ParkingLot> getFreeParkingLot(Client client) throws SQLException {
        String getSQL = "select ID_Lot\n" +
                "from Rent_lot\n" +
                "where ID_Lot not in (\n" +
                "\tselect a.ID_Lot\n" +
                "\tfrom Rent_lot as a\n" +
                "\tinner join Park_car as b\n" +
                "\ton a.ID_Lot=b.ID_Lot\n" +
                "\twhere a.End_date_reserve > GETDATE() and b.End_parking is null\n" +
                ") and ID_Client = ? and End_date_reserve > GETDATE()";
        PreparedStatement preparedStatement = connection.prepareStatement(getSQL);
        preparedStatement.setInt(1, client.getId());
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<ParkingLot> res = new ArrayList<>();
        while(rs.next()){
            res.add(new ParkingLot(rs.getInt(1)));
        }
        rs.close();
        preparedStatement.close();
        return res;
    }

    public ArrayList<Auto> getAutoForParking(Client client) throws SQLException {
        String getSQL = "select b.ID, b.Plate, b.Brand, b.Color, b.Add_inf\n" +
                "from Client_Auto a\n" +
                "inner join Auto b\n" +
                "on a.AutoID = b.ID\n" +
                "where AutoID not in (\n" +
                "\tselect AutoID\n" +
                "\tfrom (\n" +
                "\t\tselect b.AutoID, case when max(case when a.End_parking is null then 1 else 0 end) = 0 then max(a.End_parking) end as End_parking\n" +
                "\t\tfrom Park_car as a\n" +
                "\t\tright outer join Client_Auto as b\n" +
                "\t\ton a.ID_Auto = b.AutoID\n" +
                "\t\twhere a.Start_parking is not null\n" +
                "\t\tgroup by b.AutoID\n" +
                "\t) as t\n" +
                "\twhere End_parking is null\n" +
                ") and ClientID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(getSQL);
        preparedStatement.setInt(1, client.getId());
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Auto> res = new ArrayList<>();
        while(rs.next()){
            res.add(Auto.parse(rs));
        }
        rs.close();
        preparedStatement.close();
        return res;
    }

    public ArrayList<AutoOnPark> getAutoPark(Client client) throws SQLException {
        String getSQL = "select b.ID as ID_Auto, b.Plate, b.Brand, b.Color, b.Add_inf, a.ID as ID_Operation\n" +
                "from Park_car as a\n" +
                "inner join Auto as b\n" +
                "on a.ID_Auto = b.ID\n" +
                "inner join Client_Auto as c\n" +
                "on a.ID_Auto = c.AutoID\n" +
                "where a.End_parking is null and c.ClientID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(getSQL);
        preparedStatement.setInt(1, client.getId());
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<AutoOnPark> res = new ArrayList<>();
        while(rs.next()){
            res.add(AutoOnPark.parse(rs));
        }
        rs.close();
        preparedStatement.close();
        return res;
    }

    public ArrayList<Employee> getEmployees() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("select * from Employee");
        ArrayList<Employee> res = new ArrayList<>();
        while(rs.next()){
            res.add(Employee.parse(rs));
        }
        rs.close();
        st.close();
        return res;
    }

    public ArrayList<Client> getClients() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("select * from Client");
        ArrayList<Client> res = new ArrayList<>();
        while(rs.next()){
            res.add(Client.parse(rs));
        }
        rs.close();
        st.close();
        return res;
    }

    public void createClient(Client client) throws SQLException {
        String insertSQL = "insert Client(Surname, Name, Patronymic, Phone) values (?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, client.getSurname());
        preparedStatement.setString(2, client.getName());
        preparedStatement.setString(3, client.getPatronymic());
        preparedStatement.setString(4, client.getPhone());
        preparedStatement.execute();
    }

    public void createAuto(Auto auto, Client client) throws SQLException {
//        insert auto
        String insertAutoSQL = "insert into Auto (Brand, Plate, Color, Add_inf) values (?, ?, ?, ?); " +
                "select @@IDENTITY as ID";
        PreparedStatement preparedStatement = connection.prepareStatement(insertAutoSQL);
        preparedStatement.setString(1, auto.getBrand());
        preparedStatement.setString(2, auto.getPlate());
        preparedStatement.setString(3, auto.getColor());
        preparedStatement.setString(4, auto.getAddInf());
//        get the id of the last inserted record
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        int recordId = rs.getInt(1);
        rs.close();
        preparedStatement.close();

//        client and auto connection
        String insertAutoClientSQL = "insert into Client_Auto (ClientID, AutoID) values (?, ?);";
        preparedStatement = connection.prepareStatement(insertAutoClientSQL);
        preparedStatement.setInt(1, client.getId());
        preparedStatement.setInt(2, recordId);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void rentLot(Client client, ParkingLot lot, Employee employee,
                        LocalDate startDate, LocalDate endDate, float coast) throws SQLException {

        String insertSQL = "insert into Rent_lot (ID_Client, ID_Employee, ID_Lot, Price_per_day, " +
                "Start_date_reserve, End_date_reserve)\n" +
                "values (?, ?, ?, ?, convert(datetime, ? , 104), convert(datetime, ? , 104));";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setInt(1, client.getId());
        preparedStatement.setInt(2, employee.getId());
        preparedStatement.setInt(3, lot.getId());
        preparedStatement.setFloat(4, coast);
        preparedStatement.setString(5, startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        preparedStatement.setString(6, endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void parkAuto(Client client, Employee employee, Auto auto, ParkingLot lot, LocalDateTime startDateTime)
            throws SQLException {

        String insertSQL = "insert into Park_car (ID_Client, ID_Employee, ID_Auto, ID_Lot, Start_parking)\n" +
                "values (?, ?, ?, ?, convert(datetime, ? , 120));";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setInt(1, client.getId());
        preparedStatement.setInt(2, employee.getId());
        preparedStatement.setInt(3, auto.getId());
        preparedStatement.setInt(4, lot.getId());
        preparedStatement.setString(5, startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void takeAuto(AutoOnPark auto, LocalDateTime endDateTime) throws SQLException {
        String updateSQL = "update Park_car set End_parking = convert(datetime, ? , 120) where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
        preparedStatement.setString(1, endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        preparedStatement.setInt(2, auto.getOperationId());
        preparedStatement.execute();
        preparedStatement.close();
    }
}
