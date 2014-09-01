package org.nocket.gen.page.visitor.bind.builder.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.util.convert.converter.DateConverter;

public final class YearCorrectingDateConverter extends DateConverter {

    private static final long serialVersionUID = 1L;
    protected final String datePattern;
    protected final boolean useCorrection;
    protected final String yyDatePatterForCorrection;

    public YearCorrectingDateConverter(String datePattern) {
        super();
        this.datePattern = datePattern;

        useCorrection = StringUtils.contains(datePattern, "yyyy");
        yyDatePatterForCorrection = StringUtils.replace(datePattern, "yyyy", "yy");
    }

    @Override
    public Date convertToObject(final String value, final Locale locale)
    {
        Date result = super.convertToObject(value, locale);
        if (result != null && useCorrection) {

            Calendar firstYyyDate = Calendar.getInstance();
            firstYyyDate.set(100, 0, 1);
            if (result.before(firstYyyDate.getTime())) {
                result = parse(getDateFormat(locale, yyDatePatterForCorrection), value, locale);
            }
        }
        return result;
    }

    /**
     * @see org.apache.wicket.util.convert.converter.DateConverter#getDateFormat(java.util.Locale)
     */
    @Override
    public DateFormat getDateFormat(Locale locale) {
        return getDateFormat(locale, datePattern);
    }

    public DateFormat getDateFormat(Locale locale, String pattern) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        // CHECKSTYLE_OFF Verwende FastDateFormat stattdessen!
        return new SimpleDateFormat(pattern, locale);
        // CHECKSTYLE_ON Verwende FastDateFormat stattdessen!
    }
}