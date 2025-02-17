package Mitzury.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckType {
    private static final Map<String, List<String>> typeMap = new HashMap<>();
    private static final Statistics statistics = new Statistics();

    static {
        typeMap.put("Integer", new ArrayList<>());
        typeMap.put("Float", new ArrayList<>());
        typeMap.put("Double", new ArrayList<>());
        typeMap.put("String", new ArrayList<>());
        typeMap.put("Boolean", new ArrayList<>());
    }

    public static void determineTypeAndPrint(String line) {
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
            case "Double":
                addNumberToStats("Double", Double.parseDouble(line));
                break;
            case "Boolean":
                // Boolean значения игнорируются в статистике
                break;
            case "String":
                statistics.addString("String", line);
                break;
        }
    }

    private static void addNumberToStats(String type, double value) {
        statistics.addNumber(type, value);
    }

    public static Map<String, List<String>> getTypeMap() {
        return typeMap;
    }

    public static Statistics getStatistics() {
        return statistics;
    }

    private static String getType(String str) {
        if (isInteger(str)) return "Integer";
        if (isFloat(str)) return "Float";
        if (isDouble(str)) return "Double";
        if (isBoolean(str)) return "Boolean";
        if (isString(str)) return "String";
        return "Unknown";
    }

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isBoolean(String str) {
        return str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false");
    }

    private static boolean isString(String str) {
        return str.matches("^[a-zA-Zа-яА-Я\\s\\-.,!?()\"':;]+$");
    }
}