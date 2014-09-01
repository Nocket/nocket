package dmdweb.gen.page.visitor.bind.builder.components;

import java.text.DecimalFormat;
import java.util.Locale;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.AbstractDecimalConverter;

import dmdweb.component.form.DMDNumberTextField;
import dmdweb.gen.page.element.TextInputElement;
import dmdweb.gen.page.element.synchronizer.SynchronizerHelper;

public class GeneratedNumberTextField<N extends Number & Comparable<N>> extends DMDNumberTextField<N> {

    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_DECIMAL_FORMAT_STR = "#,##0.##";
    private static final DecimalFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat(DEFAULT_DECIMAL_FORMAT_STR);
    private static final Locale HTML5_LOCALE = Locale.GERMANY;
    private String format;
    private boolean rangeValidationAllowed;

    public GeneratedNumberTextField(TextInputElement element) {
        super(element.getWicketId(), (IModel) element.getModel(), (Class) element.getDomainElement().getMethod()
                .getReturnType());
        this.format = new SynchronizerHelper(element).getFormat();
        this.rangeValidationAllowed = element.getDomainElement().isRangedNumberType();
    }

    /**
     * Minimal constructor expecting second initialization phase by a call of
     * postInit. This is of interest for the creation of customized derivations
     */
    public GeneratedNumberTextField(String wicketId) {
        super(wicketId);
    }

	public void postInit(TextInputElement element) {
        this.setDefaultModel(element.getModel());
        this.setType(element.getDomainElement().getMethod().getReturnType());
        this.format = new SynchronizerHelper(element).getFormat();
        this.rangeValidationAllowed = element.getDomainElement().isRangedNumberType();
    }

    @Override
    public <C> IConverter<C> getConverter(Class<C> type) {
		IConverter<C> converter = super.getConverter(type);
		customizeConverter(converter, format);
        return converter;
    }

    public static <C> void customizeConverter(IConverter<C> converter, String format) {
        if (converter instanceof AbstractDecimalConverter<?>) {
            AbstractDecimalConverter<?> adc = (AbstractDecimalConverter<?>) converter;
            final DecimalFormat decimalFormat;
            if (format != null && !format.equals(DEFAULT_DECIMAL_FORMAT_STR)) {
                decimalFormat = new DecimalFormat(format);
            } else {
                decimalFormat = DEFAULT_DECIMAL_FORMAT;
            }
            adc.setNumberFormat(HTML5_LOCALE, decimalFormat);
            for (Locale l : Locale.getAvailableLocales()) {
                adc.setNumberFormat(l, decimalFormat);
            }
        }
    }

    @Override
    public void onConfigure() {
        if (rangeValidationAllowed) {
            super.onConfigure();
        }
    }
}
