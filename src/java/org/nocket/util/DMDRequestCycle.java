package org.nocket.util;

import org.apache.log4j.MDC;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.cycle.RequestCycleContext;

/**
 * Die derzeit einzige Funktion des DMDRequestCycle ist die SessionId dem Log4J
 * per MCD zur Verfügung zu stellen.
 * 
 * @author meis026
 */
public class DMDRequestCycle extends RequestCycle {
    
    /** The Constant SESSION_ID. */
    public final static String SESSION_ID = "SESSION_ID";

    /**
     * Instantiates a new DMD request cycle.
     *
     * @param context the context
     */
    public DMDRequestCycle(RequestCycleContext context) {
        super(context);
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.request.cycle.RequestCycle#onBeginRequest()
     */
    @Override
    protected void onBeginRequest() {
        String id = Session.get().getId();
        if (id != null) {
            MDC.put(SESSION_ID, id);
        }
        super.onBeginRequest();
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.request.cycle.RequestCycle#onEndRequest()
     */
    @Override
    protected void onEndRequest() {
        super.onEndRequest();
        MDC.remove(SESSION_ID);
    }

    /**
     * Convenience method, that returns the AjaxRequestTarget.
     *
     * @return the ajax request target
     */
    public static AjaxRequestTarget getAjaxRequestTarget() {
        return get().find(AjaxRequestTarget.class);
    }

}
