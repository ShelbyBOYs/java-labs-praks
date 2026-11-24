import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<MicroserviceVersion> list = List.of(
                new MicroserviceVersion("auth-service", "2.1.0", 5),
                new MicroserviceVersion("auth-service", "2.0.0", 2),
                new MicroserviceVersion("payment-service", "1.4.2", 8),
                new MicroserviceVersion("payment-service", "1.3.0", 3),
                new MicroserviceVersion("catalog-service", "3.0.1", 12),
                new MicroserviceVersion("catalog-service", "2.9.0", 1),
                new MicroserviceVersion("order-service", "1.0.0", 4),
                new MicroserviceVersion("notification-service", "0.9.5", 2),
                new MicroserviceVersion("gateway", "4.2.0", 6)
        );

        experiment1(list);
        experiment2(list);
        experiment3(list);
    }

    // ===================== ДОСЛІД 1: сортування =====================
    static void experiment1(List<MicroserviceVersion> source) {
        System.out.println("===== ДОСЛІД 1 =====");

        // 1) анонімний клас
        List<MicroserviceVersion> a = new ArrayList<>(source);
        a.sort(new Comparator<>() {
            @Override
            public int compare(MicroserviceVersion x, MicroserviceVersion y) {
                return Integer.compare(x.getInstances(), y.getInstances());
            }
        });
        System.out.println("Анонімний клас:   " + a);

        // 2) лямбда-вираз
        List<MicroserviceVersion> b = new ArrayList<>(source);
        b.sort((x, y) -> Integer.compare(x.getInstances(), y.getInstances()));
        System.out.println("Лямбда:           " + b);

        // 3) Comparator.comparing... з посиланням на метод
        List<MicroserviceVersion> c = new ArrayList<>(source);
        c.sort(Comparator.comparingInt(MicroserviceVersion::getInstances));
        System.out.println("Посилання метод:  " + c);


        /*
        int calls = 0;
        List<MicroserviceVersion> d = new ArrayList<>(source);
        d.sort((x, y) -> {
            calls++; // error: local variables referenced from a lambda expression
                     // must be final or effectively final
            return Integer.compare(x.getInstances(), y.getInstances());
        });
        */
    }

    // ===================== ДОСЛІД 2: Stream API =====================
    // цикл for-each
    static int sumInstancesLoop(List<MicroserviceVersion> list) {
        int sum = 0;
        for (MicroserviceVersion v : list) {
            if (v.getInstances() > 3) {
                sum += v.getInstances();
            }
        }
        return sum;
    }

    // Stream API
    static int sumInstancesStream(List<MicroserviceVersion> list) {
        return list.stream()
                .filter(v -> v.getInstances() > 3)
                .mapToInt(MicroserviceVersion::getInstances)
                .sum();
    }

    static void experiment2(List<MicroserviceVersion> list) {
        System.out.println("\n===== ДОСЛІД 2 =====");
        System.out.println("цикл: " + sumInstancesLoop(list)
                + "  потік: " + sumInstancesStream(list));

        // Конвеєр зберігаємо у змінну типу Stream
        Stream<MicroserviceVersion> pipeline = list.stream()
                .filter(v -> v.getInstances() > 3)
                .peek(v -> System.out.println("peek " + v));

        // --- ВАРІАНТ А: без термінальної операції (нічого не надрукується) ---
        System.out.println("з термінальної операцією:");

        // --- ВАРІАНТ Б: з термінальною операцією.---
        int total = pipeline.mapToInt(MicroserviceVersion::getInstances).sum();
         System.out.println("Сума з термінальною операцією: " + total);
    }

    // ===================== ДОСЛІД 3: Optional =====================
    static void experiment3(List<MicroserviceVersion> list) {
        System.out.println("\n===== ДОСЛІД 3 =====");

        // Пошук без результату: версія з понад 1000 інстансів
        Optional<MicroserviceVersion> none = list.stream()
                .filter(v -> v.getInstances() > 1000)
                .findFirst();
        System.out.println(none);

        // get() на порожньому Optional: виняток
        try {
            none.get();
        } catch (Exception e) {
            System.out.println("Виняток: " + e);
        }

        String r1 = list.stream()
                .filter(v -> v.getInstances() > 1000)
                .findFirst()
                .map(v -> v.getName() + " " + v.getVersion())
                .orElse("не знайдено");
        System.out.println("пошук без результату: " + r1);

        // Умова з досліду 2 (більше 3 інстансів): елемент є
        String r2 = list.stream()
                .filter(v -> v.getInstances() > 3)
                .findFirst()
                .map(v -> v.getName() + " " + v.getVersion())
                .orElse("не знайдено");
        System.out.println("пошук за умовою досліду 2: " + r2);
    }
}