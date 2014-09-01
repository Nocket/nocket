package org.nocket.component.form.beans;

import gengui.util.SevereGUIException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resolves properties of the specified entity class.
 * 
 * Usage:
 * 
 * <pre>
 * ...
 * final Map<String, BeanInfoPropertyDescriptor> entityProperties =
 *    new BeanInfoResolver(my.example.package.MyPojo.class).resolveProperties()
 * ...
 * </pre>
 * 
 * It works recursively, with respect to cycle references.
 * 
 * @author blaz02
 */
public class BeanInfoResolver {

    final private static Logger log = LoggerFactory.getLogger(BeanInfoResolver.class);

    final private static List<String> IGNORE = Arrays.asList("java.lang", "java.util.Date", "java.math");

    private Class<?> entityClass;
    private Set<Class<?>> alredyResolved;

    /**
     * Constructor.
     * 
     * @param entityClass
     *            Class which has to be resolved.
     */
    public BeanInfoResolver(Class<?> entityClass) {
        this(entityClass, new HashSet<Class<?>>());
    }

    /**
     * Friendly constructor for recursive resolving.
     * 
     * @param entityClass
     * @param alreadyResolved
     */
    BeanInfoResolver(Class<?> entityClass, Set<Class<?>> alreadyResolved) {
        this.entityClass = entityClass;
        this.alredyResolved = alreadyResolved;
    }

    /**
     * Performs property resolving of the entity class.
     * 
     * @return Map with the all information about class properties. Key is a
     *         string with a full class name and a property name i.e.:
     *         "my.example.package.MyPojo.propertyName".
     */
    public HashMap<String, BeanInfoPropertyDescriptor> resolveProperties() {
        if (log.isDebugEnabled()) {
            log.debug("Analyzing: " + entityClass.getCanonicalName());
        }

        alredyResolved.add(entityClass);

        HashMap<String, BeanInfoPropertyDescriptor> entityProperties = new HashMap<String, BeanInfoPropertyDescriptor>();

        BeanInfo entityBeanInfo;
        try {
            entityBeanInfo = Introspector.getBeanInfo(entityClass);
        } catch (IntrospectionException ex) {
            throw new SevereGUIException(ex);
        }

        for (PropertyDescriptor p : entityBeanInfo.getPropertyDescriptors()) {
            entityProperties.put(getPropertyPath(p), new BeanInfoPropertyDescriptor(entityClass, p));
            // Decide if a property should be resolved recursive
            if (doRecursive(p.getPropertyType()) && !isClassAlreadyResolved(p.getPropertyType()))
                entityProperties.putAll(new BeanInfoResolver(p.getPropertyType(), this.alredyResolved)
                        .resolveProperties());
        }

        return entityProperties;
    }

    private String getPropertyPath(PropertyDescriptor p) {
        return entityClass.getName() + "." + p.getName();
    }

    private boolean doRecursive(Class<?> entityClass) {
        if (entityClass == null || entityClass.isEnum())
            return false;
        String canonicalName = entityClass.getCanonicalName();
        for (String s : IGNORE) {
            if (canonicalName.startsWith(s))
                return false;
        }
        return true;
    }

    /**
     * @param entityClass
     *            Class name
     * 
     * @return True if class has been resolved already. This is important to
     *         avoid cycle references.
     */
    private boolean isClassAlreadyResolved(Class<?> entityClass) {
        return alredyResolved.contains(entityClass);
    }

}
