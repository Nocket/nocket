package org.nocket.gen.page.element.synchronizer;

import org.apache.wicket.model.LoadableDetachableModel;
import org.nocket.gen.page.DMDWebGenPageContext;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadableDetachablePropertyModelObject.
 */
public class LoadableDetachablePropertyModelObject extends LoadableDetachableModel<Object> {

    /** The ctx. */
    private DMDWebGenPageContext ctx;

    /**
     * Instantiates a new loadable detachable property model object.
     *
     * @param ctx the ctx
     */
    public LoadableDetachablePropertyModelObject(DMDWebGenPageContext ctx) {
        this.ctx = ctx;
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.model.LoadableDetachableModel#load()
     */
    @Override
    protected Object load() {
        return ctx.getRefFactory().getRootReference().getRef().getRoot();
    }

}
