// @author Steven Turmel
// @version 1.1 05 Dec 2017

//import the relevant random class to randomly generate a number
import java.util.Random;
//import the semaphore class so I can create semaphores and direct the flow of "traffic" from the threads to the table.
import java.util.concurrent.Semaphore;

public class Donut extends Thread{
    //Serves as a storage container for the donut parts between the chef and the 3 Homers
    static Table table = new Table();
    //Count tracks the number of cycles the chef must complete before he has finished cooking
    static int count;
    //I had to make i global so the Homers could later check to determine if the chef had finished
    static int i;
    //This is the semaphore declared to let the homers know that there are donut parts on the table
    static Semaphore semHomer = new Semaphore(0);
    // This is the semaphore to let the chef know that one of the Homers has emptied the table.
    //Initialized to 1 because the chef needs to go before the Homer does
    static Semaphore semTable = new Semaphore(1);
    //Random declared so the Math.random function does not need to be called every time the chef places a donut.
    static Random r;
    //boolean to let the Homers know that the chef has finished cooking.
    static boolean cont = true;

    //This is the main where the program enters and begins running when started by the JVM
    public static void main(String args[]) {
        try {
            //Is there an argument and is it an integer?
            if (args.length > 0 && Integer.parseInt(args[0]) >= 0) {
                //If so, set count equal to the argument.
                count = Integer.parseInt(args[0]);
            } else {
                //if not, set the count = 10
                count = 10;
            }
            //Go here if the argument provided is invalid.
        } catch (NumberFormatException e) {
            System.err.println(e);
            //set it equal to 10 anyway.
            count = 10;
        }
        //Declare a donut to handle the initialization of all the threads
        Donut donut = new Donut();
        //run the donut thread to start all of the other threads
        donut.start();
    }

    //This method handles declaring and starting all of the other threads.
    public void run() {
        //Declare and initialize the "Sprinkes" Homer
        Homer homer1 = new Homer("Homer Sprinkles", Item.SPRINKLES);
        //Declare and initialize the "Filling" Homer
        Homer homer2 = new Homer("Homer Filling", Item.FILLING);
        //Declare and initialize the "Shell" Homer
        Homer homer3 = new Homer("Homer Shell", Item.SHELL);
        //Declare and initialize the chef
        Chef chef = new Chef();
        //Start all of the threads
        chef.start();
        homer1.start();
        homer2.start();
        homer3.start();
        //This is here in case the chef runs 0 times, to let you know the program is running correctly.
        //Output "All threads started."
        System.out.println("All threads started.");
    }

    //The chef subclass handles randomly selecting donut part combinations to place on the table.
    public class Chef extends Thread {

        //When chef is initialized, it also initializes the r Random.
        public Chef() {
            r = new Random();
        }
        //The chef thread runs in much the same way that the Homer threads run,
        //continuously trying to acquire the semaphore to give them access to the table.
        public void run() {
            //This chef will make "count" number of donuts, and the i represents how many donuts have been made so far
            for(i = 0; i < count; i++) {
                try {
                    //Attempt to acquire the semaphore
                    semTable.acquire();
                    //Once the semaphore is acquired, print out what donut you are on and how many you will be making.
                    System.out.println("Making donut number " + (i + 1) + " of " + count);
                    //create a temporary item to store 2 randomly selected items
                    Item tempItem[] = randomItem();
                    //Place the two items on the table, where they are stored locally for the Homers.
                    table.place(tempItem[0], tempItem[1]);
                    //Release the Homer semaphore to let the Homers know there are donut pieces on the table.
                    semHomer.release();
                    //If there is an interrupt exception, handle it here
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }

        //This function is used to randomly select one of three pre-defined combinations of items
        public Item[] randomItem() {
            //create a temporary integer to select which of the three cases will be returned
            int tempInt = r.nextInt(3) + 1;
            //declare an Item array to store the combination which will be returned.
            Item[] item;
            //This switch statement interprets which of the three cases the RNG has selected and will return the selected result.
            switch (tempInt) {
                //If the RNG selected 1, the chef will place sprinkles and filling.
                case 1:
                    item = new Item[] {Item.SPRINKLES, Item.FILLING};
                    return item;
                    //If the RNG selected 2, the chef will place filling and a shell.
                case 2:
                    item = new Item[] {Item.FILLING, Item.SHELL};
                    return item;
                    //If the RNG selected 3, the chef will place a shell and sprinkles.
                case 3:
                    item = new Item[] {Item.SHELL, Item.SPRINKLES};
                    return item;
                    //If for some reason none of these were the answer, the program will let you know and the chef will
                //put out sprinkles and filling by default so the program will continue to run.
                default:
                    System.out.println("You made an error somewhere in your calculations.");
                    item = new Item[] {Item.SPRINKLES, Item.FILLING};
                    return item;
            }
        }
    }

    //This is the Homer class which represents Homer, trying to eat as many complete donuts as he can.
    public class Homer extends Thread{
        //myItem represents the one item which eat Homer has in infinite supply. The item he will NOT be looking for.
        public Item myItem;
        //When a Homer is initialized, it requires a string for his name and an item which he will have in infinite supply.
        //These are declared in the donut run() method.
        public Homer(String name, Item item) {
            setName(name);
            myItem = item;
        }

        //For the Homer run method, Homer will be trying to acquire the homer semaphore, letting him know that there are donut
        //parts on the table waiting to be assembled.
        public void run() {
            //While the chef is not done...
             while (cont) {
                 //try to acquire the access to the table.
                try {
                    semHomer.acquire();
                    //If the item I have is the one needed to make a complete donut and the table is not empty,
                    if (myItem != table.getItem1() && myItem != table.getItem2() && table.getItem1() != null) {
                        //get the donuts off the table and let the table know who is taking the donuts.
                        table.get(getName());
                        //Mmm... donuts.
                        eatDonut();
                        //Release the chef semaphore to let the chef know the table is empty and he needs to refill it.
                        semTable.release();
                    } else {
                        //If the item I have is not the correct one to make a full donut, let the other Homers know
                        //they can have a turn at the table.
                        semHomer.release();
                        //Sleep, to give them a chance to make their donuts before checking back.
                        sleep(r.nextInt(5));
                    }
                    //If someone interrupts me getting to the table, handle it here and output the error.
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
                //Check to see if the chef is done and if he is, give to other homers a chance to see that the table is empty.
                 if (i >= count) {
                    cont = false;
                    semHomer.release();
                    break;
                 }
            }
        }

        //This method handles eating a donut.
        private void eatDonut() {
            //Let the user know which thread is eating their donut.
            System.out.println( getName() + " is eating the donut.");
            try {
                //this is a randomly generated amount of time needed to make and eat the donut.
                sleep(r.nextInt(5));
                //if I can't make the donut for some reason, let the user know here.
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }

    }
}
