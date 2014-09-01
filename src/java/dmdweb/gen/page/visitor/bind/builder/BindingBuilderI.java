package dmdweb.gen.page.visitor.bind.builder;

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
import dmdweb.gen.page.element.PasswordInputElement;
import dmdweb.gen.page.element.PromptElement;
import dmdweb.gen.page.element.RadioInputElement;
import dmdweb.gen.page.element.RepeatingPanelElement;
import dmdweb.gen.page.element.SelectElement;
import dmdweb.gen.page.element.TableElement;
import dmdweb.gen.page.element.TextAreaElement;
import dmdweb.gen.page.element.TextInputElement;
import dmdweb.gen.page.element.UnknownPageElementI;

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
