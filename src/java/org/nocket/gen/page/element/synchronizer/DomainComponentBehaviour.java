package org.nocket.gen.page.element.synchronizer;

import gengui.annotations.Eager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.AbstractSingleSelectChoice;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.lang.Objects;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.AbstractValidator;
import org.nocket.component.form.JSR303Validator;
import org.nocket.gen.page.element.ButtonElement;
import org.nocket.gen.page.element.CheckboxInputElement;
import org.nocket.gen.page.element.PageElementI;
import org.nocket.gen.page.element.RadioInputElement;
import org.nocket.gen.page.element.SelectElement;
import org.nocket.gen.page.element.TextAreaElement;
import org.nocket.gen.page.element.TextInputElement;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedBeanValidationForm;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedButton;

import de.bertelsmann.coins.general.error.Assert;

/**
 * This behavior class manipulates components in several ways.<br/>
 * <br/>
 * - controls setVisible and setEnabled states for a component<br/>
 * - invokes custom validator methods<br/>
 * - add ajax for handling {@link Eager} fields<br/>
 * - adds tool tips for input fields<br/>
 * - switches TextFields and TextAreas to read-only<br/>
 */
public class DomainComponentBehaviour extends AbstractValidator<Object> {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(DomainComponentBehaviour.class);

	private static final String TITLE = "title";
	private static final String ONCHANGE = "onchange";
	private static final String ONCLICK = "onclick";
	private static final String READONLY = "readonly";
	private static final String DISABLED = "disabled";

	private final Component component;
	private final SynchronizerHelper helper;
	private final boolean hideButton;

	private List<IValidator<Object>> validators;

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
		this.component = component;

		// Add validators
		addValidatorsForComponent();

		addTooltipForField(component);

		if (helper.isEager()) {
			enableAjaxForEagerField(element, component);
		}

		addNullValidToChoiceField(element, component);
		switchDisabledTextFieldsToReadonly(element, component);
	}

	private void addValidatorsForComponent() {
		validators = new ArrayList<IValidator<Object>>();
		
		// Throw exception is validator required without validator there.
		helper.assertValidate();

		if(helper.isProperty()) {
			validators.add(new JSR303Validator<Object>(helper));
		}
		validators.add(new NocketValidateMethodValidator<Object>(helper));
	}

	/**
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
	 * @see org.apache.wicket.validation.validator.AbstractValidator#onValidate(org.apache.wicket.validation.IValidatable)
	 */
	@Override
	protected void onValidate(IValidatable<Object> validatable) {
		if (performValidation(validatable)) {
			for (IValidator<Object> v : validators) {
				v.validate(validatable);
			}
		}
	}

	private boolean performValidation(IValidatable<Object> validatable) {
		GeneratedBeanValidationForm<?> form = getParentForm();
		if (form.isEagerProcessing()) {
			if(form.isForcedProcessing()) {
				return false;
			}
			if (Objects.isEqual(validatable.getValue(), component.getDefaultModelObject())) {
				if (log.isDebugEnabled()) {
					log.debug(MessageFormat.format("No validation for component: wicket id={0}, old value={1}, new value={2}.",
							helper.getWicketId(), component.getDefaultModelObject(), validatable.getValue()));
				}
				return false;
			} else {
				if (log.isDebugEnabled()) {
					log.debug(MessageFormat.format("Validation required for component: wicket id={0}, old value={1}, new value={2}.",
							helper.getWicketId(), component.getDefaultModelObject(), validatable.getValue()));
				}
				return true;
			}
		}
		return true;
	}

	private GeneratedBeanValidationForm<?> getParentForm() {
		GeneratedBeanValidationForm<?> parentForm = component.findParent(GeneratedBeanValidationForm.class);
		Assert.notNull(parentForm, "Form for the component " + component.getId() + " cannot be found.");
		return parentForm;
	}

	/**
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
		component.add(new EagerAjaxFormSubmitBehavior(getOnChangeEventName(element), helper));
	}

	/**
	 * This method checks whether the form contains an invisible or disabled
	 * button that should now be re-enabled or re-displayed by sending it with
	 * the AjaxRequestTarget
	 * 
	 * @param target
	 * @return
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
			event = ONCLICK; // IE fix
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
