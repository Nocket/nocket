package org.nocket.component.table;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.nocket.component.table.columns.AjaxSubmitActionIconColumn;
import org.nocket.gen.page.element.synchronizer.TableButtonCallback;

public class GeneratedAjaxSubmitActionIconColumn<T> extends AjaxSubmitActionIconColumn<T> {

    protected final TableButtonCallback tableButton;
    protected final Form<?> form;
    protected transient Component headerComponent;

    public GeneratedAjaxSubmitActionIconColumn(IModel<String> headerLabel, Form form,
            String enabledIconPropertyKey, String disabledIconPropertyKey, TableButtonCallback tableButton) {
        super(headerLabel, form, enabledIconPropertyKey, disabledIconPropertyKey);
        this.tableButton = tableButton;
        this.form = form;
    }

    @Override
    protected void onSubmit(IModel<T> model, AjaxRequestTarget target) {
        tableButton.onSubmit(target, model);
        tableButton.updateAllForms(target);
    }

    @Override
    protected void onError(IModel<T> model, AjaxRequestTarget target) {
        tableButton.updateAllForms(target);
    }

    @Override
    protected boolean isEnabled(IModel<T> model) {
        return tableButton.isEnabled(model.getObject());
    }

    @Override
    protected boolean isVisible(IModel<T> model) {
        return true;
    }

    @Override
    public boolean isForced(IModel<T> model) {
        return tableButton.isForced(model.getObject());
    }

    @Override
    protected String getTooltip(IModel<T> model) {
        return tableButton.getTooltip(model.getObject());
    }

    @Override
    protected String getIconResourceName(IModel<T> model, Component c) {
        return tableButton.getIconResourceName(model.getObject());
    }

    @Override
    public Component getHeader(String componentId) {
        if (headerComponent == null) {
            headerComponent = super.getHeader(componentId);

        }
        return headerComponent;
    }

}
