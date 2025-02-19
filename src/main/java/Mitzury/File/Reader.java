package Mitzury.File;

import Mitzury.App.UsedParams;

import java.io.*;
import java.util.*;

public class Reader {
    private final UsedParams config;
    private final CheckType checkType;
    private final Writter writter;

    public Reader(UsedParams config, CheckType checkType, Writter writter) {
        this.config = config;
        this.checkType = checkType;
        this.writter = writter;
    }

    public void readFile(String fileName) {
        System.out.println("Чтение файла: " + fileName);

        Set<String> foundTypes = new HashSet<>(); // Для хранения найденных типов
        Map<String, BufferedWriter> writers = null;

        try {
            // Сначала читаем файл, чтобы определить, какие типы есть
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String type = checkType.getType(line);
                    if (type != null && !type.equals("Unknown")) {
                        foundTypes.add(type);
                    }
                }
            }

            // Если нет типов, ничего не делаем
            if (foundTypes.isEmpty()) {
                System.err.println("Предупреждение: В файле " + fileName + " не найдено ни одного известного типа данных.");
                return;
            }

            // Инициализируем writers только для найденных типов
            writers = writter.initializeWriters(foundTypes);

            // Перечитываем файл для записи
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String type = checkType.getType(line);

                    if (type == null || type.equals("Unknown")) {
                        System.out.println("Обнаружен неизвестный тип, значение: " + line);
                        continue;
                    }

                    // Готовим данные для записи
                    Map<String, String> dataByType = new HashMap<>();
                    dataByType.put(type, line);

                    // Вызываем writeToDisk для записи текущей строки
                    writter.writeToDisk(writers, dataByType);

                    // Обновляем статистику
                    switch (type) {
                        case "Integer":
                            checkType.getStatistics().addNumber("Integer", Integer.parseInt(line));
                            break;
                        case "Float":
                            checkType.getStatistics().addNumber("Float", Float.parseFloat(line));
                            break;
                        case "String":
                            checkType.getStatistics().addString("String", line);
                            break;
                        case "Boolean":
                            // Boolean игнорируется в статистике
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        } finally {
            if (writers != null) {
                writter.closeWriters(writers);
            }
        }
    }

    // Новый метод для обработки списка файлов
    public void processFiles(List<String> files) {
        boolean processedAtLeastOneFile = false;
        for (String file : files) {
            File f = new File(file);
            if (f.exists() && f.isFile() && f.canRead()) { // Проверяем существование, тип и читаемость
                System.out.println("\nОбработка файла: " + file);
                try {
                    readFile(file);
                    processedAtLeastOneFile = true;
                } catch (Exception e) {
                    System.err.println("Предупреждение: Ошибка при обработке файла " + file + ": " + e.getMessage());
                }
            } else {
                System.err.println("Предупреждение: Файл " + file + " не найден, недоступен или не может быть прочитан. Пропущен.");
            }
        }

        if (!processedAtLeastOneFile) {
            System.err.println("Предупреждение: Ни один файл не был обработан.");
        }
    }
}