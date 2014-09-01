package org.nocket.component.form;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * Validates FormComponent inputs according to JSR303 bean annotations.
 * 
 * @author blaz02
 */
class JSR303Validator<T> implements IValidator<T>, INullAcceptingValidator<T> {

    private static final long serialVersionUID = 4153742656078063152L;

    private static String EMPTY = "";
    private static String DEFAULT_KEY = "Default.message";
    private static String DEFAULT_JAVAX_KEY = "javax.validation.constraints.";

    private static transient ValidatorFactory factory = Validation
            .buildDefaultValidatorFactory();

    private String propertyName;
    private String propertyPrompt;
    private Class<T> propertyClass;
    private final IModel<?> currentValue;
    private boolean violated;

    public JSR303Validator(String propertyName, String propertyPrompt, Class<T> propertyClass, IModel<?> currentValue) {
        this.propertyName = propertyName;
        this.propertyPrompt = propertyPrompt;
        this.propertyClass = propertyClass;
        this.currentValue = currentValue;
    }

    public void validate(IValidatable<T> iv) {
        this.violated = false;
        // Only validates changed values
        if (valueNotChanged(iv)) {
            return;
        }
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validateValue(propertyClass, propertyName, iv.getValue());
        for (ConstraintViolation<T> v : violations) {
            if (violated) {
                continue;
            }
            iv.error(newValidationError(propertyName, propertyPrompt, v));
            violated = true;
        }
    }

    private boolean valueNotChanged(IValidatable<T> iv) {
        return currentValue != null && iv.getValue() != null && iv.getValue().equals(currentValue.getObject());
    }

    protected IValidationError newValidationError(String propertyName, String propertyPrompt,
            ConstraintViolation<T> violation) {
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
        ve.setVariable("label", propertyPrompt);
        ve.getVariables().putAll(violation.getConstraintDescriptor().getAttributes());
        return ve;
    }

    private String getMessageKey(String in) {
        String res = in.replaceAll("\\{", EMPTY).replaceAll("\\}", EMPTY);
        res = res.replaceAll(DEFAULT_JAVAX_KEY, EMPTY);
        return res;
    }

}
