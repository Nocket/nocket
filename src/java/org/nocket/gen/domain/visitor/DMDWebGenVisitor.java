package org.nocket.gen.domain.visitor;

import gengui.domain.AbstractDomainReference;

import java.util.ArrayList;
import java.util.List;

import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.CheckboxPropertyElement;
import org.nocket.gen.domain.element.ChoicerPropertyElement;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.element.HiddenPropertyElement;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;
import org.nocket.gen.domain.visitor.constants.ConstantsVisitor;
import org.nocket.gen.domain.visitor.html.DelegateHtmlVisitor;
import org.nocket.gen.domain.visitor.properties.DelegatePropertiesVisitor;

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
