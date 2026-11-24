import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Використання: java Main <файл> <номер інциденту>");
            return;
        }
        Path file = Path.of(args[0]);
        String key = args[1];

        // рядки даних без заголовка і порожніх рядків
        List<String> rows = Files.readAllLines(file, StandardCharsets.UTF_8).stream()
                .skip(1)
                .filter(s -> !s.isBlank())
                .toList();

        // рядки -> об'єкти, повтори прибираються за повним збігом полів
        List<Incident> unique = rows.stream()
                .map(Incident::parse)
                .distinct()
                .toList();

        System.out.println("Рядків у файлі: " + rows.size()
                + ", унікальних записів: " + unique.size());

        printByService(unique);
        printTop5(unique);
        printSearch(unique, key);
    }

    // підсумки за сервісами: кількість і сумарна тривалість, за алфавітом
    static void printByService(List<Incident> incidents) {
        Map<String, IntSummaryStatistics> stats = incidents.stream()
                .collect(Collectors.groupingBy(
                        Incident::service,
                        TreeMap::new,
                        Collectors.summarizingInt(Incident::durationMin)));

        System.out.println("Сервіси (кількість, сумарна тривалість, хв):");
        stats.forEach((service, st) ->
                System.out.printf("%-22s %3d %6d%n", service, st.getCount(), st.getSum()));
    }

    // топ-5 за тривалістю, за рівності тривалості за номером
    static void printTop5(List<Incident> incidents) {
        System.out.println("Топ-5 за тривалістю:");
        incidents.stream()
                .sorted(Comparator.comparingInt(Incident::durationMin).reversed()
                        .thenComparing(Incident::number))
                .limit(5)
                .forEach(i -> System.out.println(
                        i.durationMin() + " " + i.number() + " " + i.service() + " " + i.severity()));
    }

    // пошук за ключем без винятку і без null
    static void printSearch(List<Incident> incidents, String key) {
        String result = incidents.stream()
                .filter(i -> i.number().equals(key))
                .findFirst()
                .map(Incident::describe)
                .orElse("не знайдено");
        System.out.println("Пошук " + key + ": " + result);
    }
}