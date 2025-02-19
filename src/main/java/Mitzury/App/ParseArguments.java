package Mitzury.App;

import java.util.ArrayList;
import java.util.List;

import static Mitzury.Main.printUsage;

public class ParseArguments {

    public record ParseResult(UsedParams config, List<String> files) {}

    public static ParseResult parseArguments(String[] args) {
        if (args.length == 0) {
            printUsage();
            return null;
        }

        List<String> files = new ArrayList<>();
        UsedParams config = new UsedParams();
        String prefix = null;
        boolean appendMode = false;
        boolean printStats = false;
        boolean fullStats = false;


        int i = 0;
        while (i < args.length) {
            String arg = args[i++];

            if (arg.startsWith("-")) {

                String option = arg.substring(1);
                switch (option) {
                    case "p":
                        if (i < args.length && !args[i].startsWith("--")) {
                            prefix = args[i++];
                        } else {
                            System.err.println("Ошибка: после -p должен быть указан префикс.");
                            return null;
                        }
                        break;
                    case "a":
                        appendMode = true;
                        break;
                    case "s":
                        printStats = true;
                        break;
                    case "f":
                        fullStats = true;
                        break;
                    default:
                        System.err.println("Неизвестный параметр: " + arg);
                        printUsage();
                        return null;
                }
            } else {
                files.add(arg);
            }
        }

        if (prefix != null) {
            config.setPrefix(prefix);
        }
        config.setAppendMode(appendMode);
        config.setPrintStats(printStats);
        config.setFullStats(fullStats);

        // Если файлов нет, показываем предупреждение, но продолжаем
        if (files.isEmpty()) {
            System.err.println("Предупреждение: не указаны файлы для обработки.");
            printUsage();
            return new ParseResult(config, new ArrayList<>()); // Возвращаем пустой список файлов
        }

        return new ParseResult(config, files);
    }
}
