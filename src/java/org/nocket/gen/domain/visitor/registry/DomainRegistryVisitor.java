package org.nocket.gen.domain.visitor.registry;

import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.CheckboxPropertyElement;
import org.nocket.gen.domain.element.ChoicerPropertyElement;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.element.HiddenPropertyElement;
import org.nocket.gen.domain.element.MultivalueButtonElement;
import org.nocket.gen.domain.element.MultivalueColumnElement;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;
import org.nocket.gen.domain.visitor.AbstractDomainElementVisitor;

import gengui.domain.AbstractDomainReference;

public class DomainRegistryVisitor<E extends AbstractDomainReference> extends AbstractDomainElementVisitor<E> {

    private DomainRegistry<E> domainRegistry = new DomainRegistry<E>();

    public DomainRegistryVisitor(DMDWebGenContext<E> context) {
        super(context);
    }

    @Override
    public void visitSimpleProperty(SimplePropertyElement<E> e) {
        domainRegistry.addElement(e);
    }

    @Override
    public void visitChoicerProperty(ChoicerPropertyElement<E> e) {
        domainRegistry.addElement(e);
    }

    @Override
    public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {
        domainRegistry.addElement(e);
    }

    @Override
    public void visitButton(ButtonElement<E> e) {
        domainRegistry.addElement(e);
    }

    @Override
    public void visitResource(ResourceElement<E> e) {
        domainRegistry.addElement(e);
    }

    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {
        // ignore
    }

    @Override
    public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {
        domainRegistry.addElement(e);
        for (MultivalueButtonElement<E> b : e.getButtonElements()) {
            domainRegistry.addElement(b);
        }
        // Columns for read-only file attributes result in download links and must be registerd like button elements
        for (MultivalueColumnElement<E> d : e.getDownloadColumnElements()) {
            domainRegistry.addElement(d);
        }
    }

    @Override
    public void visitFieldsetClose() {
        // ignore
    }

    @Override
    public void visitHiddenProperty(HiddenPropertyElement<E> e) {
        // ignore
    }

    @Override
    public void finish() {
        // ignore
    }

    public DomainRegistry<E> getDomainRegistry() {
        return domainRegistry;
    }

}
