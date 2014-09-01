package org.nocket.gen.page.visitor.registry;

import gengui.domain.DomainObjectReference;

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
import org.nocket.gen.page.visitor.AbstractPageElementVisitor;

public class PageRegistryVisitor extends AbstractPageElementVisitor {

    public PageRegistryVisitor(DMDWebGenPageContext context) {
        super(context);
    }

    private void add(PageElementI<?> e) {
        getContext().getPageRegistry().addElement(e);
    }

    @Override
    public void visitModal(ModalElement e) {
        add(e);
    }

    @Override
    public void visitFeedback(FeedbackElement e) {
        add(e);
    }

    @Override
    public void visitForm(FormElement e) {
        add(e);
    }

    @Override
    public void visitTextInput(TextInputElement e) {
        add(e);
    }

    @Override
    public void visitPasswordInput(PasswordInputElement e) {
        add(e);
    }

    @Override
    public void visitFileInput(FileInputElement e) {
        add(e);
    }

    @Override
    public void visitFileDownload(FileDownloadElement e) {
        add(e);
    }

    @Override
    public void visitTextArea(TextAreaElement e) {
        add(e);
    }

    @Override
    public void visitLabel(LabelElement e) {
        add(e);
    }

    @Override
    public void visitPrompt(PromptElement e) {
        add(e);
    }

    @Override
    public void visitCheckboxInput(CheckboxInputElement e) {
        add(e);
    }

    @Override
    public void visitRadioInput(RadioInputElement e) {
        add(e);
    }

    @Override
    public void visitSelect(SelectElement e) {
        add(e);
    }

    @Override
    public void visitImage(ImageElement e) {
        add(e);
    }

    @Override
    public void visitLink(LinkElement e) {
        add(e);
    }

    @Override
    public void visitTable(TableElement e) {
        add(e);
    }

    @Override
    public void visitButton(ButtonElement e) {
        add(e);
    }

    @Override
    public void visitContainerOpen(ContainerElement e) {
    }

    @Override
    public void visitContainerClose() {
    }

    @Override
    public void visitUnknown(UnknownPageElementI<?> e) {
        add(e);
    }

    @Override
    public void finish(List<DomainElementI<DomainObjectReference>> unboundDomainElements) {
    }

    @Override
    public void visitRepeatingPanel(RepeatingPanelElement e) {
        add(e);
    }

    @Override
    public void visitHeaderLink(HeaderLinkElement e) {
    }

    @Override
    public void visitHeaderScript(HeaderScriptElement e) {
    }

    @Override
    public void visitBody(BodyElement bodyElement) {
        getContext().getPageRegistry().setBodyElement(bodyElement);
    }

    @Override
    public void visitGroupTabbedPanel(GroupTabbedPanelElement e) {
        add(e);
    }

    @Override
    public void visitDiv(DivElement e) {
        add(e);
    }
}
