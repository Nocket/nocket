package org.nocket.gen.page.element.synchronizer;

import gengui.util.I18nPropertyBasedImpl;

import java.util.MissingResourceException;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.ResourceModel;
import org.nocket.gen.page.DMDWebGenPageContext;

public class EnumChoicesRenderer<T extends Enum<T>> implements IChoiceRenderer<T> {

    private static final long serialVersionUID = 1L;

    private final EnumChoiceRenderer<T> delegate;
    private DMDWebGenPageContext context;

    public EnumChoicesRenderer(DMDWebGenPageContext context) {
        delegate = new EnumChoiceRenderer<T>(context.getPage());
        this.context = context;
    }

    @Override
    public Object getDisplayValue(T object) {
        try {
            if (context.getConfiguration().isLocalizationWicket()) {
                return delegate.getDisplayValue(object);
            } else {
                return new I18nPropertyBasedImpl().translate(object.getDeclaringClass(), object);
            }
        } catch (MissingResourceException e) {
            String stringValue = object.toString();
            String resourceValue = new ResourceModel(stringValue, "").getObject();
            if (StringUtils.isNotBlank(resourceValue)) {
                return resourceValue;
            } else {
                return stringValue;
            }
        }
    }

    @Override
    public String getIdValue(T object, int index) {
        return delegate.getIdValue(object, index);
    }

}
