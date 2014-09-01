package org.nocket.gen.page.visitor.bind.builder;

import java.io.Serializable;

import org.apache.wicket.Component;
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
import org.nocket.gen.page.element.PageElementI;
import org.nocket.gen.page.element.PasswordInputElement;
import org.nocket.gen.page.element.PromptElement;
import org.nocket.gen.page.element.RadioInputElement;
import org.nocket.gen.page.element.RepeatingPanelElement;
import org.nocket.gen.page.element.SelectElement;
import org.nocket.gen.page.element.TableElement;
import org.nocket.gen.page.element.TextAreaElement;
import org.nocket.gen.page.element.TextInputElement;
import org.nocket.gen.page.element.UnknownPageElementI;

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
