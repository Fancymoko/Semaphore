// @author Steven Turmel
// @version 02 Dec 2017


import java.util.ArrayList;

public class Chef extends Thread{
    public Table table;
    public int count;

    public Chef(Table t, int count) {
        table = t;
        this.count = count;
        new Thread(this, "Chef").start();
    }
    @Override
    public void run() {
        for(int i = 0; i < count; i++) {
            Item tempItem[] = randomItem();
            table.place(tempItem[0], tempItem[1]);
        }
    }

    public Item[] randomItem() {
        int tempInt = (int) Math.ceil(Math.random() * 3) ;
        Item[] item;
        switch (tempInt) {
            case 1:
                item = new Item[] {Item.SPRINKLES, Item.FILLING};
                return item;
            case 2:
                item = new Item[] {Item.FILLING, Item.SHELL};
                return item;
            case 3:
                item = new Item[] {Item.SHELL, Item.SPRINKLES};
                return item;
            default:
                System.out.println("You made an error somewhere in your calculations.");
                item = new Item[] {Item.SPRINKLES, Item.FILLING};
                return item;
        }
    }
}
