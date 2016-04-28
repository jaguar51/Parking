package me.academeg.API;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {

    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private String phone;

    public Client() {
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static Client parse(ResultSet rs) throws SQLException {
        Client res = new Client();
        res.id = rs.getInt(1);
        res.surname = rs.getString(2);
        res.name = rs.getString(3);
        res.patronymic = rs.getString(4);
        res.phone = rs.getString(5);
        return res;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
