package org.nocket.gen.page.element.synchronizer;

import org.apache.commons.lang.BooleanUtils;
import org.apache.wicket.model.PropertyModel;

// TODO: Auto-generated Javadoc
/**
 * The Class PrimitiveBooleanPropertyModel.
 */
public class PrimitiveBooleanPropertyModel extends PropertyModel<Object> {

    /**
     * Instantiates a new primitive boolean property model.
     *
     * @param modelObject the model object
     * @param expression the expression
     */
    public PrimitiveBooleanPropertyModel(Object modelObject, String expression) {
        super(modelObject, expression);
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.model.AbstractPropertyModel#setObject(java.lang.Object)
     */
    @Override
    public void setObject(Object object) {
        super.setObject(BooleanUtils.isTrue((Boolean) object));
    }

}
