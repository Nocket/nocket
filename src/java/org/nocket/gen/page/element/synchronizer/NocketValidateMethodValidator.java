package org.nocket.gen.page.element.synchronizer;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;

/**
 * Validates input by calling validate...() method. This is additional way to
 * perform validations besides of JSR validations.
 * <p/>
 * For example for string property "firstName" method should be like this:
 * <p/>
 * <code>
 * public String validateFirstName(String value) { ... }
 * </code>
 * 
 * @author blaz02
 * 
 */
public class NocketValidateMethodValidator<T> implements IValidator<T> {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(NocketValidateMethodValidator.class);

	private static final String LOG_ENTRY = "Nocket validation: method={0}. {2}.";

	private SynchronizerHelper helper;

	public NocketValidateMethodValidator(SynchronizerHelper helper) {
		this.helper = helper;
	}

	@Override
	public void validate(IValidatable<T> validatable) {
		String logResult = "No violation";
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
				logResult = "Violation " + validationError;
			}
			if (log.isDebugEnabled()) {
				log.debug(MessageFormat.format(LOG_ENTRY, helper.getValidatorMethod(), helper.getRef().getDomainClass(), logResult));
			}
		}
	}

}
