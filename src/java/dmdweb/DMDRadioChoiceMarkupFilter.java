package dmdweb;

import java.text.ParseException;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupElement;
import org.apache.wicket.markup.parser.AbstractMarkupFilter;

public class DMDRadioChoiceMarkupFilter extends AbstractMarkupFilter {
    private static final String RADIO = "radio";
    private static final String INPUT = "input";
    private static final String SPAN = "span";
    private static final String TYPE = "type";

    @Override
    protected MarkupElement onComponentTag(ComponentTag tag) throws ParseException {
        if (isDMDRadioChoice(tag)) {
            tag.getAttributes().remove(TYPE);
            tag.setName(SPAN);
        }
        return tag;
    }

    private boolean isDMDRadioChoice(ComponentTag tag) {
        try {
            if (INPUT.equals(tag.getName()) && tag.getAttributes().containsKey(TYPE)
                    && tag.getAttribute(TYPE).trim().toLowerCase().equals(RADIO)) {
                return true;
            }
        } catch (RuntimeException e) {
        }
        return false;
    }
}