package Mitzury.File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static Mitzury.File.CheckType.determineTypeAndPrint;
import static Mitzury.File.Writter.writeToDisk;


public class Reader {

    public static void readfile(String fileName) {
        System.out.println("Чтение файла: " + fileName);

        boolean isFileProcessed = false; // Флаг успешной обработки файла
        Set<String> processedLines = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!processedLines.contains(line)) {
                    processedLines.add(line);
                    determineTypeAndPrint(line);
                }
            }
            isFileProcessed = true; // Устанавливаем флаг, если файл успешно обработан
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла " + fileName + ": " + e.getMessage());
        }

        // Записываем данные в файлы только если файл был успешно обработан
        if (isFileProcessed) {
            writeToDisk(CheckType.getTypeMap());
        } else {
            System.out.println("Пропуск записи для файла: " + fileName);
        }

        System.out.println();
    }
}