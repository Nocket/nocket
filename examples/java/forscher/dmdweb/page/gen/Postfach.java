package forscher.dmdweb.page.gen;

import gengui.annotations.FieldOrder;

import java.io.Serializable;

@FieldOrder("Nummer, PLZ")
public class Postfach implements Serializable {
    private Integer nummer;
    private String PLZ;

    public Integer getNummer() {
        return nummer;
    }

    public void setNummer(Integer nummer) {
        this.nummer = nummer;
    }

    public String getPLZ() {
        return PLZ;
    }

    public void setPLZ(String plz) {
        PLZ = plz;
    }

    public Postfach() {
    }

    public Postfach(Integer nummer) {
        this.nummer = nummer;
    }

    public String toString() {
        return String.valueOf(nummer);
    }

    @Override
    public boolean equals(Object rhs) {
        if (!(rhs instanceof Postfach))
            return false;
        if (nummer == null) {
            if (((Postfach) rhs).nummer == null)
                return true;
        } else
            return (nummer.equals(((Postfach) rhs).nummer));
        return false;
    }

}
