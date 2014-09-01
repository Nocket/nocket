package forscher.dmdweb.page.gen.i18n;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import dmdweb.gen.page.guiservice.WebGuiServiceAdapter;
import gengui.annotations.Eager;
import gengui.annotations.Validate;

/**
 * This class is used to check if the gengui-based localization works properly
 * in dmdweb
 * 
 * @author less02
 */
public class GenguiLocalized implements Serializable {

    String myString;

    TimeUnit unit;

    Auto auto;

    private boolean radioChoice = true;

    GenguiLocalizedTableLine[] table = new GenguiLocalizedTableLine[] {
                new GenguiLocalizedTableLine()
        };

    public GenguiLocalized() {
        new WebGuiServiceAdapter()
                .infoMessage(
                        "Welcome!",
                        "<html>Hello World onPageLoad!<p><i>This is also an example for HTML-formatted message text!</i></html>");
    }

    // With "@Prompt" annotation you can define different key for the property localization
    // If annotation is missing, standard key is composed in following way : 
    // package.ClassName._property.text
    // @Prompt("example.prompt")
    public String getMyString() {
        return myString;
    }

    public void setMyString(String myString) {
        this.myString = myString;
    }

    @Validate
    public TimeUnit getUnit() {
        return unit;
    }

    @Eager
    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public String validateUnit(TimeUnit newValue) {
        if (newValue == TimeUnit.NANOSECONDS) {
            return "NANOSECONDS not allowed!";
        } else {
            return null;
        }
    }

    public GenguiLocalizedTableLine[] getTable() {
        return table;
    }

    public String disableMethod() {
        return (unit == null) ? "unit.needed" : null;
    }

    public void method() throws Exception {
        throw new Exception("bad.error");
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public boolean getRadioChoice() {
        return radioChoice;
    }

    @Eager
    @Validate
    public void setRadioChoice(boolean radioChoice) {
        this.radioChoice = radioChoice;
    }

    public String validateRadioChoice(boolean newValue) {
        //        if (newValue == false) {
        //            return "false not allowed!";
        //        } else {
        return null;
        //        }
    }

}
