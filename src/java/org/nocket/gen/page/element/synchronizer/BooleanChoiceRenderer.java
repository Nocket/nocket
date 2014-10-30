package org.nocket.gen.page.element.synchronizer;

import org.apache.commons.lang.BooleanUtils;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.PageElementI;

// TODO: Auto-generated Javadoc
/**
 * The Class BooleanChoiceRenderer.
 */
public class BooleanChoiceRenderer implements IChoiceRenderer<Boolean> {

    /** The context. */
    private final DMDWebGenPageContext context;
    
    /** The wicket id. */
    private final String wicketId;

    /**
     * Instantiates a new boolean choice renderer.
     *
     * @param element the element
     */
    public BooleanChoiceRenderer(PageElementI<?> element) {
        this.context = element.getContext();
        this.wicketId = element.getWicketId();
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getDisplayValue(java.lang.Object)
     */
    @Override
    public Object getDisplayValue(Boolean object) {
        String property = wicketId + ".radio." + object;
        //no default value to make this property mandatory
        String displayValue = context.getPage().getString(property);
        return displayValue;
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getIdValue(java.lang.Object, int)
     */
    @Override
    public String getIdValue(Boolean object, int index) {
        return String.valueOf(BooleanUtils.isTrue(object));
    }

}
