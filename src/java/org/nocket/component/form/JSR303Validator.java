package org.nocket.component.form;

import java.text.MessageFormat;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.nocket.gen.page.element.synchronizer.SynchronizerHelper;

/**
 * Validates input according to JSR303 bean annotations.
 * 
 * @author blaz02
 */
public class JSR303Validator<T> implements IValidator<T>, INullAcceptingValidator<T> {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(JSR303Validator.class);
	
	private static final String LOG_ENTRY = "JSR Validation: property={0}, class={1}. {2}";
	
	private static String DEFAULT_JAVAX_KEY = "javax.validation.constraints.";
	private static String DEFAULT_KEY = "Default.message";
	private static String EMPTY = "";

	private static transient ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

	private String propertyName;
	private Class<T> propertyClass;
	
	private boolean violated;

	public JSR303Validator(SynchronizerHelper helper) {
		this.propertyName = StringUtils.uncapitalize(helper.getPropertyName());
		this.propertyClass = helper.getRef().getDomainClass();
	}

	public void validate(IValidatable<T> iv) {
		this.violated = false;
		
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validateValue(propertyClass, propertyName, iv.getValue());
		for (ConstraintViolation<T> v : violations) {
			if (violated) {
				continue;
			}
			iv.error(newValidationError(propertyName, v));
			violated = true;
		}
		
		if(!violated && log.isDebugEnabled()) {
			log.debug(MessageFormat.format(LOG_ENTRY, propertyName, propertyClass, "No violation."));
		};
	}

	protected IValidationError newValidationError(String propertyName, ConstraintViolation<T> violation) {
		ValidationError ve = new ValidationError();
		String key = violation.getMessageTemplate();
		if (key != null) {
			if (key.startsWith("{")) {
				key = this.getMessageKey(key);
				ve.addKey(key);
			} else {
				ve.setMessage(key);
			}
		} else {
			ve.addKey(DEFAULT_KEY);
		}
		ve.setVariable("label", StringUtils.capitalize(propertyName));
		ve.getVariables().putAll(violation.getConstraintDescriptor().getAttributes());
		if(log.isDebugEnabled()) {
			log.debug(MessageFormat.format(LOG_ENTRY, propertyName, propertyClass, "Violation " + ve.toString()));
		}	
		return ve;
	}

	private String getMessageKey(String in) {
		String res = in.replaceAll("\\{", EMPTY).replaceAll("\\}", EMPTY);
		res = res.replaceAll(DEFAULT_JAVAX_KEY, EMPTY);
		return res;
	}

}
