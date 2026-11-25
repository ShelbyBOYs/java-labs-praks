package storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Storage<K, T extends Keyed<K>> implements Iterable<T> {

    private final Map<K, T> records = new LinkedHashMap<>();

    // Повторний ключ: залишаємо перший запис, другий ігноруємо
    public boolean add(T record) {
        K key = record.getKey();
        if (records.containsKey(key)) {
            return false;
        }
        records.put(key, record);
        return true;
    }

    public Optional<T> find(K key) {
        return Optional.ofNullable(records.get(key));
    }

    public int size() {
        return records.size();
    }

    // Нова копія списку, відсортована переданим компаратором
    public List<T> sorted(Comparator<? super T> comparator) {
        List<T> copy = new ArrayList<>(records.values());
        Collections.sort(copy, comparator);
        return Collections.unmodifiableList(copy);
    }

    // Обхід for-each; remove() через цей ітератор заборонений
    @Override
    public Iterator<T> iterator() {
        return Collections.unmodifiableCollection(records.values()).iterator();
    }
}