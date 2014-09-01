package dmdweb.gen.page.visitor.bind.builder.components;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;

import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.element.synchronizer.EnumChoicesRenderer;

public class EnumConverter implements IConverter<Enum> {

    protected EnumChoicesRenderer renderer;

    public EnumConverter(DMDWebGenPageContext context) {
        renderer = new EnumChoicesRenderer(context);
    }

    @Override
    public Enum convertToObject(String value, Locale locale) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String convertToString(Enum value, Locale locale) {
        Object displayValue = renderer.getDisplayValue(value);
        return (displayValue != null) ? displayValue.toString() : null;
    }

}
