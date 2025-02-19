package Mitzury.File;

import Mitzury.App.Config;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Reader {

    public static void readfile(String fileName) {
        System.out.println("Чтение файла: " + fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Map<String, BufferedWriter> writers = new HashMap<>(); // Карта потоков записи

            while ((line = reader.readLine()) != null) {
                String type = CheckType.getType(line);

                if (type == null || type.equals("Unknown")) {
                    System.out.println("Обнаружен неизвестный тип, значение: " + line);
                    continue;
                }

                // Создаем или получаем BufferedWriter для текущего типа
                if (!writers.containsKey(type)) {
                    String outputFileName = (Config.getPrefix() != null ? Config.getPrefix() : "") + type + ".txt";
                    writers.put(type, new BufferedWriter(new FileWriter(outputFileName, Config.isAppendMode())));
                }

                // Готовим данные для записи
                Map<String, String> dataByType = new HashMap<>();
                dataByType.put(type, line);

                // Вызываем writeToDisk для записи текущей строки
                Writter.writeToDisk(writers, dataByType);

                // Обновляем статистику
                switch (type) {
                    case "Integer":
                        Statistics.addNumber("Integer", Integer.parseInt(line));
                        break;
                    case "Float":
                        Statistics.addNumber("Float", Float.parseFloat(line));
                        break;
                    case "Double":
                        Statistics.addNumber("Double", Double.parseDouble(line));
                        break;
                    case "Boolean":
                        // Boolean значения игнорируются в статистике
                        break;
                    case "String":
                        Statistics.addString("String", line);
                        break;
                }
            }

            // Закрываем все потоки записи
            for (BufferedWriter writer : writers.values()) {
                writer.close();
            }

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }
}