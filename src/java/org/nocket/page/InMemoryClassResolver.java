package org.nocket.page;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.application.DefaultClassResolver;
import org.apache.wicket.application.IClassResolver;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.collections.UrlExternalFormComparator;
import org.nocket.gen.domain.WebDomainProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class resolver is a copy-paste-construct from Wicket's
 * AbstractClassResolver and DefaultClassResolver concerning the default class
 * resolvement. As usual, Wicket defines a lot of stuff final and private, so
 * that we can't simply derive from DefaultClassResolver.<br>
 * The important extension is the possibility to trigger the in-memory page and
 * panel class creation and register these classes here so that they can always
 * be found from within Wicket's core.
 * 
 * @author less02
 */
public class InMemoryClassResolver implements IClassResolver {

    private static final Logger log = LoggerFactory.getLogger(InMemoryClassResolver.class);

    protected final ConcurrentMap<String, WeakReference<Class<?>>> classes = new ConcurrentHashMap<String, WeakReference<Class<?>>>();

    /**
     * The in-memory classes are hard-referenced because they can't be recreated
     * from here on the fly However, this map is only used for canonic page
     * classes which are very small and only a few
     */
    protected final ConcurrentMap<String, Class<?>> inMemoryClasses = new ConcurrentHashMap<String, Class<?>>();

    public ClassLoader getClassLoader() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = DefaultClassResolver.class.getClassLoader();
        }
        return loader;
    }

    public final Class<?> resolveClass(final String className) throws ClassNotFoundException {
        try {
            return resolvePersistentClass(className);
        } catch (ClassNotFoundException cnfx) {
            return resolveInMemoryClass(className);
        }
    }

    protected Class<?> resolveInMemoryClass(String className) throws ClassNotFoundException {
        Class<?> inMemoryClass = inMemoryClasses.get(className);
        if (inMemoryClass == null)
            throw new ClassNotFoundException(className);
        return inMemoryClass;
    }

    protected final Class<?> resolvePersistentClass(final String className) throws ClassNotFoundException {
        Class<?> clazz = null;
        WeakReference<Class<?>> ref = classes.get(className);

        // Might be garbage-collected between getting the WeakRef and retrieving
        // the Class from it.
        if (ref != null) {
            clazz = ref.get();
        }
        if (clazz == null) {
            if (className.equals("byte")) {
                clazz = byte.class;
            } else if (className.equals("short")) {
                clazz = short.class;
            } else if (className.equals("int")) {
                clazz = int.class;
            } else if (className.equals("long")) {
                clazz = long.class;
            } else if (className.equals("float")) {
                clazz = float.class;
            } else if (className.equals("double")) {
                clazz = double.class;
            } else if (className.equals("boolean")) {
                clazz = boolean.class;
            } else if (className.equals("char")) {
                clazz = char.class;
            } else {
                // synchronize on the only class member to load only one class at a time and
                // prevent LinkageError. See above for more info
                synchronized (classes) {
                    clazz = Class.forName(className, false, getClassLoader());
                    if (clazz == null) {
                        throw new ClassNotFoundException(className);
                    }
                }
                classes.put(className, new WeakReference<Class<?>>(clazz));
            }
        }
        return clazz;
    }

    public Iterator<URL> getResources(final String name) {
        Set<URL> resultSet = new TreeSet<URL>(new UrlExternalFormComparator());

        try {
            // Try the classloader for the wicket jar/bundle
            Enumeration<URL> resources = Application.class.getClassLoader().getResources(name);
            loadResources(resources, resultSet);

            // Try the classloader for the user's application jar/bundle
            resources = Application.get().getClass().getClassLoader().getResources(name);
            loadResources(resources, resultSet);

            // Try the context class loader
            resources = getClassLoader().getResources(name);
            loadResources(resources, resultSet);
        } catch (Exception e) {
            throw new WicketRuntimeException(e);
        }

        return resultSet.iterator();
    }

    /**
     * 
     * @param resources
     * @param loadedResources
     */
    private void loadResources(Enumeration<URL> resources, Set<URL> loadedResources) {
        if (resources != null) {
            while (resources.hasMoreElements()) {
                final URL url = resources.nextElement();
                loadedResources.add(url);
            }
        }
    }

    synchronized public Class<?> buildPageClassInMemory(Class<?> domainClass) throws ClassNotFoundException {
        String fullyQualifiedPageClassName = domainClass.getName() + "Page";
        log.debug("Looking for in-memory page class " + fullyQualifiedPageClassName);
        Class<?> pageClass = inMemoryClasses.get(fullyQualifiedPageClassName);
        if (pageClass == null) {
            Class<? extends WebPage> pageBaseClass = new WebDomainProperties(domainClass).getHTMLPageBaseClass();
            log.debug("In-memory page class " + fullyQualifiedPageClassName
                    + " not yet registered, building on-the-fly... ");
            pageClass = new InMemoryPageClassBuilder(domainClass, pageBaseClass).buildClassInMemory();
            registerInMemoryClass(pageClass);
        }
        return pageClass;
    }

    synchronized public Class<?> buildPanelClassInMemory(Class<?> domainClass) throws ClassNotFoundException {
        String fullyQualifiedPanelClassName = domainClass.getName() + "Panel";
        log.debug("Looking for in-memory panel class " + fullyQualifiedPanelClassName);
        Class<?> panelClass = inMemoryClasses.get(fullyQualifiedPanelClassName);
        if (panelClass == null) {
            Class<? extends Panel> panelBaseClass = new WebDomainProperties(domainClass).getHTMLPanelBaseClass();
            log.debug("In-memory panel class " + fullyQualifiedPanelClassName
                    + " not yet registered, building on-the-fly... ");
            //TODO JL: machen ;-)
            panelClass = new InMemoryPanelClassBuilder(domainClass, panelBaseClass).buildClassInMemory();
            registerInMemoryClass(panelClass);
        }
        return panelClass;
    }

    synchronized protected void registerInMemoryClass(Class<?> inMemoryClass) {
        inMemoryClasses.put(inMemoryClass.getName(), inMemoryClass);
    }
}
