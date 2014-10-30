package org.nocket.gen.page.element.synchronizer;

import org.apache.wicket.model.IModel;
import org.nocket.gen.page.element.PageElementI;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating TouchedListenerModelWrapper objects.
 */
public class TouchedListenerModelWrapperFactory {

    /**
     * Creates the.
     *
     * @param <M> the generic type
     * @param e the e
     * @param delegate the delegate
     * @return the touched listener model wrapper
     */
    public static <M> TouchedListenerModelWrapper<M> create(PageElementI<M> e, IModel<M> delegate) {
        return new TouchedListenerModelWrapper<M>(e, delegate);
    }
}
