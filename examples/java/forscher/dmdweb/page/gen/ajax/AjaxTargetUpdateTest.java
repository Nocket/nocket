package forscher.dmdweb.page.gen.ajax;

import gengui.annotations.Eager;
import gengui.annotations.Group;
import gengui.annotations.Prompt;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Max;

import org.apache.commons.lang.StringUtils;

public class AjaxTargetUpdateTest implements Serializable {

    private AjaxTargetUpdateTestInner inner = new AjaxTargetUpdateTestInner();
    private String firstName;
    private String secondName;
    private TimeUnit time;
    private String value;
    private boolean toggleEnableStateOfValue;

    public void anAjaxButton() {
        // Nur um einen Ajax request auszuführen
    }

    public AjaxTargetUpdateTest anAjaxButtonWithRedirectToThisPage() {
        return this;
    }

    public AjaxTargetUpdateTestInner getInner() {
        return inner;
    }

    public void setInner(AjaxTargetUpdateTestInner inner) {
        this.inner = inner;
    }

    @Group("FirstTab")
    @Max(value=2)
    public String getFirstName() {
        return firstName;
    }

    @Eager
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Group("SecondTab")
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Eager
    public TimeUnit getTime() {
        return time;
    }

    public void setTime(TimeUnit time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    @Eager
    public void setValue(String value) {
        this.value = value;
    }

    public String disableValue() {
        return toggleEnableStateOfValue ? "Field disabled" : null;
    }

    public boolean getToggleEnableStateOfValue() {
        return toggleEnableStateOfValue;
    }

    @Eager
    public void setToggleEnableStateOfValue(boolean toggleEnableStateOfValue) {
        System.err.println("Setzte toggleEnableStateOfValue = " + toggleEnableStateOfValue);
        this.toggleEnableStateOfValue = toggleEnableStateOfValue;
    }

    @Prompt("Spiegelt Value")
    public String getValueMirror() {
        String disabledString = disableValue() != null ? " -> " + disableValue() : "";
        return StringUtils.trimToEmpty(getValue()) + disabledString;
    }

    @Prompt("Spiegelt firstName")
    public String getFirstNameMirror() {
        return getFirstName();
    }

    @Prompt("Spiegelt EagerFeld1")
    public String getFeld1Mirror() {
        return getInner().getEagerFeld1();
    }
}
