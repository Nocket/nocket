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
import org.nocket.gen.domain.visitor.html.DelegateHtmlVisitor;
import org.nocket.gen.domain.visitor.java.DelegateJavaPagePanelVisitor;
import org.nocket.gen.domain.visitor.java.JavaConstantsGeneratorVisitor;
import org.nocket.gen.domain.visitor.java.JavaPageGeneratorVisitor;
import org.nocket.gen.domain.visitor.print.PrintVisitor;
import org.nocket.gen.domain.visitor.properties.DelegatePropertiesVisitor;

/**
 * This visitor is used while when generating necessary files to  
 * 
 * @author blaz02
 *
 * @param <E>
 */
public class NocketGenerationVisitor<E extends AbstractDomainReference> extends AbstractDomainElementVisitor<E> {

	protected final List<DomainElementVisitorI<E>> visitors = new ArrayList<DomainElementVisitorI<E>>();

	public NocketGenerationVisitor(DMDWebGenContext<E> context) {
		super(context);
		addVisitors(context);
	}

	protected void addVisitors(DMDWebGenContext<E> context) {
		visitors.add(new PrintVisitor<E>(context));
		visitors.add(new JavaConstantsGeneratorVisitor<E>(context));
		visitors.add(new DelegateHtmlVisitor<E>(context));
		visitors.add(new DelegateJavaPagePanelVisitor<E>(context));
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
