package org.nocket.gen.page.visitor.bind.builder.components;


import gengui.guiadapter.DateSynchronizer;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.nocket.gen.domain.WebDomainProperties;
import org.nocket.gen.page.element.PageElementI;
import org.nocket.gen.page.element.synchronizer.SynchronizerHelper;

public class GeneratedDateTextField extends TextField<Date> implements ITextFormatProvider {

	private static final long serialVersionUID = 1L;

    protected String datePattern;
    protected IConverter<Date> converter = null;

	@SuppressWarnings("unchecked")
	public GeneratedDateTextField(PageElementI<?> e) {
		super(e.getWicketId(), (IModel<Date>) e.getModel());
    }

    /**
     * Minimal constructor expecting second initialization phase by a call of
     * postInit. This is of interest for the creation of customized derivations
     */
    public GeneratedDateTextField(String wicketId) {
        super(wicketId);
    }

    public void postInit(PageElementI<?> e) {
        this.setDefaultModel(e.getModel());
        datePattern = extractDateFormat(e);
        if (new SynchronizerHelper(e).isAssisted()) {
            // The default implementation of DatePicker has a different logic for
            // enabling. That's why we are using a derivation here.
            DatePicker picker = new DatePicker() {
                @Override
                public boolean isEnabled(Component component) {
                    return component.isEnabled();
                }
            };
            picker.setAutoHide(true);
            add(picker);
        }
    }

    private static String extractDateFormat(PageElementI<?> e) {
        String format = new SynchronizerHelper(e).getFormat();
        if (format == null) {
			// Get the default from the gengui.properties
			format = new WebDomainProperties().getDateFormat();
			return StringUtils.isNotBlank(format) ? format : DateSynchronizer.defaultOutFormat;
        } else {
            return format;
        }
    }

    @Override
    public String getTextFormat() {
        return datePattern;
    }

	public void setConverter(IConverter<Date> converter) {
    	this.converter = converter;
    }

	public IConverter<Date> getConverter() {
		return this.converter;
	}

    @SuppressWarnings("unchecked")
    @Override
    public <C> IConverter<C> getConverter(Class<C> clazz) {
        if (Date.class.isAssignableFrom(clazz)) {
            converter = createConverter();
            return (IConverter<C>) converter;
        } else {
            return super.getConverter(clazz);
        }
    }

	@Override
	protected Date convertValue(String[] values) throws ConversionException
	{
		String value = (values != null && values.length > 0 ? StringUtils.trimToNull(values[0]) : null);
		if (value == null) {
			return null;
		}

		return getConverter(Date.class).convertToObject(value, this.getLocale());
	}

    private IConverter<Date> createConverter() {
		return new YearCorrectingDateConverter(datePattern);
    }

}
