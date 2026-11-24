package generic;

 public class Box<T> {
    private T value;
    public void put(T v) { value = v; }
    public T get() { return value; }
 }

