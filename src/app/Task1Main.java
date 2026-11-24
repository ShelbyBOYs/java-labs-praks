package app;

import generic.Box;
import generic.ObjBox;
import generic.Utils;
import model.ServiceVersion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 public class Task1Main {
    public static void main(String[] args) {
        // 1. Generic-контейнер
        Box<ServiceVersion> box = new Box<>();
        box.put(new ServiceVersion("auth", "1.0", 3));
        System.out.println("Box: " + box.get());


        // box.put("abc");   // incompatible types: String cannot be converted to ServiceVersion

        // 2. Версія на Object
        ObjBox objBox = new ObjBox();
        objBox.put("abc");   // компілюється без помилок
        try {
            ServiceVersion s = (ServiceVersion) objBox.get();
            System.out.println(s);
        } catch (ClassCastException e) {
            System.out.println("Помилка під час виконання: " + e);
        }

        // 3. Узагальнений метод
        List<ServiceVersion> services = new ArrayList<>();
        services.add(new ServiceVersion("auth", "1.0", 3));
        services.add(null);
        services.add(new ServiceVersion("pay", "2.1", 2));
        System.out.println("Не null елементів: " + Utils.countNonNull(services));

        List<String> words = Arrays.asList("a", null, "b");
        System.out.println("Не null слів: " + Utils.countNonNull(words));

        // 4. List<Integer> -> List<Number>
        List<Integer> ints = Arrays.asList(1, 2, 3);
        List<Double> doubles = Arrays.asList(1.5, 2.5);
        System.out.println("total(ints) = " + Utils.total(ints));
        System.out.println("total(doubles) = " + Utils.total(doubles));
    }
}