package forscher.dmdweb.page.gen;

import java.io.Serializable;
import java.util.UUID;

public class GeneratedSeperatedForm implements Serializable {

    public String getSeperateFormField() {
        return UUID.randomUUID().toString();
    }

}
