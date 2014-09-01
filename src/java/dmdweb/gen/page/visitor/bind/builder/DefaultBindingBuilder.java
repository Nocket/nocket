package dmdweb.gen.page.visitor.bind.builder;

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

import dmdweb.component.form.DMDTextField;
import dmdweb.component.link.DMDResourceLink;
import dmdweb.component.modal.DMDModalWindow;
import dmdweb.component.panel.DMDFeedbackPanel;
import dmdweb.component.select.DMDListMultipleChoice;
import dmdweb.gen.page.element.ButtonElement;
import dmdweb.gen.page.element.CheckboxInputElement;
import dmdweb.gen.page.element.DivElement;
import dmdweb.gen.page.element.FeedbackElement;
import dmdweb.gen.page.element.FileDownloadElement;
import dmdweb.gen.page.element.FileInputElement;
import dmdweb.gen.page.element.FormElement;
import dmdweb.gen.page.element.GroupTabbedPanelElement;
import dmdweb.gen.page.element.ImageElement;
import dmdweb.gen.page.element.LabelElement;
import dmdweb.gen.page.element.LinkElement;
import dmdweb.gen.page.element.ModalElement;
import dmdweb.gen.page.element.PasswordInputElement;
import dmdweb.gen.page.element.PromptElement;
import dmdweb.gen.page.element.RadioInputElement;
import dmdweb.gen.page.element.RepeatingPanelElement;
import dmdweb.gen.page.element.SelectElement;
import dmdweb.gen.page.element.TableElement;
import dmdweb.gen.page.element.TextAreaElement;
import dmdweb.gen.page.element.TextInputElement;
import dmdweb.gen.page.element.UnknownPageElementI;
import dmdweb.gen.page.element.synchronizer.ArrayToCollectionModelWrapper;
import dmdweb.gen.page.visitor.bind.builder.components.GeneratedBeanValidationForm;
import dmdweb.gen.page.visitor.bind.builder.components.GeneratedButton;
import dmdweb.gen.page.visitor.bind.builder.components.GeneratedDateTextField;
import dmdweb.gen.page.visitor.bind.builder.components.GeneratedGenericDataTableFactory;
import dmdweb.gen.page.visitor.bind.builder.components.GeneratedGroupTabbedPanel;
import dmdweb.gen.page.visitor.bind.builder.components.GeneratedNumberTextField;
import dmdweb.gen.page.visitor.bind.builder.components.GeneratedRadioChoice;
import dmdweb.gen.page.visitor.bind.builder.components.GeneratedRepeatingPanel;

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
