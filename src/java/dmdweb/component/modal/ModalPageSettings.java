package dmdweb.component.modal;

import java.io.Serializable;

public class ModalPageSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private boolean resizable = true;
    private Integer minimalWidth = 500;
    private Integer minimalHeight = 100;
    private Integer initialWidth = 500;
    private Integer initialHeight = 100;

    public ModalPageSettings(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public Integer getMinimalWidth() {
        return minimalWidth;
    }

    public void setMinimalWidth(Integer minimalWidth) {
        this.minimalWidth = minimalWidth;
    }

    public Integer getMinimalHeight() {
        return minimalHeight;
    }

    public void setMinimalHeight(Integer minimalHeight) {
        this.minimalHeight = minimalHeight;
    }

    public Integer getInitialWidth() {
        return initialWidth;
    }

    public void setInitialWidth(Integer initialWidth) {
        this.initialWidth = initialWidth;
    }

    public Integer getInitialHeight() {
        return initialHeight;
    }

    public void setInitialHeight(Integer initialHeight) {
        this.initialHeight = initialHeight;
    }

}
