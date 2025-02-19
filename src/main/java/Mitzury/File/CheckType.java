package Mitzury.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckType {
    private final Statistics statistics;
    private final Map<String, List<String>> typeMap;

    public CheckType(Statistics statistics) {
        this.statistics = statistics;
        this.typeMap = new HashMap<>();
        typeMap.put("Integer", new ArrayList<>());
        typeMap.put("Float", new ArrayList<>());
        typeMap.put("String", new ArrayList<>());
        typeMap.put("Boolean", new ArrayList<>());
    }

    public void determineTypeAndPrint(String line) {
        if (line == null || line.isEmpty()) {
            System.out.println("Пустая строка");
            return;
        }

        String type = getType(line);
        if (type == null || type.equals("Unknown")) {
            System.out.println("Обнаружен неизвестный тип, значение: " + line);
            return;
        }

        typeMap.get(type).add(line);

        switch (type) {
            case "Integer":
                addNumberToStats("Integer", Integer.parseInt(line));
                break;
            case "Float":
                addNumberToStats("Float", Float.parseFloat(line));
                break;
            case "Boolean":
                // Boolean значения игнорируются в статистике
                break;
            case "String":
                statistics.addString("String", line);
                break;
        }
    }

    private void addNumberToStats(String type, double value) {
        statistics.addNumber(type, value);
    }

    public Map<String, List<String>> getTypeMap() {
        return typeMap;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public String getType(String str) {
        if (isInteger(str)) return "Integer";
        if (isFloat(str)) return "Float";
        if (isBoolean(str)) return "Boolean";
        if (isString(str)) return "String";
        return "Unknown";
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isBoolean(String str) {
        return str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false");
    }

    private boolean isString(String str) {
        return str != null && !str.isEmpty() && !isInteger(str) && !isFloat(str) && !isBoolean(str);
    }
}