package generic;

import java.util.List;

 public class Utils {

    // узагальнений метод: працює зі списком будь-якого типу
    public static <T> int countNonNull(List<T> list) {
        int count = 0;
        for (T item : list) {
            if (item != null) count++;
        }
        return count;
    }

    // початкова версія (для фіксації помилки компіляції):
    // public static double total(List<Number> list) {

    // виправлена версія: приймає List<Integer>, List<Double>, List<Number>
    public static double total(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        return sum;
    }
}