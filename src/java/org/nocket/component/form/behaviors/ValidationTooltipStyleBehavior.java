package org.nocket.component.form.behaviors;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.transformer.AbstractTransformerBehavior;

// TODO: Auto-generated Javadoc
/**
 * Zeigt ein ?-Image hinter dem fehlerhaften Eingabefeld an. Das Image hat einen
 * Tooltip, der die Fehlermeldung enthält.
 * 
 * @author meis026
 * 
 */
public class ValidationTooltipStyleBehavior extends AbstractTransformerBehavior {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new validation tooltip style behavior.
     */
    public ValidationTooltipStyleBehavior() {
        super();
    }

    /**
     * Instantiates a new validation tooltip style behavior.
     *
     * @param irgendeinWert the irgendein wert
     */
    public ValidationTooltipStyleBehavior(boolean irgendeinWert) {
        super();
        // Mir ist vollkommen unklar, wafür der Paramter ist. Sinn macht er für mich nicht. Aber er wird aus dem Generator mit boolean aufgerufen
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

    /**
     * Gets the wicket id for error span.
     *
     * @param component the component
     * @return the wicket id for error span
     */
    protected String getWicketIdForErrorSpan(Component component) {
        return component.getId() + "Error";
    }

    /**
     * Gets the wicket id for error span as j query selector.
     *
     * @param component the component
     * @return the wicket id for error span as j query selector
     */
    protected String getWicketIdForErrorSpanAsJQuerySelector(Component component) {
        return StringUtils.replace(component.getId(), ".", "\\\\.") + "Error";
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.behavior.Behavior#onConfigure(org.apache.wicket.Component)
     */
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
