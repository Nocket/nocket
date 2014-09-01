package forscher.dmdweb.page.gen;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GeneratedRepeatingViewPojo implements Serializable {
    private String feld1;
    private String feld2;
    private String feld3;

    public GeneratedRepeatingViewPojo() {

    }

    public GeneratedRepeatingViewPojo(String feld1) {
        this.feld1 = feld1;
    }

    public String getFeld1() {
        return feld1;
    }

    public String getFeld2() {
        return feld2;
    }

    public String getFeld3() {
        return feld3;
    }

    public void setFeld1(String feld1) {
        this.feld1 = feld1;
    }

    public void setFeld2(String feld2) {
        this.feld2 = feld2;
    }

    public void setFeld3(String feld3) {
        this.feld3 = feld3;
    }

    public void testButton() {
        System.err.println("Der TestButton wurde geklickt");
    }
}
