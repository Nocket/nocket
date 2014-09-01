package org.nocket.gen.page.element.synchronizer;

import org.apache.commons.lang.BooleanUtils;
import org.apache.wicket.model.PropertyModel;

public class PrimitiveBooleanPropertyModel extends PropertyModel<Object> {

    public PrimitiveBooleanPropertyModel(Object modelObject, String expression) {
        super(modelObject, expression);
    }

    @Override
    public void setObject(Object object) {
        super.setObject(BooleanUtils.isTrue((Boolean) object));
    }

}
