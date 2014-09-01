package org.nocket.gen;

import gengui.WindowOperation;
import gengui.guiadapter.table.TableModelFactory;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.core.util.resource.locator.ResourceStreamLocator;
import org.nocket.NocketWebApplication;
import org.nocket.gen.domain.WebDomainProperties;
import org.nocket.gen.resources.DMDCachingResourceStreamLocator;
import org.nocket.page.InMemoryClassResolver;

/**
 * Specialized base class for web applications which make use of org.nocket's
 * generic UIs. There are a few tricky features available which are switched on
 * by init... template methods. Projects may override these functions to change
 * the default configuration process provided in here.
 * 
 * @author less02
 */

abstract public class DMDGenericWebApplication extends NocketWebApplication {
    // TODO meis026 Warum gibt es die NocketWebApplication und die DMDGenericWebApplication? Solltes es nicht nur die DMDGenericWebApplication geben?
    @Override
    public void init() {
	super.init();
	initGenguiWebConfiguration();
	initGenguiI18N();
	initInMemoryCompilation();
	initOnTheFlyHTMLCreation();
	initGenguiClassbasedCaching();
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
