package me.academeg.API;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee {

    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private String phone;
    private int position;

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public int getPosition() {
        return position;
    }

    public static Employee parse(ResultSet rs) throws SQLException {
        Employee res = new Employee();
        res.id = rs.getInt(1);
        res.surname = rs.getString(2);
        res.name = rs.getString(3);
        res.patronymic = rs.getString(4);
        res.phone = rs.getString(5);
        res.position = rs.getInt(6);
        return res;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phone='" + phone + '\'' +
                ", position=" + position +
                '}';
    }
}
