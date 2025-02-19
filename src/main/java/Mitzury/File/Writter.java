package Mitzury.File;

import Mitzury.App.UsedParams;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Writter {
    private final UsedParams config;

    public Writter(UsedParams config) {
        this.config = config;
    }
    public void writeToDisk(Map<String, BufferedWriter> writers, Map<String, String> dataByType) {
        try {
            for (Map.Entry<String, String> entry : dataByType.entrySet()) {
                String type = entry.getKey();
                String value = entry.getValue();

                if (writers.containsKey(type)) {
                    BufferedWriter writer = writers.get(type);
                    writer.write(value);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    public Map<String, BufferedWriter> initializeWriters(Set<String> typesToCreate) {
        Map<String, BufferedWriter> writers = new HashMap<>();
        String prefix = config.getPrefix();
        boolean appendMode = config.isAppendMode();

        for (String type : typesToCreate) {
            String fileName = (prefix != null ? prefix : "") + type + ".txt";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, appendMode));
                writers.put(type, writer);
            } catch (IOException e) {
                System.err.println("Ошибка при создании файла " + type + ": " + e.getMessage());
            }
        }

        return writers;
    }

    public void closeWriters(Map<String, BufferedWriter> writers) {
        if (writers == null) return;

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