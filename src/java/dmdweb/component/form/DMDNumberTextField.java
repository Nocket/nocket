package dmdweb.component.form;

import java.util.Locale;
import java.util.Map;

import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.util.lang.Objects;
import org.apache.wicket.validation.ValidationError;

/**
 * At present Firefox and IE fail to support the html5 input fields with type number. The w3c demands, that the browsers job is to convert the
 * number in a number field to the locale defined in the browser. <br>
 * Because of this, the DMDNumberTextField doesn't support input fields with type number. It uses input fields with type text and the the locale
 * that is stored in the component or page or session.
 * 
 * @author meis026
 *
 * @param <N>
 */
public class DMDNumberTextField<N extends Number & Comparable<N>> extends NumberTextField<N> {

    private static final long serialVersionUID = 1L;

	public DMDNumberTextField(String id, IModel<N> model, Class<N> type) {
		super(id, model, type);
    }

    public DMDNumberTextField(String wicketId) {
        super(wicketId);
    }

	@Override
	protected String getInputType()
	{
		return "text";
	}

	@Override
	protected String getModelValue()
	{
		Number value = getModelObject();
		if (value == null)
		{
			return "";
		}
		else
		{
			IConverter<Number> converter = (IConverter<Number>) getConverter(value.getClass());
			if (converter != null) {
				return converter.convertToString(value, this.getLocale());
			} else {
				return Objects.stringValue(value);
			}
		}
	}

	/**
	 * Always use locale from component or page or component to parse the input.
	 */
	@Override
	protected void convertInput()
	{
		IConverter<N> converter = getConverter(getNumberType());

		try
		{
			// use locale from component or page or session
			setConvertedInput(converter.convertToObject(getInput(), this.getLocale()));
		} catch (ConversionException e)
		{
			ValidationError error = new ValidationError();
			if (e.getResourceKey() != null)
			{
				error.addMessageKey(e.getResourceKey());
			}
			if (e.getTargetType() != null)
			{
				error.addMessageKey("ConversionError." + Classes.simpleName(e.getTargetType()));
			}
			error.addMessageKey("ConversionError");

			final Locale locale = e.getLocale();
			if (locale != null)
			{
				error.setVariable("locale", locale);
			}
			error.setVariable("exception", e);

			Map<String, Object> variables = e.getVariables();
			if (variables != null)
			{
				error.getVariables().putAll(variables);
			}

			error(error);
		}
	}

	protected Class<N> getNumberType()
	{
		Class<N> numberType = getType();
		if (numberType == null && getModelObject() != null)
		{
			numberType = (Class<N>) getModelObject().getClass();
		}
		return numberType;
	}

}
