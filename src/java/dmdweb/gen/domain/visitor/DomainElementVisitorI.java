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

public interface DomainElementVisitorI<E extends AbstractDomainReference> {

    DMDWebGenContext<E> getContext();

    void visitSimpleProperty(SimplePropertyElement<E> e);

    void visitChoicerProperty(ChoicerPropertyElement<E> e);

    void visitCheckboxProperty(CheckboxPropertyElement<E> e);

    void visitButton(ButtonElement<E> e);

    void visitResource(ResourceElement<E> e);

    void visitFieldsetOpen(HeadlineElement<E> e);

    void visitMultivalueProperty(MultivaluePropertyElement<E> e);

    void visitFieldsetClose();

    void visitHiddenProperty(HiddenPropertyElement<E> e);

    void finish();

}
