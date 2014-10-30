package org.nocket.gen.page.element.synchronizer;

import gengui.annotations.Eager;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.AbstractSingleSelectChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.validator.AbstractValidator;
import org.nocket.NocketSession;
import org.nocket.gen.page.element.ButtonElement;
import org.nocket.gen.page.element.CheckboxInputElement;
import org.nocket.gen.page.element.PageElementI;
import org.nocket.gen.page.element.RadioInputElement;
import org.nocket.gen.page.element.SelectElement;
import org.nocket.gen.page.element.TextAreaElement;
import org.nocket.gen.page.element.TextInputElement;
import org.nocket.gen.page.guiservice.DMDWebGenGuiServiceProvider;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedButton;

// TODO: Auto-generated Javadoc
/**
 * This behavior class manipulates components in several ways.<br>
 * <br>
 * - controls setVisible and setEnabled states for a component<br>
 * - invokes custom validator methods<br>
 * - add ajax for handling {@link Eager}<br>
 * - adds tooltips for input fields<br>
 * - it switches disabled Textfields and TextAreas to readonly<br>
 */
public class DomainComponentBehaviour extends AbstractValidator<Object> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant TITLE. */
    private static final String TITLE = "title";
    
    /** The Constant ONCHANGE. */
    private static final String ONCHANGE = "onchange";
    
    /** The Constant ONCLICK. */
    private static final String ONCLICK = "onclick";
    
    /** The Constant READONLY. */
    private static final String READONLY = "readonly";
    
    /** The Constant DISABLED. */
    private static final String DISABLED = "disabled";

    /** The helper. */
    private final SynchronizerHelper helper;
    
    /** The hide button. */
    private final boolean hideButton;

    /**
     * The constructor for this class.
     * 
     * @param element
     *            {@link PageElementI}.
     * @param component
     *            {@link Component}.
     */
    public DomainComponentBehaviour(PageElementI<?> element, final Component component) {
        this.helper = new SynchronizerHelper(element);
        this.hideButton = element instanceof ButtonElement;

        // Throw exception is validator required without validator there.
        helper.assertValidate();

        addTooltipForField(component);

        if (helper.isEager()) {
            enableAjaxForEagerField(element, component);
        }

        addNullValidToChoiceField(element, component);
        switchDisabledTextFieldsToReadonly(element, component);
    }

    /**
     * On configure.
     *
     * @param component the component
     * @see org.apache.wicket.behavior.Behavior#onConfigure(org.apache.wicket.Component)
     */
    @Override
    public void onConfigure(Component component) {
        super.onConfigure(component);
        component.setEnabled(helper.isEnabled());
        if (hideButton && helper.getHiderMethod() != null) {
            component.setVisible(helper.invokeHiderMethod() == null);
        }
    }

    /**
     * On validate.
     *
     * @param validatable the validatable
     * @see org.apache.wicket.validation.validator.AbstractValidator#onValidate(org.apache.wicket.validation.IValidatable)
     */
    @Override
    protected void onValidate(IValidatable<Object> validatable) {
        if (helper.getValidatorMethod() != null) {
            final String validationError = (String) helper.invokeValidatorMethod(validatable.getValue());
            if (StringUtils.isNotBlank(validationError)) {
                validatable.error(new IValidationError() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getErrorMessage(IErrorMessageSource messageSource) {
                        String resourceMessage = messageSource.getMessage(validationError, null);
                        if (resourceMessage != null) {
                            return resourceMessage;
                        } else {
                            return validationError;
                        }
                    }
                });
            }
        }
    }

    /**
     * Validate on null value.
     *
     * @return true, if successful
     * @see org.apache.wicket.validation.validator.AbstractValidator#validateOnNullValue()
     */
    @Override
    public boolean validateOnNullValue() {
        return true;
    }

    /**
     * Switches TextFields and TextAreas from disabled to readonly.
     * 
     * Select drops down boxes are also manipulated. They are switched to
     * invisible via css, then the dropdown is replaced by a input field. The
     * input field is injected using the ComponentTag name, which hold the
     * string "select". We replace select with the parsed content of the
     * SELECT_HACK constant. (evil IE convinience hack...)
     * 
     * @param element
     *            {@link PageElementI}.
     * @param component
     *            {@link Component}.
     */
    private void switchDisabledTextFieldsToReadonly(PageElementI<?> element, final Component component) {
        // Replace "disabled" for TextAreas and Input Fields with "readonly".
        if (element instanceof TextInputElement || element instanceof TextAreaElement) {
            component.add(new Behavior() {
                private static final long serialVersionUID = 1L;

                @Override
                public void onComponentTag(Component component, ComponentTag tag) {
                    // Switch TextFields and TextAreas from disabled to readonly
                    if (tag.getAttributes().containsKey(DISABLED)) {
                        tag.getAttributes().remove(DISABLED);
                        tag.getAttributes().put(READONLY, READONLY);
                    }
                    super.onComponentTag(component, tag);
                }
            });
        } else if (element instanceof SelectElement) {
            component.add(new Behavior() {
                private static final long serialVersionUID = 1L;

                @Override
                public void onComponentTag(Component component, ComponentTag tag) {
                    if (tag.getAttributes().containsKey(DISABLED)) {
                        tag.getAttributes().put(READONLY, READONLY);
                    }
                    super.onComponentTag(component, tag);
                }
            });
        }
    }

    /**
     * Oh boy, this is one line of code for a veeeery special trick.
     * <p>
     * This method forces the bound attribute's setter to be invoked at the very
     * end of the synchronization phase in case the synchronization was trigger
     * by an eager-update of the behaviour's component. We assume that a
     * component is only eager-annotated when its setter performs a piece of
     * relevant application logic. Invoking the setter *at last* of all
     * attributes ensures that its logic can rely on all other attributes being
     * already in sync with the user's input.<br>
     * The method is called "reinvoke" because the setter will also be
     * automatically called earlier by Wicket, but we can't tell at which time
     * in relation to all the other attributes. So this is definitely the second
     * time which should not cause any performance issues as the setters of
     * eager-updated attributes must perform their job very fast anyway.<br>
     * The setter is called with the current attribute's value, which should be
     * OK for a re-invokation according to the
     * "Stang'sches Synchronisierungskalkül". If the attribute's value may be
     * influenced by other attributes' setters, these other attributes should
     * also be eager-updated. This in turn means that their appropriate GUI
     * components won't change their value in this phase here and therefore
     * their setters won't be invoked. So the current attribute's value will
     * only be changed by its own GUI component here and can therefore be set to
     * the same value when being set for the second time.
     * <p>
     * The whole thing looks so strange because it is a poor-man's solution. The
     * proper solution would be to change the order of model updates within
     * Wicket so that the setter must only be called only *once*. But all
     * methods in the Form class like internalUpdateFormComponentModels and
     * updateFormComponentModels and the internal class FormModelUpdateVisitor
     * are all private and final and so on :-(
     */
    protected void reinvokeSetter() {
        helper.invokeSetterMethod(helper.invokeGetterMethod());
    }

    /**
     * Add eager behavior.
     * 
     * @param element
     *            {@link PageElementI}.
     * @param component
     *            {@link Component}.
     */
    private void enableAjaxForEagerField(PageElementI<?> element, final Component component) {
        component.add(new AjaxFormSubmitBehavior(getOnChangeEventName(element)) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                finalize(target);
            }

            @Override
            protected void onError(final AjaxRequestTarget target) {
                SynchronizerHelper.synchronizeModelsForValidInput(Form.findForm(component));
                finalize(target);
            }

            protected void finalize(final AjaxRequestTarget target) {
                DMDWebGenGuiServiceProvider webGenGuiServiceProvider = NocketSession.get()
                        .getDMDWebGenGuiServiceProvider();
                try {
                    webGenGuiServiceProvider.registerAjaxRequestTarget(helper.getContext(), target);
                    helper.updateAllForms(target);
                } finally {
                    webGenGuiServiceProvider.unregisterAjaxRequestTarget(helper.getContext(), target);
                }
                // vocke03: GlasPane entfernen, wenn ein disabled oder invisible Button in der Form enthalten ist
                if (formCreatesInvisibleOrDisabledButton(target)) {
                    target.appendJavaScript("unblock();");
                }
            }
        });
    }

    /**
     * This method checks whether the form contains an invisible or disabled
     * button that should now be re-enabled or re-displayed by sending it with
     * the AjaxRequestTarget.
     *
     * @param target the target
     * @return true, if successful
     */
    protected boolean formCreatesInvisibleOrDisabledButton(AjaxRequestTarget target) {
        for (Component comp : target.getComponents()) {
            if (comp instanceof GeneratedButton) {
                GeneratedButton button = (GeneratedButton) comp;
                return button.isEnabled() || button.determineVisibility();
            }
        }
        return false;
    }

    /**
     * Gets the correct name of the onChange Element.
     * 
     * @param element
     *            {@link PageElementI}.
     * @return String.
     */
    private String getOnChangeEventName(PageElementI<?> element) {
        final String event;
        if (element instanceof CheckboxInputElement || element instanceof RadioInputElement) {
            event = ONCLICK; //IE fix
        } else {
            event = ONCHANGE;
        }
        return event;
    }

    /**
     * Add tooltip handler.
     * 
     * @param component
     *            {@link Component}.
     */
    private void addTooltipForField(final Component component) {
        component.add(new AttributeModifier(TITLE, new AbstractReadOnlyModel<String>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
                return helper.getFieldTooltip();
            }
        }));
    }

    /**
     * Add null valid behaviour.
     * 
     * @param element
     *            {@link PageElementI}.
     * @param component
     *            {@link Component}.
     */
    private void addNullValidToChoiceField(PageElementI<?> element, final Component component) {
        if (element instanceof SelectElement && component instanceof AbstractSingleSelectChoice) {
            component.add(new Behavior() {
                private static final long serialVersionUID = 1L;
                private final AbstractSingleSelectChoice<?> choice = (AbstractSingleSelectChoice<?>) component;

                @Override
                public void onConfigure(Component component) {
                    choice.setNullValid(helper.isChoicesNullValid());
                }
            });
        }
    }

}
