package dmdweb.gen.page.visitor.bind.builder.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.DateConverter;

public class SimpleDateConverter extends DateConverter {
    protected String simpleFormat;

    public SimpleDateConverter(String simpleFormat) {
        this.simpleFormat = simpleFormat;
    }

    @Override
    public DateFormat getDateFormat(Locale locale) {
        return new SimpleDateFormat(simpleFormat, locale);
    }

}
