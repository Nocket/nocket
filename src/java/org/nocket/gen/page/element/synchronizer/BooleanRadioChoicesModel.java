package org.nocket.gen.page.element.synchronizer;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.model.AbstractReadOnlyModel;

public class BooleanRadioChoicesModel extends AbstractReadOnlyModel<List<Object>> {

    @Override
    public List<Object> getObject() {
        return (List) Arrays.asList(Boolean.TRUE, Boolean.FALSE);
    }

}
