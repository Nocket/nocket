package org.nocket.component.form.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.transformer.AbstractTransformerBehavior;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * This behavior sets a special css class on the component tag in case it is not
 * valid and if desired adds paragraph with an error message right behind the
 * component.
 * 
 * @auto blaz02
 * 
 */
public class ValidationStyleBehavior extends AbstractTransformerBehavior {

    private static final long serialVersionUID = 1L;

    boolean showInlineError;

    public ValidationStyleBehavior() {
        this(true);
    }

    public ValidationStyleBehavior(boolean showInlineError) {
        super();
        this.showInlineError = showInlineError;
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        FormComponent<?> c = (FormComponent<?>) component;
        if (!c.isValid()) {
            tag.append("class", "error", " ");
        }
    }

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

    protected String getWicketIdForErrorSpan(Component component) {
        return component.getId() + "Error";
    }

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
