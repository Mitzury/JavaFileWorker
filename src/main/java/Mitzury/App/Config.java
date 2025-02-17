package Mitzury.App;

public class Config {
    private static String prefix = null;
    private static boolean appendMode = false;
    public static boolean printStats = false; // Флаг для вывода статистики
    public static boolean fullStats = false;  // Тип статистики (полная или краткая)

    public static void setPrefix(String prefix) {
        Config.prefix = prefix;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setAppendMode(boolean appendMode) {
        Config.appendMode = appendMode;
    }

    public static boolean isAppendMode() {
        return appendMode;
    }

    public static void setPrintStats(boolean printStats) {
        Config.printStats = printStats;
    }

    public static boolean isPrintStats() {
        return printStats;
    }

    public static void setFullStats(boolean fullStats) {
        Config.fullStats = fullStats;
    }

    public static boolean isFullStats() {
        return fullStats;
    }
}