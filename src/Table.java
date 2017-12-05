// @author Steven Turmel
// @version 1.1 05 Dec 2017

//Handles the storage and processing of the donut parts for the chef and the Homers.
public class Table {
    //The first item stored by the chef.
    private Item item1;
    //The second item stored by the chef.
    private Item item2;

    //This method handles setting the donut and is the receiving end for the data generated in the randomItem
    // method of the chef class
    public void place(Item i, Item j) {
        //Set the first item made by the chef here
        item1 = i;
        //Set the second item made by the chef here
        item2 = j;
        //Let the user know what parts the chef just put on the table.
        System.out.println("Chef placed a " + item1.toString() + " and a " + item2.toString());
    }

    //This method delivers the donuts to homer and lets the user know who is taking the pieces off the table
    //Note - there are no actual donut parts delivered here, this is all simulated.
    public void get(String name) {
        //Call the getItem method, setting both of the donuts back to null to give the chef space to place more items on the table.
        getItem();
        //Print to console the name of the homer taking the donut parts off the table
        System.out.println(name + " is making a donut.");
    }

//    private void placeItem(Item i) {
//        switch (i) {
//            case SPRINKLES:
//                item1 = Item.SPRINKLES;
//                break;
//            case FILLING:
//                break;
//            case SHELL:
//                break;
//            default:
//                System.out.println("You shouldn't get this error");
//        }
//    }

    //This method clears space on the table so the chef can put more donut parts down
    private void getItem() {
        //The spot for donut part 1 is now empty.
        item1 = null;
        //The spot for donut part 2 is now empty.
        item2 = null;
    }

    //This method returns which kind of part is stored in the space for donut part 1.
    public Item getItem1() {
        return item1;
    }

    //This method returns which kind of part is stored in the space for donut part 2.
    public Item getItem2() {
        return item2;
    }
}
