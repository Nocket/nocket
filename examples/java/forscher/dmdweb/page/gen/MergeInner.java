package forscher.dmdweb.page.gen;

import java.io.Serializable;

public class MergeInner implements Serializable {
    private String feld1;
    private String feld2;
    private String feld3;

    public MergeInnerInner inner;

    public MergeInner() {
        inner = new MergeInnerInner();
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

    public MergeInnerInner getInnerInner() {
        return inner;
    }
}
