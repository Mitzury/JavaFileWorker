package Mitzury.App;

public class UsedParams {

    private String prefix;
    private boolean appendMode;
    private boolean printStats; // Флаг для вывода статистики
    private boolean fullStats;  // Тип статистики (полная или краткая)

    public UsedParams() {
        this.prefix = null;
        this.appendMode = false;
        this.printStats = false;
        this.fullStats = false;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setAppendMode(boolean appendMode) {
        this.appendMode = appendMode;
    }

    public boolean isAppendMode() {
        return appendMode;
    }

    public void setPrintStats(boolean printStats) {
        this.printStats = printStats;
    }

    public boolean isPrintStats() {
        return printStats;
    }

    public void setFullStats(boolean fullStats) {
        this.fullStats = fullStats;
    }

    public boolean isFullStats() {
        return fullStats;
    }

}
