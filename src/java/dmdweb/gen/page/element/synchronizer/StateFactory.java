package dmdweb.gen.page.element.synchronizer;

import org.apache.wicket.Component;

public class StateFactory {

    public static <E> State<E> create(TouchedListenerModelWrapper<E> wrapper, Component component,
            SynchronizerHelper helper) {
        return new StateStandard<E>(wrapper, component, helper);
    }
}
