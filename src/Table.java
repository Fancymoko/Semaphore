// @author Steven Turmel
// @version 02 Dec 2017

import java.util.concurrent.Semaphore;

public class Table {
    static Semaphore semChef = new Semaphore(1);
    static Semaphore semHomer = new Semaphore(0);

    private Item item1;
    private Item item2;

    public void place(Item i, Item j) {
        try {
            semChef.acquire();
        } catch(InterruptedException e) {
            System.err.println(e);
        }
        item1 = i;
        item2 = j;
        System.out.println("Chef placed a " + item1.toString() + " and a " + item2.toString());
        semHomer.release();
    }

    public void get(String name, Item i) {
        try {
            semHomer.acquire();
        } catch(InterruptedException e) {
            System.err.println(e);
        }
        if (i != item1 && i != item2 ) {
            getItem();
            System.out.println(name + " ate a donut.");
            semChef.release();
        } else
            semHomer.release();
    }

    private void placeItem(Item i) {
        switch (i) {
            case SPRINKLES:
                item1 = Item.SPRINKLES;
                break;
            case FILLING:
                break;
            case SHELL:
                break;
            default:
                System.out.println("You shouldn't get this error");
        }
    }

    public void getItem() {
        item1 = null;
        item2 = null;
    }
}
