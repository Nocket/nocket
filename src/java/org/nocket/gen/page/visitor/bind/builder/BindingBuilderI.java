package org.nocket.gen.page.visitor.bind.builder;

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
import org.nocket.gen.page.element.PasswordInputElement;
import org.nocket.gen.page.element.PromptElement;
import org.nocket.gen.page.element.RadioInputElement;
import org.nocket.gen.page.element.RepeatingPanelElement;
import org.nocket.gen.page.element.SelectElement;
import org.nocket.gen.page.element.TableElement;
import org.nocket.gen.page.element.TextAreaElement;
import org.nocket.gen.page.element.TextInputElement;
import org.nocket.gen.page.element.UnknownPageElementI;

public interface BindingBuilderI {

    Component createModal(ModalElement e);

    Component createFeedback(FeedbackElement e);

    Component createForm(FormElement e);

    Component createLabel(LabelElement e);

    Component createPrompt(PromptElement p);

    Component createTextInput(TextInputElement e);

    Component createTextArea(TextAreaElement e);

    Component createCheckboxInput(CheckboxInputElement e);

    Component createRadioInput(RadioInputElement e);

    Component createSelect(SelectElement e);

    Component createTable(TableElement e);

    Component createButton(ButtonElement e);

    Component createUnknown(UnknownPageElementI<?> e);

    Component createListView(RepeatingPanelElement e);

    Component createFileInput(FileInputElement e);

    Component createFileDownload(FileDownloadElement e);

    Component createPasswordInput(PasswordInputElement e);

    Component createImage(ImageElement e);

    Component createLink(LinkElement e);

    Component createGroupTabbedPanel(GroupTabbedPanelElement e);

    Component createDiv(DivElement e);

}
