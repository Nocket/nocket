package forscher.dmdweb.page.gen.layout;

import java.io.Serializable;

public class AllComponentsNested implements Serializable {
    String textfield;

    public String getTextfield() {
        return textfield;
    }

    public void setTextfield(String textfield) {
        this.textfield = textfield;
    }

    public void button() {
        System.out.println("Done");
    }
}
