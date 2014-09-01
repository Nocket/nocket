package dmdweb.listener;

import gengui.util.SevereGUIException;

import java.lang.reflect.Constructor;

import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.core.request.mapper.StalePageException;
import org.apache.wicket.protocol.http.PageExpiredException;
import org.apache.wicket.protocol.http.servlet.ResponseIOException;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.settings.IExceptionSettings;

import dmdweb.NocketWebApplication;
import dmdweb.page.DMDWebPage;
import dmdweb.page.error.DMDInternalErrorPage;

public class DMDLoggingRequestCycleListener extends AbstractRequestCycleListener {
    protected Class<? extends DMDWebPage> internalErrorPageType;

    public DMDLoggingRequestCycleListener() {
        this(DMDInternalErrorPage.class);
    }

    public DMDLoggingRequestCycleListener(Class<? extends DMDWebPage> internalErrorPageType) {
        this.internalErrorPageType = internalErrorPageType;
    }

    /**
     * @see org.apache.wicket.request.cycle.AbstractRequestCycleListener#onException(org.apache.wicket.request.cycle.RequestCycle,
     *      java.lang.Exception)
     */
    @Override
    public IRequestHandler onException(RequestCycle cycle, Exception ex) {
        if (!(isIgnoredException(ex))) {
            if (IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE.equals(NocketWebApplication.get().getExceptionSettings()
                    .getUnexpectedExceptionDisplay())) {
                return createPageRequestHandler(new PageProvider(createInternalErrorPage(ex)));
            }
        }
        return null;
    }

    /**
     * Those Exceptions are ignored in our Errorhandling. Due to the fact that
     * Applications using DMDWeb may invalidate their Session using their
     * ErrorPage.<br>
     * <br>
     * If you want to change the behavior for your Application, you can
     * overwrite this method.
     * 
     * @param ex
     *            The thrown Exception
     * @return true if the Exception is ignored, else false.
     */
    protected boolean isIgnoredException(Exception ex) {
        if (ex instanceof PageExpiredException) {
            return true;
        } else if (ex instanceof ResponseIOException) {
            return true;
        } else if (ex instanceof StalePageException) {
            return true;
        }
        return false;
    }

    @Override
    public void onExceptionRequestHandlerResolved(RequestCycle cycle,
            IRequestHandler handler, Exception exception) {
        super.onExceptionRequestHandlerResolved(cycle, handler, exception);
    }

    private RenderPageRequestHandler createPageRequestHandler(PageProvider pageProvider) {
        RequestCycle requestCycle = RequestCycle.get();

        if (requestCycle == null) {
            throw new IllegalStateException("there is no current request cycle attached to this thread");
        }

        /*
         * Use NEVER_REDIRECT policy to preserve the original page's URL for
         * non-Ajax requests and always redirect for ajax requests
         */
        RenderPageRequestHandler.RedirectPolicy redirect = RenderPageRequestHandler.RedirectPolicy.NEVER_REDIRECT;

        if (isProcessingAjaxRequest()) {
            redirect = RenderPageRequestHandler.RedirectPolicy.AUTO_REDIRECT;
        }

        return new RenderPageRequestHandler(pageProvider, redirect);
    }

    private boolean isProcessingAjaxRequest() {
        RequestCycle rc = RequestCycle.get();
        Request request = rc.getRequest();
        if (request instanceof WebRequest) {
            return ((WebRequest) request).isAjax();
        }
        return false;
    }

    protected DMDWebPage createInternalErrorPage(Exception ex) {
        try {
            Constructor<? extends DMDWebPage> ctor = internalErrorPageType.getConstructor(Throwable.class);
            if (ctor == null)
                ctor = internalErrorPageType.getConstructor(Exception.class);
            if (ctor != null)
                return ctor.newInstance(ex);
            else
                throw new IllegalArgumentException("No suitable constructor in page class "
                        + internalErrorPageType.getName());
        } catch (Exception x) {
            ex.printStackTrace();
            throw new SevereGUIException(x);
        }
    }
}
