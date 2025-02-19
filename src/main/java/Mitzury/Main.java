package Mitzury;

import Mitzury.App.ParseArguments;
import Mitzury.App.UsedParams;
import Mitzury.File.CheckType;
import Mitzury.File.Reader;
import Mitzury.File.Statistics;
import Mitzury.File.Writter;
import java.util.List;

import static Mitzury.App.ParseArguments.parseArguments;
import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {

        ParseArguments.ParseResult result = parseArguments(args);
        if (result == null) {
            System.out.print(getUsageText());
            exit(0);
            return;
        }

        UsedParams UsedParams = result.config();
        List<String> files = result.files();

        Statistics statistics = new Statistics();
        CheckType checkType = new CheckType(statistics);
        Writter writter = new Writter(UsedParams);
        Reader reader = new Reader(UsedParams, checkType, writter);
        reader.processFiles(files);
    }


    public static String getUsageText() {
        String s = "Использование: java -jar YourApp.jar [опции] <файл1> [файл2 ...]\n" +
                "Опции могут быть указаны в любом порядке:\n" +
                "  -p <префикс>   Установить префикс для выходных файлов\n" +
                "  -a             Включить режим добавления (append) в файлы\n" +
                "  -s             Включить вывод статистики\n" +
                "  -f             Включить подробную статистику\n" +
                "Пример: java -jar YourApp.jar -p data_ -a file1.txt file2.txt";
        return s;
    }

    public static void printUsage() {
        System.out.print(getUsageText());
    }
}