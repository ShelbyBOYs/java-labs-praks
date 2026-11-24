package exp1;

 public class EventCounter {
    private int count = 0;

    public synchronized void add(){
        count++;
    }
    public int getCount() {
        return count;
    }
}