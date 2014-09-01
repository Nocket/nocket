package dmdweb.gen.domain.visitor;

import gengui.domain.AbstractDomainReference;

import java.util.ArrayList;
import java.util.List;

import dmdweb.gen.domain.DMDWebGenContext;
import dmdweb.gen.domain.element.ButtonElement;
import dmdweb.gen.domain.element.CheckboxPropertyElement;
import dmdweb.gen.domain.element.ChoicerPropertyElement;
import dmdweb.gen.domain.element.HeadlineElement;
import dmdweb.gen.domain.element.HiddenPropertyElement;
import dmdweb.gen.domain.element.MultivaluePropertyElement;
import dmdweb.gen.domain.element.ResourceElement;
import dmdweb.gen.domain.element.SimplePropertyElement;
import dmdweb.gen.domain.visitor.constants.ConstantsVisitor;
import dmdweb.gen.domain.visitor.html.DelegateHtmlVisitor;
import dmdweb.gen.domain.visitor.properties.DelegatePropertiesVisitor;

public class DMDWebGenVisitor<E extends AbstractDomainReference> extends AbstractDomainElementVisitor<E> {

    protected final List<DomainElementVisitorI<E>> visitors = new ArrayList<DomainElementVisitorI<E>>();

    public DMDWebGenVisitor(DMDWebGenContext<E> context) {
        super(context);
        addVisitors(context);
    }

    public DMDWebGenVisitor(DMDWebGenContext<E> context, DomainElementVisitorI<E> domainElementVisitor,
            DomainElementVisitorI<E>... domainElementVisitorIs) {
        super(context);
        visitors.add(domainElementVisitor);
        for (int i = 0; domainElementVisitorIs != null && i < domainElementVisitorIs.length; i++) {
            visitors.add(domainElementVisitorIs[i]);
        }
    }

    protected void addVisitors(DMDWebGenContext<E> context) {
        // visitors.add(new PrintVisitor<E>(context));
        visitors.add(new ConstantsVisitor<E>(context));
        visitors.add(new DelegateHtmlVisitor<E>(context));
        visitors.add(new DelegatePropertiesVisitor<E>(context));
    }

    @Override
    public void visitSimpleProperty(SimplePropertyElement<E> e) {
        for (DomainElementVisitorI<E> v : visitors) {
            e.accept(v);
        }
    }

    @Override
    public void visitChoicerProperty(ChoicerPropertyElement<E> e) {
        for (DomainElementVisitorI<E> v : visitors) {
            e.accept(v);
        }
    }

    @Override
    public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {
        for (DomainElementVisitorI<E> v : visitors) {
            e.accept(v);
        }
    }

    @Override
    public void visitButton(ButtonElement<E> e) {
        for (DomainElementVisitorI<E> v : visitors) {
            e.accept(v);
        }
    }

    @Override
    public void visitResource(ResourceElement<E> e) {
        for (DomainElementVisitorI<E> v : visitors) {
            e.accept(v);
        }
    }

    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {
        for (DomainElementVisitorI<E> v : visitors) {
            e.accept(v);
        }
    }

    @Override
    public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {
        for (DomainElementVisitorI<E> v : visitors) {
            e.accept(v);
        }
    }

    @Override
    public void visitFieldsetClose() {
        for (DomainElementVisitorI<E> v : visitors) {
            v.visitFieldsetClose();
        }
    }

    @Override
    public void visitHiddenProperty(HiddenPropertyElement<E> e) {
        for (DomainElementVisitorI<E> v : visitors) {
            e.accept(v);
        }
    }

    public void finish() {
        for (DomainElementVisitorI<E> v : visitors) {
            v.finish();
        }
    }

}
