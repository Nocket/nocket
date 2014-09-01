package org.nocket.component.table;

import java.util.MissingResourceException;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;
import org.apache.wicket.model.ResourceModel;

/**
 * This class is a work-around: GenericDataTable is based on low-case started
 * property names while the generated resource files of the generic part of
 * org.nocket are based on capital started property names so we try both notations
 * here. The class is so big because many things in the base class ResourceModel
 * are unfortunately final and private.
 */
public class UpperAndLowerCaseResourceModel extends ResourceModel {
    protected final String propertyName;
    protected final String resourceBaseName;

    public UpperAndLowerCaseResourceModel(String resourceBaseName, String propertyName) {
        super(resourceBaseName + propertyName, null);
        this.propertyName = propertyName;
        this.resourceBaseName = resourceBaseName;
    }

    @Override
    public String getObject() {
        try {
            return super.getObject();
        } catch (MissingResourceException mrx) {
            return findCapitalizedResource(null);
        }
    }

    protected String findCapitalizedResource(Component component) {
        String capitalizedPropertyName = StringUtils.capitalize(propertyName);
        String capitalizedKey = resourceBaseName + capitalizedPropertyName;
        return Application.get().getResourceSettings().getLocalizer()
                .getString(capitalizedKey, component, capitalizedPropertyName);
    }

    public IWrapModel<String> wrapOnAssignment(final Component component) {
        return new AssignmentWrapper(resourceBaseName + propertyName, null, component);
    }

    /**
     * I don't really know what this class is good for. I just copied it from
     * the inner private class of the same name from Wicket's
     * {@link ResourceModel} and modified method {@link #getObject()} according
     * to the UpperAndLowerCaseResourceModel's implementation of this method.
     */
    private class AssignmentWrapper extends ResourceModel implements IWrapModel<String> {
        private static final long serialVersionUID = 1L;

        private final Component component;

        public AssignmentWrapper(String resourceKey, String defaultValue, Component component) {
            super(resourceKey, defaultValue);
            this.component = component;
        }

        public IModel<String> getWrappedModel() {
            return UpperAndLowerCaseResourceModel.this;
        }

        @Override
        public String getObject() {
            try {
                return Application.get().getResourceSettings().getLocalizer()
                        .getString(resourceBaseName + propertyName, component, (String) null);
            } catch (MissingResourceException mrx) {
                return findCapitalizedResource(component);
            }
        }

        @Override
        public void detach() {
            super.detach();
            UpperAndLowerCaseResourceModel.this.detach();
        }

    }
}
