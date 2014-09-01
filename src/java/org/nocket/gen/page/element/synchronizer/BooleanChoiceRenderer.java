package org.nocket.gen.page.element.synchronizer;

import org.apache.commons.lang.BooleanUtils;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.PageElementI;

public class BooleanChoiceRenderer implements IChoiceRenderer<Boolean> {

    private final DMDWebGenPageContext context;
    private final String wicketId;

    public BooleanChoiceRenderer(PageElementI<?> element) {
        this.context = element.getContext();
        this.wicketId = element.getWicketId();
    }

    @Override
    public Object getDisplayValue(Boolean object) {
        String property = wicketId + ".radio." + object;
        //no default value to make this property mandatory
        String displayValue = context.getPage().getString(property);
        return displayValue;
    }

    @Override
    public String getIdValue(Boolean object, int index) {
        return String.valueOf(BooleanUtils.isTrue(object));
    }

}
