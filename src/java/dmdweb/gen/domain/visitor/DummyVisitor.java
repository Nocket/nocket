package dmdweb.gen.domain.visitor;

import gengui.domain.AbstractDomainReference;
import dmdweb.gen.domain.DMDWebGenContext;
import dmdweb.gen.domain.element.ButtonElement;
import dmdweb.gen.domain.element.CheckboxPropertyElement;
import dmdweb.gen.domain.element.ChoicerPropertyElement;
import dmdweb.gen.domain.element.HeadlineElement;
import dmdweb.gen.domain.element.HiddenPropertyElement;
import dmdweb.gen.domain.element.MultivaluePropertyElement;
import dmdweb.gen.domain.element.ResourceElement;
import dmdweb.gen.domain.element.SimplePropertyElement;

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
