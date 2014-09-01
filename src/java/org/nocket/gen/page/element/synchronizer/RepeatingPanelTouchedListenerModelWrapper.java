package org.nocket.gen.page.element.synchronizer;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.nocket.gen.page.element.PageElementI;

public class RepeatingPanelTouchedListenerModelWrapper<E> extends TouchedListenerModelWrapper<E> {

    public RepeatingPanelTouchedListenerModelWrapper(PageElementI<E> e, IModel<E> delegate) {
        super(e, delegate);
    }

    @Override
    protected State preserveState(Component object) {
        // TODO Auto-generated method stub
        return super.preserveState(object);
    }

}
