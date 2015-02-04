package org.nocket.gen.domain.visitor.java;

import gengui.domain.AbstractDomainReference;

import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.CheckboxPropertyElement;
import org.nocket.gen.domain.element.ChoicerPropertyElement;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.element.HiddenPropertyElement;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;
import org.nocket.gen.domain.visitor.AbstractDomainElementVisitor;
import org.nocket.gen.domain.visitor.DomainElementVisitorI;

public class DelegateJavaPagePanelVisitor<E extends AbstractDomainReference> extends AbstractDomainElementVisitor<E>{

    /** The delegate. */
    private final DomainElementVisitorI<E> delegate;

	public DelegateJavaPagePanelVisitor(DMDWebGenContext<E> context) {
		super(context);
		if(context.getGeneratePanel()) {
			delegate = new JavaPanelGeneratorVisitor<E>(context);
		} else {
			delegate = new JavaPageGeneratorVisitor<E>(context);
		}
	}

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitSimpleProperty(org.nocket.gen.domain.element.SimplePropertyElement)
     */
    @Override
    public void visitSimpleProperty(SimplePropertyElement<E> e) {
        e.accept(delegate);
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitChoicerProperty(org.nocket.gen.domain.element.ChoicerPropertyElement)
     */
    @Override
    public void visitChoicerProperty(ChoicerPropertyElement<E> e) {
        e.accept(delegate);
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitCheckboxProperty(org.nocket.gen.domain.element.CheckboxPropertyElement)
     */
    @Override
    public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {
        e.accept(delegate);
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitButton(org.nocket.gen.domain.element.ButtonElement)
     */
    @Override
    public void visitButton(ButtonElement<E> e) {
        e.accept(delegate);
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitResource(org.nocket.gen.domain.element.ResourceElement)
     */
    @Override
    public void visitResource(ResourceElement<E> e) {
        e.accept(delegate);
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitFieldsetOpen(org.nocket.gen.domain.element.HeadlineElement)
     */
    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {
        e.accept(delegate);
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitMultivalueProperty(org.nocket.gen.domain.element.MultivaluePropertyElement)
     */
    @Override
    public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {
        e.accept(delegate);
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitFieldsetClose()
     */
    @Override
    public void visitFieldsetClose() {
        delegate.visitFieldsetClose();
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitHiddenProperty(org.nocket.gen.domain.element.HiddenPropertyElement)
     */
    @Override
    public void visitHiddenProperty(HiddenPropertyElement<E> e) {
        e.accept(delegate);
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#finish()
     */
    @Override
    public void finish() {
        delegate.finish();
    }

}
