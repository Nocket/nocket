package org.nocket.gen.page.visitor.bind;

import gengui.domain.DomainObjectReference;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.AbstractPageHeaderElement;
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
import org.nocket.gen.page.element.synchronizer.DomainComponentBehaviour;
import org.nocket.gen.page.visitor.AbstractPageElementVisitor;
import org.nocket.gen.page.visitor.bind.builder.BindingBuilderI;
import org.nocket.gen.page.visitor.bind.builder.components.HeaderContributor;
import org.nocket.gen.page.visitor.bind.builder.components.SQLInjectionValidator;

public class BindingVisitor extends AbstractPageElementVisitor {

    private final BindingBuilderI bindingBuilder;
    private final Deque<MarkupContainer> panelStack;
    private ContainerElement nextContainer;
    private List<AbstractPageHeaderElement> headerElements = new ArrayList<AbstractPageHeaderElement>();

    public BindingVisitor(DMDWebGenPageContext context, BindingBuilderI bindingBuilder) {
        super(context);
        this.bindingBuilder = bindingBuilder;
        this.panelStack = new ArrayDeque<MarkupContainer>();
        this.panelStack.addLast(context.getPage());
    }

    private void addContainer(PageElementI<?> e, MarkupContainer c) {
        panelStack.getLast().add(c);
        if (nextContainer != null) {
            panelStack.addLast(c);
            nextContainer = null;
        }
    }

    private void addComponent(Component c) {
        if (nextContainer != null) {
            throw new IllegalArgumentException("Expected a " + MarkupContainer.class.getSimpleName() + " instead of a "
                    + Component.class.getSimpleName() + " as next element!");
        }
        panelStack.getLast().add(c);
    }

    protected void add(PageElementI<?> e, Component c) {
        // an unknown element is a container but was not bound
        if (c == null) {
            return;
        }
        if (c instanceof FormComponent) {
            c.add(new SQLInjectionValidator());
        }
        // add domain behaviour
        if (e.isDomainElement() && !(e.getDomainElement() instanceof MultivaluePropertyElement)) {
            c.add(new DomainComponentBehaviour(e, c));
        }
        // add normal component
        if (c instanceof MarkupContainer) {
            addContainer(e, (MarkupContainer) c);
        } else {
            addComponent(c);
        }
        getContext().getComponentRegistry().addComponent(c);
    }

    @Override
    public void visitModal(ModalElement e) {
        Component modal = bindingBuilder.createModal(e);
        add(e, modal);
    }

    @Override
    public void visitFeedback(FeedbackElement e) {
        Component feedback = bindingBuilder.createFeedback(e);
        add(e, feedback);
    }

    @Override
    public void visitForm(FormElement e) {
        Component form = bindingBuilder.createForm(e);
        add(e, form);
    }

    @Override
    public void visitLabel(LabelElement e) {
        Component label = bindingBuilder.createLabel(e);
        add(e, label);
    }

    @Override
    public void visitPrompt(PromptElement e) {
        Component label = bindingBuilder.createPrompt(e);
        add(e, label);
    }

    @Override
    public void visitTextInput(TextInputElement e) {
        Component textInput = bindingBuilder.createTextInput(e);
        add(e, textInput);
    }

    @Override
    public void visitPasswordInput(PasswordInputElement e) {
        Component textInput = bindingBuilder.createPasswordInput(e);
        add(e, textInput);
    }

    @Override
    public void visitFileInput(FileInputElement e) {
        Component fileInput = bindingBuilder.createFileInput(e);
        add(e, fileInput);
    }

    @Override
    public void visitFileDownload(FileDownloadElement e) {
        Component fileDownload = bindingBuilder.createFileDownload(e);
        add(e, fileDownload);
    }

    @Override
    public void visitTextArea(TextAreaElement e) {
        Component textArea = bindingBuilder.createTextArea(e);
        add(e, textArea);
    }

    @Override
    public void visitCheckboxInput(CheckboxInputElement e) {
        Component textInput = bindingBuilder.createCheckboxInput(e);
        add(e, textInput);
    }

    @Override
    public void visitRadioInput(RadioInputElement e) {
        Component textInput = bindingBuilder.createRadioInput(e);
        add(e, textInput);
    }

    @Override
    public void visitSelect(SelectElement e) {
        Component select = bindingBuilder.createSelect(e);
        add(e, select);
    }

    @Override
    public void visitImage(ImageElement e) {
        Component image = bindingBuilder.createImage(e);
        add(e, image);
    }

    @Override
    public void visitLink(LinkElement e) {
        Component link = bindingBuilder.createLink(e);
        add(e, link);
    }

    @Override
    public void visitTable(TableElement e) {
        Component table = bindingBuilder.createTable(e);
        add(e, table);
    }

    @Override
    public void visitButton(ButtonElement e) {
        Component button = bindingBuilder.createButton(e);
        add(e, button);
    }

    @Override
    public void visitContainerOpen(ContainerElement e) {
        if (nextContainer != null) {
            throw new IllegalStateException("Did not expect a new " + ContainerElement.class.getSimpleName()
                    + " while another is waiting for its element visit!");
        }
        nextContainer = e;
    }

    @Override
    public void visitContainerClose() {
        if (nextContainer != null) {
            nextContainer = null;
        } else if (panelStack.removeLast() == null) {
            throw new IllegalStateException("PanelStack improperly implemented, did not expect a panel to be null!");
        }
    }

    @Override
    public void visitUnknown(UnknownPageElementI<?> e) {
        Component button = bindingBuilder.createUnknown(e);
        add(e, button);
    }

    @Override
    public void finish(List<DomainElementI<DomainObjectReference>> unboundDomainElements) {
        addHeaderContributions(getContext().getPage());
    }

    protected void addHeaderContributions(MarkupContainer page) {
        if (headerElements.size() > 0) {
            HeaderContributor hc = new HeaderContributor();
            for (AbstractPageHeaderElement headerElement : headerElements)
                hc.addContribution(headerElement);
            page.add(hc);
        }
    }

    @Override
    public void visitRepeatingPanel(RepeatingPanelElement e) {
        Component listView = bindingBuilder.createListView(e);
        add(e, listView);
    }

    @Override
    public void visitHeaderLink(HeaderLinkElement headerLinkElement) {
        headerElements.add(headerLinkElement);
    }

    @Override
    public void visitHeaderScript(HeaderScriptElement headerScriptElement) {
        headerElements.add(headerScriptElement);
    }

    @Override
    public void visitBody(BodyElement bodyElement) {
    }

    @Override
    public void visitGroupTabbedPanel(GroupTabbedPanelElement e) {
        Component button = bindingBuilder.createGroupTabbedPanel(e);
        add(e, button);
    }

    @Override
    public void visitDiv(DivElement e) {
        Component div = bindingBuilder.createDiv(e);
        add(e, div);
    }
}
