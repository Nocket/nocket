package forscher.nocket.page.gen.i18n;

import java.io.File;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class GenguiLocalizedTableLine implements Serializable {

    private String column1 = "String";
    private TimeUnit column2 = TimeUnit.DAYS;
    private File column3 = null;

    // With "@Prompt" annotation you can define different key for the property localization
    // If annotation is missing, standard key is composed in following way : 
    // package.ClassName._property.text
    // @Prompt("Column1.prompt")
    public String getColumn1() {
        return column1;
    }

    public TimeUnit getColumn2() {
        return column2;
    }

    public File getColumn3() {
        return column3;
    }

    public void method() {
        System.out.println(this.getClass().getCanonicalName() + "#method() clicked");
    }

}
