// @author Steven Turmel
// @version 02 Dec 2017


public class Table {


    private Item item1;
    private Item item2;

    public void place(Item i, Item j) {
        item1 = i;
        item2 = j;
        System.out.println("Chef placed a " + item1.toString() + " and a " + item2.toString());
    }

    public void get(String name) {
        getItem();
        System.out.println(name + " ate a donut.");
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

    public void getItem() {
        item1 = null;
        item2 = null;
    }

    public Item getItem1() {
        return item1;
    }

    public Item getItem2() {
        return item2;
    }
}
