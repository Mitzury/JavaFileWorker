package Mitzury;

import Mitzury.App.*;
import static Mitzury.App.Config.fullStats;
import static Mitzury.App.Config.printStats;
import static Mitzury.File.Reader.readfile;


public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: Не переданы аргументы");
            return;
        }
// Обработка параметров
        for (int i = 0; i < args.length; i++) {
            if ("-p".equals(args[i])) {
                if (i + 1 >= args.length) {
                    System.out.println("Ошибка: После параметра -p должен быть указан префикс.");
                    return;
                }
                String potentialPrefix = args[++i];
                if (potentialPrefix.endsWith(".txt")) {
                    System.out.println("Ошибка: Префикс не может быть именем файла (.txt).");
                    return;
                }
                Config.setPrefix(potentialPrefix);
            } else if ("-a".equals(args[i])) {
                Config.setAppendMode(true);
            } else if ("-f".equals(args[i]) || "-s".equals(args[i])) {
                if (printStats) {
                    // Если уже установлен флаг вывода статистики, выдаем ошибку
                    System.out.println("Ошибка: Нельзя одновременно использовать параметры -s и -f.");
                    return;
                }
                printStats = true; // Включаем вывод статистики
                fullStats = "-f".equals(args[i]); // Устанавливаем тип статистики
            }
        }

        // Если были использованы параметры -s или -f, устанавливаем их в Config
        if (printStats) {
            Config.setPrintStats(true);
            Config.setFullStats(fullStats);
        }

        // Обработка файлов
        for (String param : args) {
            if (param.endsWith(".txt") && !"-p".equals(param) && !"-a".equals(param) && !"-f".equals(param) && !"-s".equals(param)) {
                readfile(param);
            }
        }

        // Вывод статистики только если флаг установлен
        if (Config.isPrintStats()) {
            Mitzury.File.CheckType.getStatistics().printStats(Config.isFullStats());
        }
    }
}