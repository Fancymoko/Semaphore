// @author Steven Turmel
// @version 02 Dec 2017

public class Homer extends Thread{
    public Table table;
    public int count;
    public Item myItem;
    public Homer(Table t, int count, String name, Item item) {
        table = t;
        this.count = count;
        setName(name);
        myItem = item;
        start();
    }
    @Override
    public void run() {
        for(int i = 0; i < count; i++) {
            table.get(getName(), myItem);
        }
    }

}
