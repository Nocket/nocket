package org.nocket.test.widgets.choices;

import java.util.Collection;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.nocket.component.form.BeanValidationForm;
import org.nocket.component.select.DMDDropDownChoice;
import org.nocket.component.select.DMDListMultipleChoice;
import org.nocket.test.page.BrowserTestsPage;

@SuppressWarnings("serial")
public class ChoicesTestsPage extends BrowserTestsPage {

    public ChoicesTestsPage() {
        this(Model.of(new ChoicesTestsPageModel()));
    }

    public ChoicesTestsPage(IModel<ChoicesTestsPageModel> model) {
        super(model);
        final BeanValidationForm<ChoicesTestsPageModel> form = new BeanValidationForm<ChoicesTestsPageModel>("form",
                new CompoundPropertyModel<ChoicesTestsPageModel>(model));
        final DMDListMultipleChoice<DayListItem> dmdListMultipleChoice = new DMDListMultipleChoice<DayListItem>(
                "multi.days", new PropertyModel<Collection<DayListItem>>(model.getObject(), "multiDays"),
                model.getObject().getAllDays(), new DayListItem.ChoiceRenderer());
        dmdListMultipleChoice.setOutputMarkupId(true);
        form.add(dmdListMultipleChoice);

        final DMDDropDownChoice<DayListItem> dmdDropDownChoice = new DMDDropDownChoice<DayListItem>(
                "one.day", new PropertyModel<DayListItem>(model.getObject(), "oneDay"),
                model.getObject().getAllDays(), new DayListItem.ChoiceRenderer());
        dmdDropDownChoice.setOutputMarkupId(true);
        form.add(dmdDropDownChoice);

        final ListMultipleChoice<DayListItem> listMultipleChoice = new ListMultipleChoice<DayListItem>(
                "multiDaysClassic", model.getObject().getAllDays());
        listMultipleChoice.setOutputMarkupId(true);
        form.add(listMultipleChoice);

        form.add(new AjaxButton("checkit") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                dmdListMultipleChoice.setEnabled(!dmdListMultipleChoice.isEnabled());
                dmdDropDownChoice.setEnabled(!dmdDropDownChoice.isEnabled());
                listMultipleChoice.setEnabled(!listMultipleChoice.isEnabled());
                target.add(dmdListMultipleChoice);
                target.add(dmdDropDownChoice);
                target.add(listMultipleChoice);
            }

        });
        form.setOutputMarkupId(true);
        add(form);
    }
}
