// @author Steven Turmel
// @version 02 Dec 2017

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Donut extends Thread{
    static Table table = new Table();
    static int count;
    static Semaphore semHomer = new Semaphore(0);
    static Semaphore semTable = new Semaphore(1);
    static Random r;
    static boolean cont = true;

    public static void main(String args[]) {
        if (args.length > 0) {
            count = Integer.parseInt(args[0]);
        } else {
            count = 10;
        }
        Donut donut = new Donut();
        donut.start();
    }

    public void run() {
        Homer homer1 = new Homer("Homer Sprinkles", Item.SPRINKLES);
        Homer homer2 = new Homer("Homer Filling", Item.FILLING);
        Homer homer3 = new Homer("Homer Shell", Item.SHELL);
        Chef chef = new Chef();
        chef.start();
        homer1.start();
        homer2.start();
        homer3.start();
        System.out.println("All threads started.");
    }

    public class Chef extends Thread {

        public Chef() {
            r = new Random();
        }
        public void run() {
            System.out.println("test");
            for(int i = 0; i < count; i++) {
                try {
                    semTable.acquire();
                    Item tempItem[] = randomItem();
                    table.place(tempItem[0], tempItem[1]);
                    semHomer.release();
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
            cont = false;
        }

        public Item[] randomItem() {
            int tempInt = r.nextInt(3) + 1;
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

    public class Homer extends Thread{
        public Item myItem;
        public Homer(String name, Item item) {
            setName(name);
            myItem = item;
        }

        public void run() {
            while (cont) {
                try {
                    semHomer.acquire();
                    if (myItem != table.getItem1() && myItem != table.getItem2()) {
                        table.get(getName());
                        eatDonut();
                        semTable.release();
                    } else {
                        semHomer.release();
                        sleep(r.nextInt(5));
                    }
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }

        private void eatDonut() {
            System.out.println( getName() + " is eating the donut.");
            try {
                sleep((long) Math.floor(Math.random() * 5));
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }

    }
}
