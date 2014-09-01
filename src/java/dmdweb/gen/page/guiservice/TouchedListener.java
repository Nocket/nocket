package dmdweb.gen.page.guiservice;

import java.io.Serializable;

public interface TouchedListener extends Serializable {

    public abstract void touched(String wicketId);

    public abstract void untouched(String wicketId);

}
