package me.academeg;

import me.academeg.API.ContentProvider;
import me.academeg.API.ParkingLot;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestMain {
    public static void main(String[] args) {
        ContentProvider contentProvider = new ContentProvider();
        try {
            contentProvider.open();
            ArrayList<ParkingLot> freeParkingLot = contentProvider.getFreeParkingLot();
            freeParkingLot.forEach(System.out::println);
            contentProvider.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
