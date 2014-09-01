package org.nocket.gen.page.visitor;

import gengui.domain.DomainObjectReference;
import gengui.guiadapter.ElementNotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.nocket.component.table.GenericDataTablePanel;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.page.GeneratedBinding;
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
import org.nocket.gen.page.visitor.bind.BindingVisitor;
import org.nocket.gen.page.visitor.bind.builder.InterceptingBindingBuilder;
import org.nocket.gen.page.visitor.registry.PageRegistryVisitor;

public class GeneratedBindingVisitor extends AbstractPageElementVisitor {

    private final List<PageElementVisitorI> visitors = new ArrayList<PageElementVisitorI>();

    public GeneratedBindingVisitor(GeneratedBinding parent) {
        super(parent.getContext());
        visitors.add(new BindingVisitor(parent.getContext(), new InterceptingBindingBuilder(parent.getInterceptors())));
        visitors.add(new PageRegistryVisitor(parent.getContext()));
    }

    @Override
    public void visitModal(ModalElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitFeedback(FeedbackElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitForm(FormElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitTextInput(TextInputElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitPasswordInput(PasswordInputElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitFileInput(FileInputElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitFileDownload(FileDownloadElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitTextArea(TextAreaElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitLabel(LabelElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitPrompt(PromptElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitCheckboxInput(CheckboxInputElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitRadioInput(RadioInputElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitSelect(SelectElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitImage(ImageElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitLink(LinkElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitTable(TableElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitButton(ButtonElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitContainerOpen(ContainerElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitContainerClose() {
        for (PageElementVisitorI visitor : visitors) {
            visitor.visitContainerClose();
        }
    }

    @Override
    public void visitUnknown(UnknownPageElementI<?> e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void finish(List<DomainElementI<DomainObjectReference>> unboundDomainElements)
            throws ElementNotFoundException {
        for (PageElementVisitorI visitor : visitors) {
            visitor.finish(unboundDomainElements);
        }
        htmlizeTableHeaders(getContext().getPage());
        if (unboundDomainElements.size() > 0) {
            throw new ElementNotFoundException(unboundDomainElements.get(0).getWicketId());
        }
    }

    protected void htmlizeTableHeaders(MarkupContainer page) {
        page.visitChildren(GenericDataTablePanel.class, new IVisitor<GenericDataTablePanel<?>, Component>() {
            @Override
            public void component(GenericDataTablePanel<?> table, IVisit<Component> visit) {
                table.htmlizeTableHeaders();
            }
        });
    }

    @Override
    public void visitRepeatingPanel(RepeatingPanelElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitHeaderLink(HeaderLinkElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitHeaderScript(HeaderScriptElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitBody(BodyElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitGroupTabbedPanel(GroupTabbedPanelElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }

    @Override
    public void visitDiv(DivElement e) {
        for (PageElementVisitorI visitor : visitors) {
            e.accept(visitor);
        }
    }
}
