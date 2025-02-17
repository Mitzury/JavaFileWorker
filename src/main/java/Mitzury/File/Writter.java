package Mitzury.File;

import Mitzury.App.Config;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Writter {

    public static void writeToDisk(Map<String, List<String>> typeMap) {
        String prefix = Config.getPrefix(); // Получаем префикс из Config
        boolean appendMode = Config.isAppendMode(); // Получаем режим добавления

        for (Map.Entry<String, List<String>> entry : typeMap.entrySet()) {
            String type = entry.getKey();
            List<String> values = entry.getValue();

            if (!values.isEmpty()) {
                String fileName = (prefix != null ? prefix : "") + type + ".txt"; // Добавляем префикс
                try (FileWriter writer = new FileWriter(fileName, appendMode)) { // Используем appendMode
                    for (String value : values) {
                        writer.write(value + "\n");
                    }
                    System.out.println("Файл " + fileName + " успешно обновлен.");
                } catch (IOException e) {
                    System.out.println("Ошибка записи в файл " + fileName + ": " + e.getMessage());
                }
            }
        }
    }
}