package Mitzury;

import Mitzury.App.Config;
import Mitzury.File.Reader;

public class Main {

    public static void main(String[] args) {
        // Проверяем наличие аргументов
        if (args.length == 0) {
            System.out.println("Ошибка: Не переданы аргументы");
            return;
        }

        try {
            // Разбираем параметры командной строки
            parseArguments(args);

            // Находим все файлы для обработки
            String[] files = findFiles(args);

            // Обрабатываем каждый файл
            for (String file : files) {
                Reader.readfile(file);
            }

            // Выводим статистику, если это требуется
            if (Config.isPrintStats()) {
                Mitzury.File.CheckType.getStatistics().printStats(Config.isFullStats());
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Разбирает параметры командной строки.
     *
     * @param args массив аргументов
     * @throws IllegalArgumentException если параметры указаны неверно
     */
    private static void parseArguments(String[] args) throws IllegalArgumentException {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-p": // Префикс
                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException("После параметра -p должен быть указан префикс.");
                    }
                    String potentialPrefix = args[++i];
                    if (potentialPrefix.endsWith(".txt")) {
                        throw new IllegalArgumentException("Префикс не может быть именем файла (.txt).");
                    }
                    Config.setPrefix(potentialPrefix);
                    break;

                case "-a": // Режим добавления
                    Config.setAppendMode(true);
                    break;

                case "-f", "-s": // Статистика
                    if (Config.isPrintStats()) {
                        throw new IllegalArgumentException("Нельзя одновременно использовать параметры -s и -f.");
                    }
                    Config.setPrintStats(true);
                    Config.setFullStats("-f".equals(args[i]));
                    break;

                default:
                    // Игнорируем непонятные параметры, если они не являются файлами
                    break;
            }
        }
    }

    /**
     * Находит все файлы среди аргументов командной строки.
     * @param args массив аргументов
     * @return массив файлов для обработки
     */
    private static String[] findFiles(String[] args) {
        return java.util.Arrays.stream(args)
                .filter(arg -> arg.endsWith(".txt") && !"-p".equals(arg) && !"-a".equals(arg) && !"-f".equals(arg) && !"-s".equals(arg))
                .toArray(String[]::new);
    }
}