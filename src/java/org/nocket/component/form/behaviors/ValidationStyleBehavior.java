package org.nocket.component.form.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.transformer.AbstractTransformerBehavior;
import org.apache.wicket.request.cycle.RequestCycle;

// TODO: Auto-generated Javadoc
/**
 * This behavior sets a special css class on the component tag in case it is not
 * valid and if desired adds paragraph with an error message right behind the
 * component.
 * 
 * @author blaz02
 * 
 */
public class ValidationStyleBehavior extends AbstractTransformerBehavior {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The show inline error. */
    boolean showInlineError;

    /**
     * Instantiates a new validation style behavior.
     */
    public ValidationStyleBehavior() {
        this(true);
    }

    /**
     * Instantiates a new validation style behavior.
     *
     * @param showInlineError the show inline error
     */
    public ValidationStyleBehavior(boolean showInlineError) {
        super();
        this.showInlineError = showInlineError;
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.behavior.Behavior#onComponentTag(org.apache.wicket.Component, org.apache.wicket.markup.ComponentTag)
     */
    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        FormComponent<?> c = (FormComponent<?>) component;
        if (!c.isValid()) {
            tag.append("class", "error", " ");
        }
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.markup.transformer.AbstractTransformerBehavior#transform(org.apache.wicket.Component, java.lang.CharSequence)
     */
    @Override
    public CharSequence transform(Component component, CharSequence cs) {
        CharSequence res = cs;
        FormComponent<?> fc = (FormComponent<?>) component;
        if (!fc.isValid() && showInlineError) {

            for (FeedbackMessage message : fc.getFeedbackMessages()) {
                message.markRendered();
                String id = getWicketIdForErrorSpan(component);
                res = cs + "<p id=\"" + id + "\" class=\"error\">" + message.getMessage() + "</p>";
            }
        }

        return res;
    }

    /**
     * Gets the wicket id for error span.
     *
     * @param component the component
     * @return the wicket id for error span
     */
    protected String getWicketIdForErrorSpan(Component component) {
        return component.getId() + "Error";
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.behavior.Behavior#onConfigure(org.apache.wicket.Component)
     */
    @Override
    public void onConfigure(Component component) {
        super.onConfigure(component);
        AjaxRequestTarget ajaxRequestTarget = RequestCycle.get().find(AjaxRequestTarget.class);
        if (ajaxRequestTarget != null) {
            String wicketIdForErrorSpan = getWicketIdForErrorSpan(component).replace(".", "\\\\.");
            ajaxRequestTarget.prependJavaScript("$('#" + wicketIdForErrorSpan + "').remove();");
        }
    }
}
