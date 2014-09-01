package dmdweb.gen.page.visitor;

import gengui.domain.DomainObjectReference;
import gengui.guiadapter.ElementNotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import dmdweb.component.table.GenericDataTablePanel;
import dmdweb.gen.domain.element.DomainElementI;
import dmdweb.gen.page.GeneratedBinding;
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
import dmdweb.gen.page.visitor.bind.BindingVisitor;
import dmdweb.gen.page.visitor.bind.builder.InterceptingBindingBuilder;
import dmdweb.gen.page.visitor.registry.PageRegistryVisitor;

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
