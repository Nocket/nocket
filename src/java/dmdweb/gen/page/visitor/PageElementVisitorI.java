package dmdweb.gen.page.visitor;

import gengui.domain.DomainObjectReference;
import gengui.guiadapter.ElementNotFoundException;

import java.util.List;

import dmdweb.gen.domain.element.DomainElementI;
import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.element.BodyElement;
import dmdweb.gen.page.element.ButtonElement;
import dmdweb.gen.page.element.CheckboxInputElement;
import dmdweb.gen.page.element.ContainerElement;
import dmdweb.gen.page.element.DivElement;
import dmdweb.gen.page.element.FeedbackElement;
import dmdweb.gen.page.element.FileDownloadElement;
import dmdweb.gen.page.element.FileInputElement;
import dmdweb.gen.page.element.FormElement;
import dmdweb.gen.page.element.GroupTabbedPanelElement;
import dmdweb.gen.page.element.HeaderLinkElement;
import dmdweb.gen.page.element.HeaderScriptElement;
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

public interface PageElementVisitorI {

    DMDWebGenPageContext getContext();

    void visitModal(ModalElement e);

    void visitFeedback(FeedbackElement e);

    void visitForm(FormElement e);

    void visitTextInput(TextInputElement e);

    void visitFileInput(FileInputElement e);

    void visitTextArea(TextAreaElement e);

    void visitLabel(LabelElement e);

    void visitPrompt(PromptElement e);

    void visitCheckboxInput(CheckboxInputElement e);

    void visitImage(ImageElement e);

    void visitRadioInput(RadioInputElement e);

    void visitSelect(SelectElement e);

    void visitTable(TableElement e);

    void visitButton(ButtonElement e);

    void visitContainerOpen(ContainerElement e);

    void visitContainerClose();

    void finish(List<DomainElementI<DomainObjectReference>> unboundDomainElements) throws ElementNotFoundException;

    void visitUnknown(UnknownPageElementI<?> e);

    void visitRepeatingPanel(RepeatingPanelElement repeatingPanelElement);

    void visitFileDownload(FileDownloadElement fileDownloadElement);

    void visitPasswordInput(PasswordInputElement passwordInputElement);

    void visitLink(LinkElement linkElement);

    void visitHeaderLink(HeaderLinkElement headerLinkElement);

    void visitHeaderScript(HeaderScriptElement headerScriptElement);

    void visitBody(BodyElement bodyElement);

    void visitGroupTabbedPanel(GroupTabbedPanelElement groupTabbedPanelElement);

    void visitDiv(DivElement divElement);
}
