package org.nocket.gen.page.visitor;

import gengui.domain.DomainObjectReference;
import gengui.guiadapter.ElementNotFoundException;

import java.util.List;

import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.BodyElement;
import org.nocket.gen.page.element.ButtonElement;
import org.nocket.gen.page.element.CheckboxInputElement;
import org.nocket.gen.page.element.ContainerElement;
import org.nocket.gen.page.element.DivElement;
import org.nocket.gen.page.element.FeedbackElement;
import org.nocket.gen.page.element.FileDownloadElement;
import org.nocket.gen.page.element.FileInputElement;
import org.nocket.gen.page.element.FormElement;
import org.nocket.gen.page.element.GroupTabbedPanelElement;
import org.nocket.gen.page.element.HeaderLinkElement;
import org.nocket.gen.page.element.HeaderScriptElement;
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
