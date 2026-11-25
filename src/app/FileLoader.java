package app;

import model.Incident;
import model.Service;
import storage.Storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileLoader {

    // Формат рядка: назва;версія;інстанси
    public static int loadServices(String path, Storage<String, Service> storage)
            throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        int rows = 0;
        for (int i = 1; i < lines.size(); i++) {      // i = 1: перший рядок це заголовок
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split(";");
            Service service = new Service(parts[0], parts[1], Integer.parseInt(parts[2]));
            storage.add(service);
            rows++;
        }
        return rows;
    }

    // Формат рядка: номер;сервіс;тривалість_хв
    public static int loadIncidents(String path, Storage<String, Incident> storage)
            throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        int rows = 0;
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split(";");
            Incident incident = new Incident(parts[0], parts[1], Integer.parseInt(parts[2]));
            storage.add(incident);
            rows++;
        }
        return rows;
    }
}