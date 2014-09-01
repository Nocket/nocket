package forscher.nocket.page.gen.ajax;

import gengui.annotations.Eager;

import java.io.Serializable;

public class AjaxTargetUpdateTestInner implements Serializable {

    private String feld1;
    private String feld2;

    public String getEagerFeld1() {
        return feld1;
    }

    @Eager
    public void setEagerFeld1(String feld1) {
        this.feld1 = feld1;
    }

    public String getFeld2() {
        return feld2;
    }

    public void setFeld2(String feld2) {
        this.feld2 = feld2;
    }

}
