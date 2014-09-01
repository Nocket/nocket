package forscher.dmdweb.page.gen;

import java.io.Serializable;

/**
 * This class is special in the sense that there is no corresponding Page class
 * around. It will be constructed in-memory
 * 
 * @author less02
 */
public class Pageless implements Serializable {
    private String attribute;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Generated gotoGenerated() {
        return new Generated();
    }
}
