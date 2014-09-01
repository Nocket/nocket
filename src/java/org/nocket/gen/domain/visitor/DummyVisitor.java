package org.nocket.gen.domain.visitor;

import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.CheckboxPropertyElement;
import org.nocket.gen.domain.element.ChoicerPropertyElement;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.element.HiddenPropertyElement;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;

import gengui.domain.AbstractDomainReference;

public class DummyVisitor<E extends AbstractDomainReference> extends AbstractDomainElementVisitor<E> {

    public DummyVisitor(DMDWebGenContext<E> context) {
        super(context);
    }

    @Override
    public void visitSimpleProperty(SimplePropertyElement<E> e) {

    }

    @Override
    public void visitChoicerProperty(ChoicerPropertyElement<E> e) {

    }

    @Override
    public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {

    }

    @Override
    public void visitButton(ButtonElement<E> e) {

    }

    @Override
    public void visitResource(ResourceElement<E> e) {

    }

    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {

    }

    @Override
    public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {

    }

    @Override
    public void visitFieldsetClose() {

    }

    @Override
    public void visitHiddenProperty(HiddenPropertyElement<E> e) {

    }

    @Override
    public void finish() {

    }

}
