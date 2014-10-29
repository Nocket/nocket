package org.nocket.component.form.behaviors;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.transformer.AbstractTransformerBehavior;

/**
 * Zeigt ein ?-Image hinter dem fehlerhaften Eingabefeld an. Das Image hat einen
 * Tooltip, der die Fehlermeldung enthält.
 * 
 * @auto meis026
 * 
 */
public class ValidationTooltipStyleBehavior extends AbstractTransformerBehavior {

    private static final long serialVersionUID = 1L;

    public ValidationTooltipStyleBehavior() {
        super();
    }

    public ValidationTooltipStyleBehavior(boolean irgendeinWert) {
        super();
        // Mir ist vollkommen unklar, wafür der Paramter ist. Sinn macht er für mich nicht. Aber er wird aus dem Generator mit boolean aufgerufen
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

        String span = "";
        if (!fc.isValid()) {
            for (FeedbackMessage message : fc.getFeedbackMessages()) {
                message.markRendered();
                String id = getWicketIdForErrorSpan(component);
                span = "<span id=\"" + id + "\" class=\"error-icon\">" + "<i class=\"" +
                        ValidationTooltipStyleGroupBehavior.VALIDATION_ERROR_ICON_CLASS +
                        "\" title = \""
                        + message.getMessage() + "\"></i></span>";
            }
        }
        res = cs + span;

        return res;
    }

    protected String getWicketIdForErrorSpan(Component component) {
        return component.getId() + "Error";
    }

    protected String getWicketIdForErrorSpanAsJQuerySelector(Component component) {
        return StringUtils.replace(component.getId(), ".", "\\\\.") + "Error";
    }

    @Override
    public void onConfigure(Component component) {
        super.onConfigure(component);
        AjaxRequestTarget ajaxRequestTarget = component.getRequestCycle().find(AjaxRequestTarget.class);
        if (ajaxRequestTarget != null) {
            ajaxRequestTarget.prependJavaScript("if ($('#" + getWicketIdForErrorSpanAsJQuerySelector(component)
                    + "')) $('#" + getWicketIdForErrorSpanAsJQuerySelector(component)
                    + "').remove();");
        }
    }
}
