package org.nocket.page;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.nocket.util.SevereWebException;

/**
 * Factory class for creating a web pages instances for specified views. Web
 * page must extend DMDWebPage and be in sub-package ".page.". Class name of it
 * must be the same as class name of view + "Page" suffix.
 * <p>
 * For example view: my.package.login.LoginView expects its web page in class
 * my.package.login.page.LoginViewPage.
 * <p>
 * Normally you don't have to use this class directly. Rather use
 * {@link DMDWebPage#setResponsePageForView(Object)} or
 * {@link DMDWebPage#setRedirectPageForView(Object, PageParameters)} methods
 * instead.
 * 
 * @author blaz02
 * 
 */
public class DMDPageFactory {

    /**
     * @param view
     *            Instance of the view. Cannot be null.
     * 
     * @return Instance of the page for the view.
     * 
     * @throws SevereWebException
     *             If page instance cannot be created.
     */
    public static DMDWebPage getViewPageInstance(Object view) {
        return new FactoryHelper(view).getPageInstance();
    }

    /**
     * @param view
     *            Instance of the view. Cannot be null.
     * 
     * @return Class name for the page for the view.
     * 
     * @throws SevereWebException
     *             If page instance cannot be created.
     */
    public static Class<DMDWebPage> getViewPageClass(Object view) {
        return new FactoryHelper(view).getPageClass();
    }

    /**
     * If NocketSession exists the factory will store the PageReference of the
     * page with the hashCode of the View in the Session.PageHistory Map.
     * 
     * @param page
     *            - The Page which PageReference will be stored
     * @param view
     *            - The view to (maybe) come back - Nothing will be stored if
     *            View is null
     */
    public static void storePageForView(DMDWebPage page, Object view) {
        if (view != null) {
            new FactoryHelper(view).storePageForView(page);
        }
    }
}
