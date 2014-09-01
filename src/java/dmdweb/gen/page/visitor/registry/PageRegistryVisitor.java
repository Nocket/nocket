package dmdweb.gen.page.visitor.registry;

import gengui.domain.DomainObjectReference;

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
import dmdweb.gen.page.visitor.AbstractPageElementVisitor;

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
