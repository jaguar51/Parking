package me.academeg.API;

public class ParkingLot {

    private int id;
    private String description;

    public ParkingLot() {
    }

    public ParkingLot(int id) {
        this.id = id;
    }

    public ParkingLot(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ParkingLot{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
