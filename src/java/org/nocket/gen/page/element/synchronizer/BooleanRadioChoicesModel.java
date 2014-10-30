package org.nocket.gen.page.element.synchronizer;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.model.AbstractReadOnlyModel;

// TODO: Auto-generated Javadoc
/**
 * The Class BooleanRadioChoicesModel.
 */
public class BooleanRadioChoicesModel extends AbstractReadOnlyModel<List<Object>> {

    /* (non-Javadoc)
     * @see org.apache.wicket.model.AbstractReadOnlyModel#getObject()
     */
    @Override
    public List<Object> getObject() {
        return (List) Arrays.asList(Boolean.TRUE, Boolean.FALSE);
    }

}
