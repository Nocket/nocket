package dmdweb.gen.domain.visitor.registry;

import gengui.domain.AbstractDomainReference;
import dmdweb.gen.domain.DMDWebGenContext;
import dmdweb.gen.domain.element.ButtonElement;
import dmdweb.gen.domain.element.CheckboxPropertyElement;
import dmdweb.gen.domain.element.ChoicerPropertyElement;
import dmdweb.gen.domain.element.HeadlineElement;
import dmdweb.gen.domain.element.HiddenPropertyElement;
import dmdweb.gen.domain.element.MultivalueButtonElement;
import dmdweb.gen.domain.element.MultivalueColumnElement;
import dmdweb.gen.domain.element.MultivaluePropertyElement;
import dmdweb.gen.domain.element.ResourceElement;
import dmdweb.gen.domain.element.SimplePropertyElement;
import dmdweb.gen.domain.visitor.AbstractDomainElementVisitor;

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
