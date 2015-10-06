package org.nocket.gen.domain.visitor.html;

import gengui.domain.AbstractDomainReference;
import gengui.util.DomainProperties.JfdRetentionStrategy;

import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.AbstractDomainElement;
import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.CheckboxPropertyElement;
import org.nocket.gen.domain.element.ChoicerPropertyElement;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.element.HiddenPropertyElement;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;
import org.nocket.gen.domain.visitor.DomainElementVisitorI;
import org.nocket.gen.domain.visitor.DummyVisitor;
import org.nocket.gen.domain.visitor.html.create.CreateHtmlVisitor;
import org.nocket.gen.domain.visitor.html.merge.MergeHtmlVisitor;
import org.nocket.gen.domain.visitor.html.styling.StylingFactory;
import org.nocket.gen.domain.visitor.html.styling.common.StylingStrategyI;

/**
 * This visitor is delegates HTML generation to another visitor depending on retention strategy
 * and layouting strategy. 
 *  
 * @param <E>
 */
public class DelegateHtmlVisitor<E extends AbstractDomainReference> extends AbstractHtmlVisitor<E> {

	private DomainElementVisitorI<E> delegate;

	public DelegateHtmlVisitor(DMDWebGenContext<E> context) {
		super(context);
	}

	@Override
	public void visitSimpleProperty(SimplePropertyElement<E> e) {
		doDelegation(e);
	}

	@Override
	public void visitChoicerProperty(ChoicerPropertyElement<E> e) {
		doDelegation(e);
	}

	@Override
	public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {
		doDelegation(e);
	}

	@Override
	public void visitButton(ButtonElement<E> e) {
		doDelegation(e);
	}

	@Override
	public void visitResource(ResourceElement<E> e) {
		doDelegation(e);
	}

	@Override
	public void visitFieldsetOpen(HeadlineElement<E> e) {
		doDelegation(e);
	}

	@Override
	public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {
		doDelegation(e);
	}

	@Override
	public void visitFieldsetClose() {
		delegate.visitFieldsetClose();
	}

	@Override
	public void visitHiddenProperty(HiddenPropertyElement<E> e) {
		doDelegation(e);
	}

	protected void doDelegation(AbstractDomainElement<E> e) {
		if (delegate == null) {
			lazyInitDelegate(getContext());
		}
		e.accept(delegate);
	}

	@Override
	public void finish() {
		if (delegate == null) {
			lazyInitDelegate(getContext());
		}
		delegate.finish();
	}

	protected void lazyInitDelegate(DMDWebGenContext<E> context) {
		boolean fileExists = getHtmlFile().exists();
		JfdRetentionStrategy jfdRetentionStrategy = getContext().getDomainProperties().getJFDRetentionStrategy();
		switch (jfdRetentionStrategy) {
		case merge:
		case silentmerge:
			if (fileExists) {
				this.delegate = new MergeHtmlVisitor<E>(context, getStylingStrategy(context));
			} else {
				this.delegate = new CreateHtmlVisitor<E>(context, getStylingStrategy(context));
			}
			break;
		case keep:
			if (fileExists) {
				this.delegate = new DummyVisitor<E>(context);
			} else {
				this.delegate = new CreateHtmlVisitor<E>(context, getStylingStrategy(context));
			}
			break;
		case overwrite:
			this.delegate = new CreateHtmlVisitor<E>(context, getStylingStrategy(context));
			break;
		case none:
		default:
			throw new UnsupportedOperationException("Unsupported " + JfdRetentionStrategy.class.getSimpleName() + ": "
					+ jfdRetentionStrategy);
		}
	}

	private StylingStrategyI getStylingStrategy(DMDWebGenContext<E> context) {
		return StylingFactory.newStylingStrategyInstance(context);
	}
	
	
}
