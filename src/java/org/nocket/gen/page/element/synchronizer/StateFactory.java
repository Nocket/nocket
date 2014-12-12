package org.nocket.gen.page.element.synchronizer;

import org.apache.wicket.Component;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating State objects.
 */
public class StateFactory {

    /**
     * Creates the.
     *
     * @param <E> the element type
     * @param wrapper the wrapper
     * @param component the component
     * @param helper the helper
     * @return the state
     */
    public static <E> State<E> create(TouchedListenerModelWrapper<E> wrapper, Component component,
            SynchronizerHelper helper) {
        return new StateStandard<E>(wrapper, component, helper);
    }
}
