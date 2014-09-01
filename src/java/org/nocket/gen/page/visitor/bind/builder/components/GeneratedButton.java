package org.nocket.gen.page.visitor.bind.builder.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.nocket.component.button.DMDFormOverlayAjaxButton;
import org.nocket.gen.i18n.I18NLabelModelFactory;
import org.nocket.gen.page.element.ButtonElement;
import org.nocket.gen.page.element.synchronizer.ButtonCallback;
import org.nocket.gen.page.element.synchronizer.SynchronizerHelper;
import org.nocket.gen.page.element.synchronizer.ButtonCallback.ButtonCallbackInterceptor;

@SuppressWarnings("serial")
public class GeneratedButton extends DMDFormOverlayAjaxButton {

    private ButtonCallback callback;
    protected final SynchronizerHelper helper;

    public GeneratedButton(ButtonElement e) {
        this(e, null);
    }

    public GeneratedButton(ButtonElement e, ButtonCallbackInterceptor buttonCallbackInterceptor) {
        super(e.getWicketId());
        add(new Label(e.getWicketId() + ".label", I18NLabelModelFactory.createLabelModel(e)));
        this.callback = new ButtonCallback(e, buttonCallbackInterceptor);
        helper = new SynchronizerHelper(e);
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        callback.onSubmit(target);
        super.onSubmit(target, form);
        callback.updateAllForms(target);
    }

    @Override
    protected void onError(AjaxRequestTarget target, Form<?> form) {
        if (callback.isForced()) {
            // The following lines cause the same behavior for forced operations as in gengui:
            // The input of all *valid* fields as synchronized to the models
            // The input of all invalid fields is erased and replaced by the model values
            SynchronizerHelper.synchronizeModelsForValidInput(form);
            form.clearInput();
            //form.getSession().cleanupFeedbackMessages();
            callback.onSubmit(target);
        }
        super.onError(target, form);
        callback.updateAllForms(target);
    }

    @Override
    public FormComponent<String> setLabel(IModel<String> labelModel) {
        /*
         * Hier wird die Standard-Methode zum Setzen eines Labels ein bisschen
         * verbogen. Da wir beim GeneratedButton ein eigenes Label hinzufügen.
         * Muss hier das entsprechende Label und nicht das internalLabel einer
         * FormComponent geändert werden.
         */
        Label label = (Label) this.get(this.getId() + ".label");
        label.setDefaultModel(labelModel);
        return this;
    }

    /**
     * Checks whether the state of the button (visible -> invisible, enabled ->
     * disabled) has changed between the request and the response in order to
     * decide if the button should be part of the Ajax update
     */
    public boolean hasButtonStateChanged() {
        if (isEnabled() != helper.isEnabled()) {
            return true;
        }

        if (isVisible() && helper.getHiderMethod() != null) {
            return true;
        }

        return false;
    }

}
