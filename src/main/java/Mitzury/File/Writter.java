package Mitzury.File;

import Mitzury.App.Config;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Writter {

    /**
     * Записывает данные в файлы потоково.
     *
     * @param typeMap карта, где ключ — тип данных, значение — список строк этого типа.
     */
    public static void writeToDisk(Map<String, BufferedWriter> writers, Map<String, String> dataByType) {
        String prefix = Config.getPrefix(); // Получаем префикс из Config
        boolean appendMode = Config.isAppendMode(); // Режим добавления

        try {
            // Создаем или открываем файлы для записи
            for (String type : dataByType.keySet()) {
                String fileName = (prefix != null ? prefix : "") + type + ".txt"; // Добавляем префикс
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, appendMode));
                writers.put(type, writer); // Сохраняем поток записи для каждого типа
            }

            // Записываем данные в соответствующие файлы
            for (Map.Entry<String, String> entry : dataByType.entrySet()) {
                String type = entry.getKey();
                String value = entry.getValue();

                if (writers.containsKey(type)) {
                    try (BufferedWriter writer = writers.get(type)) {
                        writer.write(value);
                        writer.newLine(); // Добавляем новую строку после записи значения
                    } catch (IOException e) {
                        System.err.println("Ошибка записи в файл типа " + type + ": " + e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Общая ошибка записи: " + e.getMessage());
        } finally {
            // Закрываем все открытые потоки записи
            for (BufferedWriter writer : writers.values()) {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии потока записи: " + e.getMessage());
                }
            }
        }
    }
}