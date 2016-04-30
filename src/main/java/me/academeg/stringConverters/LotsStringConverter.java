package me.academeg.stringConverters;

import javafx.util.StringConverter;
import me.academeg.API.ParkingLot;

import java.util.HashMap;
import java.util.Map;

public class LotsStringConverter extends StringConverter<ParkingLot> {
    private Map<String, ParkingLot> map = new HashMap<>();

    @Override
    public String toString(ParkingLot object) {
        if (object != null) {
            String key = Integer.toString(object.getId());
            map.put(key, object);
            return key;
        }
        return "";
    }

    @Override
    public ParkingLot fromString(String string) {
        if (!map.containsKey(string)) {
            return null;
        }
        return map.get(string);
    }
}
