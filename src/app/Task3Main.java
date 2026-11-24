package app;

import model.SvBoth;
import model.SvEquals;
import model.SvNone;

import java.util.HashSet;
import java.util.Set;

 public class Task3Main {
    public static void main(String[] args) {
        // Стан 1: без equals і hashCode
        Set<SvNone> s1 = new HashSet<>();
        s1.add(new SvNone("auth", "1.0", 3));
        s1.add(new SvNone("auth", "1.0", 5));
        System.out.println("без equals і hashCode: size = " + s1.size()
                + ", contains = " + s1.contains(new SvNone("auth", "1.0", 1)));

        // Стан 2: лише equals
        Set<SvEquals> s2 = new HashSet<>();
        s2.add(new SvEquals("auth", "1.0", 3));
        s2.add(new SvEquals("auth", "1.0", 5));
        System.out.println("лише equals: size = " + s2.size()
                + ", contains = " + s2.contains(new SvEquals("auth", "1.0", 1)));

        // Стан 3: equals і hashCode
        Set<SvBoth> s3 = new HashSet<>();
        s3.add(new SvBoth("auth", "1.0", 3));
        s3.add(new SvBoth("auth", "1.0", 5));
        System.out.println("equals і hashCode: size = " + s3.size()
                + ", contains = " + s3.contains(new SvBoth("auth", "1.0", 1)));

        // Зміна ключа в об'єкта, що вже лежить у множині
        Set<SvBoth> s4 = new HashSet<>();
        SvBoth x = new SvBoth("pay", "1.0", 2);
        s4.add(x);
        System.out.println("\nДо зміни ключа: contains(x) = " + s4.contains(x));
        x.setVersion("2.0");
        System.out.println("Після зміни ключа: contains(x) = " + s4.contains(x));
        System.out.println("contains(новий pay:2.0) = " + s4.contains(new SvBoth("pay", "2.0", 2)));
        System.out.println("size = " + s4.size());
    }
}