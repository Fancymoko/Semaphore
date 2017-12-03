// @author Steven Turmel
// @version 02 Dec 2017

public class Donut {
    public static void main(String args[]) {
        int count;
        if (args.length > 0) {
            count = Integer.parseInt(args[0]);
        } else {
            count = 10;
        }
        Table table = new Table();
        new Homer(table, count, "Homer Sprinkles", Item.SPRINKLES);
        new Homer(table, count, "Homer Filling", Item.FILLING);
        new Homer(table, count, "Homer Shell", Item.SHELL);
        new Chef(table, count * 3);
    }
}
