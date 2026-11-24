package generic;

 public class ObjBox {
    private Object value;

    public void put(Object value) { this.value = value; }

    public Object get() { return value; }
}