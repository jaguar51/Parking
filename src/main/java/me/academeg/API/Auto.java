package me.academeg.API;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Auto {

    private int id;
    private String plate;
    private String brand;
    private String color;
    private String addInf;

    public Auto() {
    }

    public int getId() {
        return id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAddInf() {
        return addInf;
    }

    public void setAddInf(String addInf) {
        this.addInf = addInf;
    }

    public static Auto parse(ResultSet rs) throws SQLException {
        Auto res = new Auto();
        res.id = rs.getInt(1);
        res.plate = rs.getString(2);
        res.brand = rs.getString(3);
        res.color = rs.getString(4);
        res.addInf = rs.getString(5);
        return res;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", plate='" + plate + '\'' +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", addInf='" + addInf + '\'' +
                '}';
    }
}
