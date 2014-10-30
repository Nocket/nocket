package org.nocket.gen.page.element.synchronizer;

import gengui.annotations.Prompt;
import gengui.domain.DomainObjectReference;
import gengui.util.AnnotationHelper;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.MissingResourceException;

import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
import org.nocket.gen.domain.element.MultivalueColumnElement;
import org.nocket.gen.i18n.I18NLabelModel;
import org.nocket.gen.page.DMDWebGenPageContext;

// TODO: Auto-generated Javadoc
/**
 * The Class TableDownloadCallback.
 */
public class TableDownloadCallback extends TableCallback implements Serializable {

    /** The Constant ICON_ENABLED. */
    private static final String ICON_ENABLED = "{0}.icon.enabled";
    
    /** The Constant ICON_DISABLED. */
    private static final String ICON_DISABLED = "{0}.icon.disabled";

    /**
     * Instantiates a new table download callback.
     *
     * @param context the context
     * @param columnElement the column element
     */
    public TableDownloadCallback(DMDWebGenPageContext context,
            MultivalueColumnElement<DomainObjectReference> columnElement) {
        super(context, columnElement);
    }

    /**
     * Gets the wicket id.
     *
     * @return the wicket id
     */
    public String getWicketId() {
        return helper.getWicketId();
    }

    /**
     * Gets the property name.
     *
     * @return the property name
     */
    public String getPropertyName() {
        return helper.getPropertyName();
    }

    /**
     * Gets the method.
     *
     * @return the method
     */
    public Method getMethod() {
        return helper.getGetterMethod();
    }

    /**
     * Gets the prompt.
     *
     * @return the prompt
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Gets the properties wicket id.
     *
     * @return the properties wicket id
     */
    public String getPropertiesWicketId() {
        return propertiesWicketId;
    }

    /**
     * Checks if is enabled.
     *
     * @param targetObject the target object
     * @return true, if is enabled
     */
    public boolean isEnabled(Object targetObject) {
        helper.setForcedMethodTargetObject(targetObject);
        return helper.isEnabled();
    }

    /**
     * Gets the tooltip.
     *
     * @param targetObject the target object
     * @return the tooltip
     */
    public String getTooltip(Object targetObject) {
        helper.setForcedMethodTargetObject(targetObject);
        return helper.getFieldTooltip();
    }

    /**
     * Gets the icon resource name.
     *
     * @param targetObject the target object
     * @param c the c
     * @return the icon resource name
     */
    public String getIconResourceName(Object targetObject, Component c) {
        if (helper.getContext().getConfiguration().isLocalizationWicket()) {
            String key = isEnabled(targetObject) ? getIconTableKey(ICON_ENABLED, getPropertiesWicketId()) :
                    getIconTableKey(ICON_DISABLED, getPropertiesWicketId());
            return localizeLikeWicket(key, c.getPage());
        }
        String key = null;
        Class<?> domainClass = helper.getRef().getDomainClass();
        Method getterMethod = helper.getGetterMethod();
        Prompt prompt = new AnnotationHelper(getterMethod).getAnnotation(Prompt.class);
        if (prompt != null) {
            key = isEnabled(targetObject) ? getIconTableKey(ICON_ENABLED, prompt.value()) :
                    getIconTableKey(ICON_DISABLED, prompt.value());
        } else {
            key = isEnabled(targetObject) ? getIconTableKey(ICON_ENABLED, helper.getPropertyName()) :
                    getIconTableKey(ICON_DISABLED, helper.getPropertyName());
        }
        return new I18NLabelModel(domainClass, key).getObject();
    }

    /**
     * Gets the icon table key.
     *
     * @param pattern the pattern
     * @param baseKey the base key
     * @return the icon table key
     */
    private String getIconTableKey(String pattern, String baseKey) {
        return MessageFormat.format(pattern, baseKey);
    }

    /**
     * Localize like wicket.
     *
     * @param key the key
     * @param c the c
     * @return the string
     */
    private String localizeLikeWicket(String key, Component c) {
        try {
            return Localizer.get().getString(key, c.getPage());
        } catch (MissingResourceException e) {
            return null;
        }
    }

}
