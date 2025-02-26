package Mitzury.File;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private final Map<String, TypeStats> statsMap = new HashMap<>();

    public static class TypeStats {
        private int count = 0;
        private double sum = 0;
        private double min = Double.MAX_VALUE;
        private double max = Double.MIN_VALUE;
        private int minLength = Integer.MAX_VALUE;
        private int maxLength = Integer.MIN_VALUE;

        public void addNumber(double value) {
            count++;
            sum += value;
            if (value < min) min = value;
            if (value > max) max = value;
        }

        public void addString(String value) {
            count++;
            int length = value.length();
            if (length < minLength) minLength = length;
            if (length > maxLength) maxLength = length;
        }

        public String getBriefStats() {
            return "Количество: " + count;
        }

        public String getFullStats() {
            StringBuilder sb = new StringBuilder();
            sb.append("--Количество: ").append(count).append("\n");
            if (count > 0) {
                if (min != Double.MAX_VALUE && max != Double.MIN_VALUE) {
                    sb.append("--Минимальное значение: ").append(min).append("\n");
                    sb.append("--Максимальное значение: ").append(max).append("\n");
                    sb.append("--Сумма: ").append(sum).append("\n");
                    sb.append("--Среднее: ").append(sum / count).append("\n");
                }
                if (minLength != Integer.MAX_VALUE && maxLength != Integer.MIN_VALUE) {
                    sb.append("--Минимальная длина строки: ").append(minLength).append("\n");
                    sb.append("--Максимальная длина строки: ").append(maxLength).append("\n");
                }
            }
            return sb.toString();
        }
    }

    public void addNumber(String type, double value) {
        statsMap.computeIfAbsent(type, k -> new TypeStats()).addNumber(value);
    }

    public void addString(String type, String value) {
        statsMap.computeIfAbsent(type, k -> new TypeStats()).addString(value);
    }

    public String printStats(boolean fullStats) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, TypeStats> entry : statsMap.entrySet()) {
            result.append("-Тип: ").append(entry.getKey()).append("\n");
            if (fullStats) {
                result.append(entry.getValue().getFullStats());
            } else {
                result.append(entry.getValue().getBriefStats());
            }
            result.append("\n");
        }
        return result.toString();
    }
}