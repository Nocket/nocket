package org.nocket;

import org.apache.wicket.DefaultMapperContext;
import org.apache.wicket.IRequestCycleProvider;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.core.request.mapper.IMapperContext;
import org.apache.wicket.markup.MarkupFactory;
import org.apache.wicket.markup.MarkupParser;
import org.apache.wicket.markup.MarkupResourceStream;
import org.apache.wicket.markup.parser.filter.WicketTagIdentifier;
import org.apache.wicket.page.CouldNotLockPageException;
import org.apache.wicket.page.PageAccessSynchronizer;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.cycle.RequestCycleContext;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.time.Duration;
import org.nocket.component.header.jquery.JQueryHelper;
import org.nocket.listener.DMDLoggingRequestCycleListener;
import org.nocket.util.DMDRequestCycle;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see com.mycompany.Start#main(String[])
 */
abstract public class NocketWebApplication extends WebApplication {

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
	super.init();

	getMarkupSettings().setMarkupFactory(getMarkupFactory());
	getPageSettings().addComponentResolver(new LabelI18NMarkupFilter());
	getJavaScriptLibrarySettings().setJQueryReference(
		new PackageResourceReference(JQueryHelper.class, JQueryHelper.getCurrentVersion()));
    }

    protected MarkupFactory getMarkupFactory() {
	return new MarkupFactory() {

	    @Override
	    public MarkupParser newMarkupParser(MarkupResourceStream resource) {
		MarkupParser parser = super.newMarkupParser(resource);
		parser.add(new LabelI18NMarkupFilter());
		parser.add(new DMDRadioChoiceMarkupFilter(), WicketTagIdentifier.class);
		return parser;
	    }
	};
    }

    @Override
    protected IMapperContext newMapperContext() {
	return new DefaultMapperContext() {
	    @Override
	    public IRequestablePage getPageInstance(int pageId) {
		try {
		    //try to lock failing fast: http://mail-archives.apache.org/mod_mbox/wicket-users/201211.mbox/%3CCANgwjP4xsMKo6kKjVSOOnf_qKvdV+nbhXh8bkZ0R6oZN1BS8YA@mail.gmail.com%3E
		    PageAccessSynchronizer synchronizer = new PageAccessSynchronizer(Duration.ONE_SECOND);
		    synchronizer.lockPage(pageId);
		    try {
			return super.getPageInstance(pageId);
		    } finally {
			synchronizer.unlockPage(pageId);
		    }
		} catch (CouldNotLockPageException e) {
		    //if lock not possible, ignore this page and worst case create a new one
		    return null;
		}
	    }
	};
    }

    protected IRequestCycleProvider newRequestCycleProvider() {
	return new IRequestCycleProvider() {
	    @Override
	    public RequestCycle get(RequestCycleContext context) {
		return new DMDRequestCycle(context);
	    }
	};
    }

    protected void addDMDLoggingRequestCycleListener() {
	getRequestCycleListeners().add(new DMDLoggingRequestCycleListener());
    }

    /**
     * @see org.apache.wicket.Application#newSession(org.apache.wicket.request.Request,
     *      org.apache.wicket.request.Response)
     */
    @Override
    public Session newSession(Request request, Response response) {
	return new NocketSession(request);
    }

    /**
     * Application subclasses must specify a login page class by implementing
     * this abstract method. If application has no login page return null.
     * 
     * @return Login page class for this application
     */
    abstract public Class<? extends Page> getLoginPage();

    @Override
    public Class<? extends Page> getHomePage() {
	// TODO Auto-generated method stub
	return null;
    }
}
