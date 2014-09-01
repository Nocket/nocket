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

public class TableDownloadCallback extends TableCallback implements Serializable {

    private static final String ICON_ENABLED = "{0}.icon.enabled";
    private static final String ICON_DISABLED = "{0}.icon.disabled";

    public TableDownloadCallback(DMDWebGenPageContext context,
            MultivalueColumnElement<DomainObjectReference> columnElement) {
        super(context, columnElement);
    }

    public String getWicketId() {
        return helper.getWicketId();
    }

    public String getPropertyName() {
        return helper.getPropertyName();
    }

    public Method getMethod() {
        return helper.getGetterMethod();
    }

    public String getPrompt() {
        return prompt;
    }

    public String getPropertiesWicketId() {
        return propertiesWicketId;
    }

    public boolean isEnabled(Object targetObject) {
        helper.setForcedMethodTargetObject(targetObject);
        return helper.isEnabled();
    }

    public String getTooltip(Object targetObject) {
        helper.setForcedMethodTargetObject(targetObject);
        return helper.getFieldTooltip();
    }

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

    private String getIconTableKey(String pattern, String baseKey) {
        return MessageFormat.format(pattern, baseKey);
    }

    private String localizeLikeWicket(String key, Component c) {
        try {
            return Localizer.get().getString(key, c.getPage());
        } catch (MissingResourceException e) {
            return null;
        }
    }

}
