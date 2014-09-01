package dmdweb.gen.page.visitor.bind.builder;

import java.io.Serializable;

import org.apache.wicket.Component;

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
import dmdweb.gen.page.element.PageElementI;
import dmdweb.gen.page.element.PasswordInputElement;
import dmdweb.gen.page.element.PromptElement;
import dmdweb.gen.page.element.RadioInputElement;
import dmdweb.gen.page.element.RepeatingPanelElement;
import dmdweb.gen.page.element.SelectElement;
import dmdweb.gen.page.element.TableElement;
import dmdweb.gen.page.element.TextAreaElement;
import dmdweb.gen.page.element.TextInputElement;
import dmdweb.gen.page.element.UnknownPageElementI;

/**
 * You can overwrite the create() method to just fetch any component. Or use the
 * specific createX() method to spare you the casting of the element when you
 * need the specific type.
 */
public class BindingInterceptor implements BindingBuilderI, Serializable {

    @Override
    public Component createModal(ModalElement e) {
        return create(e);
    }

    @Override
    public Component createFeedback(FeedbackElement e) {
        return create(e);
    }

    @Override
    public Component createForm(FormElement e) {
        return create(e);
    }

    @Override
    public Component createLabel(LabelElement e) {
        return create(e);
    }

    @Override
    public Component createPrompt(PromptElement e) {
        return create(e);
    }

    @Override
    public Component createTextInput(TextInputElement e) {
        return create(e);
    }

    @Override
    public Component createPasswordInput(PasswordInputElement e) {
        return create(e);
    }

    @Override
    public Component createFileInput(FileInputElement e) {
        return create(e);
    }

    @Override
    public Component createFileDownload(FileDownloadElement e) {
        return create(e);
    }

    @Override
    public Component createTextArea(TextAreaElement e) {
        return create(e);
    }

    @Override
    public Component createCheckboxInput(CheckboxInputElement e) {
        return create(e);
    }

    @Override
    public Component createRadioInput(RadioInputElement e) {
        return create(e);
    }

    @Override
    public Component createSelect(SelectElement e) {
        return create(e);
    }

    @Override
    public Component createImage(ImageElement e) {
        return create(e);
    }

    @Override
    public Component createLink(LinkElement e) {
        return create(e);
    }

    @Override
    public Component createTable(TableElement e) {
        return create(e);
    }

    @Override
    public Component createButton(ButtonElement e) {
        return create(e);
    }

    @Override
    public Component createUnknown(UnknownPageElementI<?> e) {
        return create(e);
    }

    public Component create(PageElementI<?> e) {
        return null;
    }

    @Override
    public Component createListView(RepeatingPanelElement e) {
        return create(e);
    }

    @Override
    public Component createGroupTabbedPanel(GroupTabbedPanelElement e) {
        return create(e);
    }

    @Override
    public Component createDiv(DivElement e) {
        return create(e);
    }
}
