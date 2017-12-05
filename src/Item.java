// @author Steven Turmel
// @version 1.1 05 Dec 2017

//This enumeration is used to prevent a lot of confusing integers from being used because it is a lot clearer to read
// than "Chef placed a 2 on the table"
public enum Item {
    //These are the Item types that are allowed.
    SPRINKLES, FILLING , SHELL;

    @Override
    //Return the corresponding string for each of the donut parts
    //This method just converts an Item into a String to be used in the console.
    public String toString() {
        switch(this) {
            //If this is SPRINKLES, return "Sprinkles".
            case SPRINKLES: return "sprinkles";
            //If this is a FILLING, return "Filling".
            case FILLING: return "filling";
            //If this is a SHELL, return "Shell".
            case SHELL: return "shell";
            //If this is none of these, let the user know they gave us a donut part that doesn't exist.
            default: throw new IllegalArgumentException();
        }
    }
}
