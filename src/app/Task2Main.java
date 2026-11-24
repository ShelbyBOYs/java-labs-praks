package app;

import model.ServiceVersion;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

 public class Task2Main {
    private static final int SIZE = 100_000;
    private static final int OPS = 1000;
    private static final int RUNS = 3;

    private static void fill(List<ServiceVersion> list) {
        for (int i = 0; i < SIZE; i++) {
            list.add(new ServiceVersion("svc" + i, "1." + (i % 10), i % 5 + 1));
        }
    }

    private static ServiceVersion[] makeTargets() {
        // шукаємо елементи з кінця колекції (найгірший випадок для списків)
        ServiceVersion[] targets = new ServiceVersion[OPS];
        for (int i = 0; i < OPS; i++) {
            int id = SIZE - 1 - i;
            targets[i] = new ServiceVersion("svc" + id, "1." + (id % 10), 1);
        }
        return targets;
    }

    private static long insertMiddle(List<ServiceVersion> list) {
        long t0 = System.nanoTime();
        for (int i = 0; i < OPS; i++) {
            list.add(list.size() / 2, new ServiceVersion("new" + i, "2.0", 1));
        }
        return (System.nanoTime() - t0) / 1_000_000;
    }

    private static long containsList(List<ServiceVersion> list, ServiceVersion[] targets) {
        long t0 = System.nanoTime();
        for (int i = 0; i < OPS; i++) {
            list.contains(targets[i]);
        }
        return (System.nanoTime() - t0) / 1_000_000;
    }

    private static long containsSet(Set<ServiceVersion> set, ServiceVersion[] targets) {
        long t0 = System.nanoTime();
        for (int i = 0; i < OPS; i++) {
            set.contains(targets[i]);
        }
        return (System.nanoTime() - t0) / 1_000_000;
    }

    public static void main(String[] args) {
        ServiceVersion[] targets = makeTargets();

        for (int run = 1; run <= RUNS; run++) {
            ArrayList<ServiceVersion> arrayList = new ArrayList<>();
            LinkedList<ServiceVersion> linkedList = new LinkedList<>();
            HashSet<ServiceVersion> hashSet = new HashSet<>();
            fill(arrayList);
            fill(linkedList);
            hashSet.addAll(arrayList);

            System.out.println("=== Запуск " + run + " ===");
            System.out.println("ArrayList  insert x1000: " + insertMiddle(arrayList) + " мс");
            System.out.println("LinkedList insert x1000: " + insertMiddle(linkedList) + " мс");

            System.out.println("ArrayList  contains x1000: " + containsList(arrayList, targets) + " мс");
            System.out.println("LinkedList contains x1000: " + containsList(linkedList, targets) + " мс");
            System.out.println("HashSet    contains x1000: " + containsSet(hashSet, targets) + " мс");
        }

        // Видалення в циклі for-each
        System.out.println("\n=== Видалення for-each ===");
        ArrayList<ServiceVersion> list1 = new ArrayList<>();
        fill(list1);
        try {
            for (ServiceVersion s : list1) {
                if (s.getInstances() == 1) {
                    list1.remove(s);
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Виняток: " + e);
        }

        // Видалення через Iterator
        System.out.println("\n=== Видалення через Iterator ===");
        ArrayList<ServiceVersion> list2 = new ArrayList<>();
        fill(list2);
        System.out.println("Було: " + list2.size());
        Iterator<ServiceVersion> it = list2.iterator();
        while (it.hasNext()) {
            if (it.next().getInstances() == 1) {
                it.remove();
            }
        }
        System.out.println("Стало: " + list2.size());
    }
}