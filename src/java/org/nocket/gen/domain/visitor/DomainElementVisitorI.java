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
