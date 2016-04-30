package me.academeg.stringConverters;

import javafx.util.StringConverter;
import me.academeg.API.Client;

import java.util.HashMap;
import java.util.Map;

public class ClientsStringConverter extends StringConverter<Client> {

    private Map<String, Client> map = new HashMap<>();

    @Override
    public String toString(Client object) {
        if (object != null) {
            String key = String.format("%s %s %s", object.getSurname(), object.getName(),
                    object.getPatronymic());
            map.put(key, object);
            return key;
        }
        return "";
    }

    @Override
    public Client fromString(String string) {
        if (!map.containsKey(string)) {
            return null;
        }
        return map.get(string);
    }
}

