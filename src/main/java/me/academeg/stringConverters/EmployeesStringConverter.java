package me.academeg.stringConverters;

import javafx.util.StringConverter;
import me.academeg.API.Employee;

import java.util.HashMap;
import java.util.Map;

public class EmployeesStringConverter extends StringConverter<Employee> {

    private Map<String, Employee> map = new HashMap<>();

    @Override
    public String toString(Employee object) {
        if (object != null) {
            String key = String.format("%s %s %s", object.getSurname(), object.getName(),
                    object.getPatronymic());
            map.put(key, object);
            return key;
        }
        return "";
    }

    @Override
    public Employee fromString(String string) {
        if (!map.containsKey(string)) {
            return null;
        }
        return map.get(string);
    }
}
