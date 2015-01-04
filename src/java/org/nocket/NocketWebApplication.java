package org.nocket;

import gengui.WindowOperation;
import gengui.guiadapter.table.TableModelFactory;

import org.apache.wicket.DefaultMapperContext;
import org.apache.wicket.IRequestCycleProvider;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.Session;
import org.apache.wicket.core.request.mapper.IMapperContext;
import org.apache.wicket.core.util.resource.locator.ResourceStreamLocator;
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
import org.nocket.component.CSSResourceInjector;
import org.nocket.component.JavaScriptResourceInjector;
import org.nocket.component.header.jquery.JQueryHelper;
import org.nocket.gen.WebGUISession;
import org.nocket.gen.domain.WebDomainProperties;
import org.nocket.gen.resources.DMDCachingResourceStreamLocator;
import org.nocket.listener.DMDLoggingRequestCycleListener;
import org.nocket.page.InMemoryClassResolver;
import org.nocket.util.DMDRequestCycle;

/**
 * Specialized base class for web applications which make use of org.nocket's
 * generic UIs. There are a few tricky features available which are switched on
 * by init... template methods. Projects may override these functions to change
 * the default configuration process provided in here.
 * 
 * @author less02
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
		getJavaScriptLibrarySettings().setJQueryReference(new PackageResourceReference(JQueryHelper.class, JQueryHelper.getCurrentVersion()));
		getComponentInstantiationListeners().add(new CSSResourceInjector());
		getComponentInstantiationListeners().add(new JavaScriptResourceInjector());

		initGenguiWebConfiguration();
		initGenguiI18N();
		initInMemoryCompilation();
		initOnTheFlyHTMLCreation();
		initGenguiClassbasedCaching();
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
					// try to lock failing fast:
					// http://mail-archives.apache.org/mod_mbox/wicket-users/201211.mbox/%3CCANgwjP4xsMKo6kKjVSOOnf_qKvdV+nbhXh8bkZ0R6oZN1BS8YA@mail.gmail.com%3E
					PageAccessSynchronizer synchronizer = new PageAccessSynchronizer(Duration.ONE_SECOND);
					synchronizer.lockPage(pageId);
					try {
						return super.getPageInstance(pageId);
					} finally {
						synchronizer.unlockPage(pageId);
					}
				} catch (CouldNotLockPageException e) {
					// if lock not possible, ignore this page and worst case
					// create a new one
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
		return null;
	}

	/**
	 * Some initializations are only performed in development mode which means
	 * depending the the return value of this function. It returns true if
	 * Wicket's configuration type is set to DEVELOPMENT. Overwrite this
	 * function if you want to make the availability of certain dynamic features
	 * to depend on something else.
	 */
	protected boolean isDevelopmentMode() {
		return (getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT);
	}

	/**
	 * Switch off any class-dependent caching in gengui if the application runs
	 * in development mode. This allows to modify class structures at runtime
	 * and let the application react on these changes without rebooting the
	 * server.
	 */
	protected void initGenguiClassbasedCaching() {
		if (isDevelopmentMode()) {
			TableModelFactory.caching(false);
		}
	}

	/**
	 * Enable on-the-fly HTML creation by registering a resource manager in
	 * Wicket's resource settings which allows to create resources at runtime
	 * and registering them subsequently. By default, Wicket's resource
	 * management is aware of non-existent resources and does not expect them to
	 * appear later.
	 * <p>
	 * As this feature is usually only of interest during development, the
	 * resource manager is only provided in development mode.
	 */
	protected void initOnTheFlyHTMLCreation() {
		if (isDevelopmentMode()) {
			ResourceStreamLocator locator = new ResourceStreamLocator(getResourceSettings().getResourceFinders());
			DMDCachingResourceStreamLocator cachedLocator = new DMDCachingResourceStreamLocator(locator);
			getResourceSettings().setResourceStreamLocator(cachedLocator);
		}
	}

	/**
	 * Enable in-memory creation of page and panel classes by registering an
	 * extended class resolver in Wickets application settings. As this is
	 * usually only of interest during development, the resolver is only
	 * registered in development mode.
	 */
	protected void initInMemoryCompilation() {
		if (isDevelopmentMode())
			getApplicationSettings().setClassResolver(new InMemoryClassResolver());
	}

	/**
	 * Allow access to gengui's internationalization API by registering a
	 * have-alive GUI session, connected to the Wicket session. See class
	 * {@link WebGUISession} for more details.
	 */
	protected void initGenguiI18N() {
		WindowOperation.connect(null, new WebGUISession());
	}

	/**
	 * Enable some web-specific property keys and values in gengui's
	 * configuration
	 */
	protected void initGenguiWebConfiguration() {
		new WebDomainProperties().init();
	}

}
