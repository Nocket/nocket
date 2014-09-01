package forscher.dmdweb.page.gen;

import java.io.Serializable;

/**
 * This class is special in the sense that there is no corresponding panel class
 * around. It will be constructed in-memory
 * 
 * @author less02
 */
public class Panelless implements Serializable {
    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void abbrechen() {
    }
}
