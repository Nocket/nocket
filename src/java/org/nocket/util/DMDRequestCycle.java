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
 * 
 */
public class DMDRequestCycle extends RequestCycle {
    public final static String SESSION_ID = "SESSION_ID";

    public DMDRequestCycle(RequestCycleContext context) {
        super(context);
    }

    @Override
    protected void onBeginRequest() {
        String id = Session.get().getId();
        if (id != null) {
            MDC.put(SESSION_ID, id);
        }
        super.onBeginRequest();
    }

    @Override
    protected void onEndRequest() {
        super.onEndRequest();
        MDC.remove(SESSION_ID);
    }

    /**
     * Convenience method, that returns the AjaxRequestTarget
     * 
     * @return
     */
    public static AjaxRequestTarget getAjaxRequestTarget() {
        return get().find(AjaxRequestTarget.class);
    }

}
