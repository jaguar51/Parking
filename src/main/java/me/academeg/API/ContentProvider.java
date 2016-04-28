package me.academeg.API;

import java.sql.*;
import java.time.LocalDate;
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
        if(connection!=null) {
            connection.close();
        }
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

    public void rentLot(Client client, ParkingLot lot, Employee employee,
                        LocalDate startDate, LocalDate endDate, float coast) throws SQLException {

        String updateTableSQL = "insert into Rent_lot (ID_Client, ID_Employee, ID_Lot, Price_per_day, " +
                "Start_date_reserve, End_date_reserve)\n" +
                "values (?, ?, ?, ?, convert(datetime, ? , 104), convert(datetime, ? , 104));";
        PreparedStatement preparedStatement = connection.prepareStatement(updateTableSQL);
        preparedStatement.setInt(1, client.getId());
        preparedStatement.setInt(2, employee.getId());
        preparedStatement.setInt(3, lot.getId());
        preparedStatement.setFloat(4, coast);
        preparedStatement.setString(5, startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        preparedStatement.setString(6, endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        preparedStatement.execute();
    }

    private void test() throws SQLException {
        Statement st = connection.createStatement();
        //Statement позволяет отправлять запросы базе данных
        ResultSet rs = st.executeQuery("select * from Park_car");
        //ResultSet получает результирующую таблицу
        int x = rs.getMetaData().getColumnCount();
        //результирующей таблице
        while(rs.next()){
            for(int i=1; i<=x;i++){
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }
        System.out.println();
        if(rs != null) {
            rs.close();
        }
        if(st != null) {
            st.close();
        }
    }

}
