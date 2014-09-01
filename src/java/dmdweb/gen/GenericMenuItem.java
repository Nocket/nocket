package dmdweb.gen;

import java.io.Serializable;

import dmdweb.component.menu.MenuItem;
import dmdweb.page.FactoryHelper;

/**
 * Specialized menu item class which allows to register <i>domain classes</i>
 * rather than <i>page classes</i> in the application's main menu. If in-memory
 * compilation and on-the-fly HTML creation is configured, neither the page
 * class nor the corresponding HTML must exist in advance. See
 * {@link DMDGenericWebApplication} how to enable these features.
 * 
 * @author less02
 */
public class GenericMenuItem extends MenuItem {

    public GenericMenuItem(String label, Class<? extends Serializable> domainClass) {
        super(label, FactoryHelper.getPageClass(domainClass));
    }

}
