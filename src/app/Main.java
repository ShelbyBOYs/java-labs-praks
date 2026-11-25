package app;

import model.Incident;
import model.Service;
import sorting.IncidentByDuration;
import sorting.ServiceByInstances;
import storage.Storage;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Storage<String, Service> services = new Storage<>();
        Storage<String, Incident> incidents = new Storage<>();

        try {
            int serviceRows = FileLoader.loadServices("data/v3_services.txt", services);
            int incidentRows = FileLoader.loadIncidents("data/v3_incidents.txt", incidents);

            System.out.println("Сервіси: рядків у файлі " + serviceRows
                    + ", записів у сховищі " + services.size());
            System.out.println("Інциденти: рядків у файлі " + incidentRows
                    + ", записів у сховищі " + incidents.size());
        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
            return;
        }

        // Пошук за ключем
        System.out.println("\n=== Пошук за ключем ===");
        printSearch("auth-service", services.find("auth-service"));
        printSearch("cart-service", services.find("cart-service"));
        printSearch("unknown-service", services.find("unknown-service"));
        printSearch("INC-5191", incidents.find("INC-5191"));
        printSearch("INC-0000", incidents.find("INC-0000"));

        // Сортування
        List<Service> byInstances = services.sorted(new ServiceByInstances());
        printAll("Сервіси за кількістю інстансів", byInstances);

        List<Incident> byDuration = incidents.sorted(new IncidentByDuration());
        printAll("Інциденти за тривалістю", byDuration);

        // Перевірка, що в результаті сортування нічого не загублено
        System.out.println("\n=== Перевірка повноти ===");
        System.out.println("Сервіси: у сховищі " + services.size()
                + ", у відсортованому списку " + byInstances.size());
        System.out.println("Інциденти: у сховищі " + incidents.size()
                + ", у відсортованому списку " + byDuration.size());

        // Обхід for-each
        System.out.println("\n=== Обхід for-each ===");
        int count = 0;
        for (Service s : services) {
            count++;
        }
        System.out.println("Пройдено сервісів: " + count);

        // Захист внутрішнього стану
        System.out.println("\n=== Спроби змінити сховище ззовні ===");
        try {
            byInstances.add(new Service("hack-service", "0.0.0", 1));
        } catch (UnsupportedOperationException e) {
            System.out.println("add у відсортований список: UnsupportedOperationException");
        }
        try {
            Iterator<Service> it = services.iterator();
            it.next();
            it.remove();
        } catch (UnsupportedOperationException e) {
            System.out.println("remove через ітератор: UnsupportedOperationException");
        }
        System.out.println("Розмір сховища після спроб: " + services.size());
    }

    private static <T> void printSearch(String key, Optional<T> result) {
        if (result.isPresent()) {
            System.out.println("Пошук " + key + ": " + result.get());
        } else {
            System.out.println("Пошук " + key + ": не знайдено");
        }
    }

    private static <T> void printAll(String title, List<T> list) {
        System.out.println("\n=== " + title + " (" + list.size() + " записів) ===");
        for (T item : list) {
            System.out.println(item);
        }
    }
}