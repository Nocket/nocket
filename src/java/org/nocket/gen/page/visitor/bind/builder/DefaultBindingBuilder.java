package org.nocket.gen.page.visitor.bind.builder;

import java.io.File;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.IModel;
import org.nocket.component.form.DMDTextField;
import org.nocket.component.link.DMDResourceLink;
import org.nocket.component.modal.DMDModalWindow;
import org.nocket.component.panel.DMDFeedbackPanel;
import org.nocket.component.select.DMDListMultipleChoice;
import org.nocket.gen.page.element.ButtonElement;
import org.nocket.gen.page.element.CheckboxInputElement;
import org.nocket.gen.page.element.DivElement;
import org.nocket.gen.page.element.FeedbackElement;
import org.nocket.gen.page.element.FileDownloadElement;
import org.nocket.gen.page.element.FileInputElement;
import org.nocket.gen.page.element.FormElement;
import org.nocket.gen.page.element.GroupTabbedPanelElement;
import org.nocket.gen.page.element.ImageElement;
import org.nocket.gen.page.element.LabelElement;
import org.nocket.gen.page.element.LinkElement;
import org.nocket.gen.page.element.ModalElement;
import org.nocket.gen.page.element.PasswordInputElement;
import org.nocket.gen.page.element.PromptElement;
import org.nocket.gen.page.element.RadioInputElement;
import org.nocket.gen.page.element.RepeatingPanelElement;
import org.nocket.gen.page.element.SelectElement;
import org.nocket.gen.page.element.TableElement;
import org.nocket.gen.page.element.TextAreaElement;
import org.nocket.gen.page.element.TextInputElement;
import org.nocket.gen.page.element.UnknownPageElementI;
import org.nocket.gen.page.element.synchronizer.ArrayToCollectionModelWrapper;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedBeanValidationForm;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedButton;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedDateTextField;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedGenericDataTableFactory;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedGroupTabbedPanel;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedNumberTextField;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedRadioChoice;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedRepeatingPanel;

public class DefaultBindingBuilder implements BindingBuilderI {

    @Override
    public Component createModal(ModalElement e) {
        DMDModalWindow modal = new DMDModalWindow(e.getWicketId());
        return modal;
    }

    @Override
    public Component createFeedback(FeedbackElement e) {
        DMDFeedbackPanel feedback = new DMDFeedbackPanel(e.getWicketId());
        feedback.setOutputMarkupId(true);
        return feedback;
    }

    @Override
    public Component createForm(FormElement e) {
        GeneratedBeanValidationForm<Object> form = new GeneratedBeanValidationForm<Object>(e.getContext(),
                e.getWicketId(), e.getModel());
        form.setOutputMarkupId(true);
        return form;
    }

    @Override
    public Component createLabel(LabelElement e) {
        Label label = new Label(e.getWicketId(), e.getModel());
        return label;
    }

    @Override
    public Component createPrompt(PromptElement e) {
        Label label = new Label(e.getWicketId(), e.getModel());
        label.setEscapeModelStrings(false);
        return label;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Component createTextInput(TextInputElement e) {
        if (e.getDomainElement().isNumberType()) {
            GeneratedNumberTextField textInput = new GeneratedNumberTextField(e);
            textInput.postInit(e);
            return textInput;
        } else if (e.getDomainElement().isDateType()) {
            GeneratedDateTextField textInput = new GeneratedDateTextField(e);
            textInput.postInit(e);
            return textInput;
        } else {
            DMDTextField<Object> textInput = new DMDTextField<Object>(e.getWicketId(), e.getModel());
            return textInput;
        }
    }

    @Override
    public Component createFileInput(FileInputElement e) {
        FileUploadField fileInput = new FileUploadField(e.getWicketId(), (IModel) e.getModel());
        return fileInput;
    }

    @Override
    public Component createPasswordInput(PasswordInputElement e) {
        return new PasswordTextField(e.getWicketId(), (IModel) e.getModel());
    }

    @Override
    public Component createFileDownload(FileDownloadElement e) {
        IModel<File> model = e.getModel();
        DownloadLink downloadLink = new DownloadLink(e.getWicketId(), model);
        return downloadLink;
    }

    @Override
    public Component createTextArea(TextAreaElement e) {
        // This is still a bit simple. As a difference to text input fields, this implementation
        // assumes that text areas are used for text only and not for dates or numbers.
        TextArea<Object> textArea = new TextArea<Object>(e.getWicketId(), e.getModel());
        return textArea;
    }

    @Override
    public Component createCheckboxInput(CheckboxInputElement e) {
        CheckBox checkboxInput = new CheckBox(e.getWicketId(), e.getModel());
        return checkboxInput;
    }

    @Override
    public Component createRadioInput(RadioInputElement e) {
        GeneratedRadioChoice radioInput = new GeneratedRadioChoice(e);
        return radioInput;
    }

    @Override
    public Component createImage(ImageElement e) {
        IModel model = e.getModel();
        ContextImage image = new ContextImage(e.getWicketId(), model);
        return image;
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Component createLink(LinkElement e) {
        Component component;
        if (e.isResourceLink()) {
            component = new DMDResourceLink(e.getWicketId(), (IModel<?>) e.getModel());
        } else {
            component = new ExternalLink(e.getWicketId(), e.getModel());
        }
        return component;
    }

    @Override
    public Component createSelect(SelectElement e) {
        if (!e.isMultiselect()) {
            DropDownChoice<Object> select = new DropDownChoice<Object>(e.getWicketId(), e.getModel(),
                    e.getChoicesModel(), e.getChoicesRenderer());
            return select;
        } else {
            IModel<?> model = e.getModel();
            Class getterMethodType = e.getDomainElement().getMethod().getReturnType();
            if (getterMethodType.isArray()) {
                model = new ArrayToCollectionModelWrapper(model, getterMethodType.getComponentType());
            }
            boolean compactSelect = (e.getNumberOfVisibleEntries() != null && e.getNumberOfVisibleEntries() == 1);
            ListMultipleChoice list = compactSelect ?
                    new DMDListMultipleChoice(e.getWicketId(), model, e.getChoicesModel()) :
                    new ListMultipleChoice(e.getWicketId(), model, e.getChoicesModel());
            return list;
        }
    }

    @Override
    public Component createTable(TableElement e) {
        return new GeneratedGenericDataTableFactory(e).createTable();
    }

    @Override
    public Component createButton(final ButtonElement e) {
        Button button = new GeneratedButton(e);
        return button;
    }

    @Override
    public Component createUnknown(UnknownPageElementI<?> e) {
        return null;
    }

    @Override
    public Component createListView(RepeatingPanelElement e) {
        return new GeneratedRepeatingPanel(e);
    }

    @Override
    public Component createGroupTabbedPanel(GroupTabbedPanelElement e) {
        GeneratedGroupTabbedPanel generatedGroupTabbedPanel = new GeneratedGroupTabbedPanel(e);
        return generatedGroupTabbedPanel;
    }

    @Override
    public Component createDiv(DivElement e) {
        WebMarkupContainer div = new WebMarkupContainer(e.getWicketId());
        return div;
    }
}
