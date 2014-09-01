package forscher.nocket.page.gen;

import java.io.Serializable;

/**
 * This is a structured class to test if choicers allow to choose from
 * structured types too.
 * 
 * @author less02
 * 
 */
public class GeneratedChoice implements Serializable {
    private String name;
    private String firstName;
    private String lastName;

    public GeneratedChoice(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = firstName + " " + lastName;
    }

    public GeneratedChoice() {
    }

    public String toString() {
        return name;
    }

}
