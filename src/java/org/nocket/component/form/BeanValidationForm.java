package org.nocket.component.form;

import gengui.util.SevereGUIException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.transformer.AbstractTransformerBehavior;
import org.apache.wicket.model.IChainingModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.nocket.component.form.beans.BeanInfoPropertyDescriptor;
import org.nocket.component.form.beans.BeanInfoResolver;
import org.nocket.component.form.behaviors.ValidationStyleBehavior;
import org.nocket.component.form.behaviors.ValidationStyleGroupBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * Wicket Form with support for bean validation JSR 302 API.
 *
 * @author blaz02
 * @param <T> the generic type
 */
public class BeanValidationForm<T> extends Form<T> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(BeanValidationForm.class);

    /** The validators added. */
    private boolean validatorsAdded = false;

    /** The edit mode. */
    private boolean editMode;

    /** The validation style behavior class. */
    private Class<? extends AbstractTransformerBehavior> validationStyleBehaviorClass;
    
    /** The validation style group behavior class. */
    private Class<? extends AbstractTransformerBehavior> validationStyleGroupBehaviorClass;

    /**
     * Constructor for the form. Entity is a simple POJO with annotations
     * following JSR 303 Bean Validation standard. Entity and its nested POJOs
     * cannot be null. Internally entity will be put
     * {@link DMDCompoundPropertyModel}.
     *
     * @param id            wicket's id of the form.
     * @param entity            instance of the entity.
     */
    public BeanValidationForm(String id, T entity) {
        this(id, new DMDCompoundPropertyModel<T>(entity));
    }

    /**
     * Constructor for the form. Entity is a simple POJO with annotations
     * following JSR 303 Bean Validation standard. Entity and its nested POJOs
     * cannot be null. Internally entity will be put
     * {@link DMDCompoundPropertyModel}.
     *
     * @param id            wicket's id of the form.
     * @param model the model
     */
    public BeanValidationForm(String id, IModel<T> model) {
        this(id, new DMDCompoundPropertyModel<T>(model));
    }

    /**
     * Adds the with validation.
     *
     * @param childs the childs
     */
    public void addWithValidation(final Component... childs) {
        super.add(childs);
    }

    /**
     * Constructor for the form. The model and the object within it cannot be
     * null.
     *
     * @param id            Wicket's id of the form.
     * @param model the model
     */
    public BeanValidationForm(String id, DMDCompoundPropertyModel<T> model) {
        super(id, model);
        if (model == null)
            throw new IllegalStateException("Model cannot be null.");
    }

    /**
     * Checks if is edits the mode.
     *
     * @return true, if is edits the mode
     */
    public boolean isEditMode() {
        return editMode;
    }

    /**
     * Sets the edits the mode.
     *
     * @param editMode the new edits the mode
     */
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.markup.html.form.Form#onBeforeRender()
     */
    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        // Add validators to the controls before first rendering
        if (!validatorsAdded) {

            // Resolve properties of an entity.
            final Map<String, BeanInfoPropertyDescriptor> entityProperties = new BeanInfoResolver(getModelObject()
                    .getClass()).resolveProperties();

            // Visit children of the form and add validator to FormComponents
            // if the are bound to the properties of an entity
            visitChildren(new IVisitor<Component, Component>() {
                @Override
                public void component(Component component, IVisit<Component> visit) {
                    StringBuilder msg = new StringBuilder(192);
                    if (component instanceof FormComponent) {
                        FormComponent c = (FormComponent) component;
                        final String path = getComponentPropertyPath(c);
                        msg.append("Visiting: ").append(getValidatorPropertyPath(c)).append(". Property path is: ")
                                .append(path);
                        if (path == null) {
                            if (log.isDebugEnabled()) {
                                msg.append(". Continue with next component.");
                                log.debug(msg.toString());
                            }
                            return;
                        }
                        final BeanInfoPropertyDescriptor desc = entityProperties.get(path);
                        if (desc != null) {
                            if (log.isDebugEnabled()) {
                                msg.append(". Descriptor found.");
                                log.debug(msg.toString());
                            }
                            c.add(new JSR303Validator<T>(getValidatorPropertyPath(c), getValidatorPropertyPrompt(c),
                                    desc.getEntityClass(),
                                    editMode ? c.getModel() : null));
                            c.add(newValidationStyleBehavior(!belongsToGroupBorder(c)));
                        } else {
                            msg.append(". Descriptor NOT found.");
                            log.warn(msg.toString());
                        }
                    } else if (component instanceof ComponentGroup) {
                        component.add(newValidationStyleGroupBehavior());
                    }
                }
            });

            validatorsAdded = true;
        }
    }

    /**
     * Gets the validator property path.
     *
     * @param c the c
     * @return the validator property path
     */
    protected String getValidatorPropertyPath(FormComponent c) {
        return c.getId();
    }

    /**
     * Methods return True, if the specified component has a parent of Class
     * ComponentGroup.
     *
     * @param component the component
     * @return true if the specified component has a parent of class
     *         {@link ComponentGroup}.
     */
    private boolean belongsToGroupBorder(Component component) {
        for (Component current = component; current != null;) {
            final MarkupContainer parent = current.getParent();
            if (parent != null && parent instanceof ComponentGroup) {
                return true;
            }
            current = parent;
        }
        return false;
    }

    /**
     * Calculates component property path in the same manner as
     * {@link BeanInfoResolver} does.
     * 
     * @param component
     *            reference to the FormComponent.
     * 
     * @return string with a full class name and a property name i.e.:
     *         "my.example.package.MyPojo.propertyName".
     */
    protected String getComponentPropertyPath(FormComponent component) {
        for (IModel<?> m = component.getDefaultModel(); m != null && m instanceof IChainingModel<?>;) {
            if (m instanceof DMDCompoundPropertyModel<?>) {
                return ((DMDCompoundPropertyModel<?>) m).getTargetClass().getName() + "." + component.getId();
            }
            m = ((IChainingModel<?>) m).getChainedModel();
        }
        return null;
    }

    /**
     * Gets the validator property prompt.
     *
     * @param c the c
     * @return the validator property prompt
     */
    protected String getValidatorPropertyPrompt(FormComponent c) {
        return getComponentPropertyPath(c);
    }

    /**
     * Factory method for the Behavior, which will be added to every
     * FormComponent of the form. Default implementation returns
     *
     * @param showInline the show inline
     * @return the behavior
     */
    protected Behavior newValidationStyleBehavior(boolean showInline) {
        Behavior result;
        if (validationStyleBehaviorClass == null) {
            result = new ValidationStyleBehavior(showInline);
        } else {
            result = invokeBehavior(validationStyleBehaviorClass, showInline);
        }
        return result;
    }

    /**
     * Invoke behavior.
     *
     * @param clazz the clazz
     * @param showInline the show inline
     * @return the behavior
     */
    private Behavior invokeBehavior(Class<? extends AbstractTransformerBehavior> clazz, boolean showInline) {
        try {
            Constructor<? extends AbstractTransformerBehavior> constructor = clazz.getConstructor(boolean.class);
            return constructor.newInstance(showInline);
        } catch (IllegalArgumentException e) {
            throw new SevereGUIException(e);
        } catch (InstantiationException e) {
            throw new SevereGUIException(e);
        } catch (IllegalAccessException e) {
            throw new SevereGUIException(e);
        } catch (InvocationTargetException e) {
            throw new SevereGUIException(e);
        } catch (SecurityException e) {
            throw new SevereGUIException(e);
        } catch (NoSuchMethodException e) {
            throw new SevereGUIException(e);
        }
    }

    /**
     * Factory method for the Behavior, which will be added to every
     * FormComponent of the form. Default implementation returns
     *
     * @return the behavior
     */
    protected Behavior newValidationStyleGroupBehavior() {
        Behavior result;
        if (validationStyleGroupBehaviorClass == null) {
            result = new ValidationStyleGroupBehavior();
        } else {
            result = invokeBehavior(validationStyleGroupBehaviorClass);
        }
        return result;
    }

    /**
     * Invoke behavior.
     *
     * @param clazz the clazz
     * @return the abstract transformer behavior
     */
    private AbstractTransformerBehavior invokeBehavior(Class<? extends AbstractTransformerBehavior> clazz) {
        try {
            return clazz.newInstance();
        } catch (IllegalArgumentException e) {
            throw new SevereGUIException(e);
        } catch (InstantiationException e) {
            throw new SevereGUIException(e);
        } catch (IllegalAccessException e) {
            throw new SevereGUIException(e);
        } catch (SecurityException e) {
            throw new SevereGUIException(e);
        }
    }

    /**
     * Gets the validation style behavior class.
     *
     * @return the validation style behavior class
     */
    public Class<? extends AbstractTransformerBehavior> getValidationStyleBehaviorClass() {
        return validationStyleBehaviorClass;
    }

    /**
     * Sets the validation style behavior class.
     *
     * @param validationStyleBehaviorClass the new validation style behavior class
     */
    public void setValidationStyleBehaviorClass(
            Class<? extends AbstractTransformerBehavior> validationStyleBehaviorClass) {
        this.validationStyleBehaviorClass = validationStyleBehaviorClass;
    }

    /**
     * Gets the validation style group behavior class.
     *
     * @return the validation style group behavior class
     */
    public Class<? extends AbstractTransformerBehavior> getValidationStyleGroupBehaviorClass() {
        return validationStyleGroupBehaviorClass;
    }

    /**
     * Sets the validation style group behavior class.
     *
     * @param validationStyleGroupBehaviorClass the new validation style group behavior class
     */
    public void setValidationStyleGroupBehaviorClass(
            Class<? extends AbstractTransformerBehavior> validationStyleGroupBehaviorClass) {
        this.validationStyleGroupBehaviorClass = validationStyleGroupBehaviorClass;
    }

}
