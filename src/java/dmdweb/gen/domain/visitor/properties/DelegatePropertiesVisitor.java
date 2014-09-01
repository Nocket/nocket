package dmdweb.gen.domain.visitor.properties;

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
import dmdweb.gen.domain.visitor.AbstractDomainElementVisitor;
import dmdweb.gen.domain.visitor.DomainElementVisitorI;
import dmdweb.gen.domain.visitor.DummyVisitor;

public class DelegatePropertiesVisitor<E extends AbstractDomainReference> extends AbstractDomainElementVisitor<E> {

    private final DomainElementVisitorI<E> delegate;

    public DelegatePropertiesVisitor(DMDWebGenContext<E> context) {
        super(context);
        if (context.getDomainProperties().isJFDLocalizationOnce()) {
            this.delegate = new DummyVisitor<E>(context);
        } else if (context.getDomainProperties().isLocalizationWicket()) {
            this.delegate = new WicketPropertiesVisitor<E>(context);
        } else {
            this.delegate = new GenguiPropertiesVisitor<E>(context);
        }
    }

    @Override
    public void visitSimpleProperty(SimplePropertyElement<E> e) {
        e.accept(delegate);
    }

    @Override
    public void visitChoicerProperty(ChoicerPropertyElement<E> e) {
        e.accept(delegate);
    }

    @Override
    public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {
        e.accept(delegate);
    }

    @Override
    public void visitButton(ButtonElement<E> e) {
        e.accept(delegate);
    }

    @Override
    public void visitResource(ResourceElement<E> e) {
        e.accept(delegate);
    }

    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {
        e.accept(delegate);
    }

    @Override
    public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {
        e.accept(delegate);
    }

    @Override
    public void visitFieldsetClose() {
        delegate.visitFieldsetClose();
    }

    @Override
    public void visitHiddenProperty(HiddenPropertyElement<E> e) {
        e.accept(delegate);
    }

    @Override
    public void finish() {
        delegate.finish();
    }

}
