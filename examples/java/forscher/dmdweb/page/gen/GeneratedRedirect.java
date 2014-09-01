package forscher.dmdweb.page.gen;

import java.io.Serializable;

public class GeneratedRedirect implements Serializable {

    private Object back;
    private String text;

    public GeneratedRedirect(Object back, String text) {
        this.back = back;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object goBack() {
        return back;
    }

}
