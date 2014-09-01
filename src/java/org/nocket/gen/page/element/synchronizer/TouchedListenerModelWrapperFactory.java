package org.nocket.gen.page.element.synchronizer;

import org.apache.wicket.model.IModel;
import org.nocket.gen.page.element.PageElementI;

public class TouchedListenerModelWrapperFactory {

    public static <M> TouchedListenerModelWrapper<M> create(PageElementI<M> e, IModel<M> delegate) {
        return new TouchedListenerModelWrapper<M>(e, delegate);
    }
}
