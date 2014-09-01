package dmdweb.component.button;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * This button overlays the form with a semi-transparent area, to indicate that
 * there is an ongoing action an to avoid multiple submissions in the form.
 * 
 * Warning: you will override mostly onSubmit() and onError(). If you do so, you
 * have to call the super methods implemented here, else the indicator area will
 * not close on action callback.
 * 
 * @author blaz02
 */
public abstract class DMDFormOverlayAjaxButton extends AjaxButton {
    private static final long serialVersionUID = 1L;

    public DMDFormOverlayAjaxButton(final String id) {
        this(id, null, null);
    }

    public DMDFormOverlayAjaxButton(final String id, final IModel<String> model) {
        this(id, model, null);
    }

    public DMDFormOverlayAjaxButton(final String id, final Form<?> form) {
        this(id, null, form);
    }

    public DMDFormOverlayAjaxButton(final String id, final IModel<String> model, final Form<?> form) {
        super(id, model, form);
        add(new DMDOnClickIndicatorAttributeModifier(this));
        add(new DefaultButtonBehavior());
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        target.appendJavaScript(DMDOnClickIndicatorAttributeModifier.getBlockerRemoveScript());
        super.onSubmit(target, form);
    }

    @Override
    protected void onError(AjaxRequestTarget target, Form<?> form) {
        target.appendJavaScript(DMDOnClickIndicatorAttributeModifier.getBlockerRemoveScript());
        super.onError(target, form);
    }
}
