package org.nocket.gen.page.element.synchronizer;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.nocket.gen.page.element.PageElementI;

// TODO: Auto-generated Javadoc
/**
 * The Class RepeatingPanelTouchedListenerModelWrapper.
 *
 * @param <E> the element type
 */
public class RepeatingPanelTouchedListenerModelWrapper<E> extends TouchedListenerModelWrapper<E> {

    /**
     * Instantiates a new repeating panel touched listener model wrapper.
     *
     * @param e the e
     * @param delegate the delegate
     */
    public RepeatingPanelTouchedListenerModelWrapper(PageElementI<E> e, IModel<E> delegate) {
        super(e, delegate);
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.page.element.synchronizer.TouchedListenerModelWrapper#preserveState(org.apache.wicket.Component)
     */
    @Override
    protected State preserveState(Component object) {
        // TODO Auto-generated method stub
        return super.preserveState(object);
    }

}
