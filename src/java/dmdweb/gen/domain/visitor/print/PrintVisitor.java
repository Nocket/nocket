package dmdweb.gen.domain.visitor.print;

import gengui.domain.AbstractDomainReference;
import dmdweb.gen.domain.DMDWebGenContext;
import dmdweb.gen.domain.element.ButtonElement;
import dmdweb.gen.domain.element.CheckboxPropertyElement;
import dmdweb.gen.domain.element.ChoicerPropertyElement;
import dmdweb.gen.domain.element.HeadlineElement;
import dmdweb.gen.domain.element.HiddenPropertyElement;
import dmdweb.gen.domain.element.MultivalueButtonElement;
import dmdweb.gen.domain.element.MultivaluePropertyElement;
import dmdweb.gen.domain.element.ResourceElement;
import dmdweb.gen.domain.element.SimplePropertyElement;
import dmdweb.gen.domain.visitor.AbstractDomainElementVisitor;

public class PrintVisitor<E extends AbstractDomainReference> extends AbstractDomainElementVisitor<E> {

    private static final String INDENT = "  ";
    private int indentLevel = 0;

    public PrintVisitor(DMDWebGenContext<E> context) {
        super(context);
    }

    @Override
    public void visitSimpleProperty(SimplePropertyElement<E> e) {
        println("Property: " + e.getWicketId() + " -> " + e.getPrompt());
    }

    @Override
    public void visitChoicerProperty(ChoicerPropertyElement<E> e) {
        println("Choicer: " + e.getWicketId() + " -> " + e.getPrompt());
    }

    @Override
    public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {
        println("Checkbox: " + e.getWicketId() + " -> " + e.getPrompt());
    }

    @Override
    public void visitButton(ButtonElement<E> e) {
        println("Button: " + e.getWicketId() + " -> " + e.getPrompt());
    }

    @Override
    public void visitResource(ResourceElement<E> e) {
        println("Resource: " + e.getWicketId() + " -> " + e.getPrompt());
    }

    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {
        println("Headline: " + e.getWicketId() + " -> " + e.getPrompt());
        indentLevel++;
    }

    @Override
    public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {
        println("MultivalueProperty: " + e.getWicketId() + " -> " + e.getPrompt());
        for (MultivalueButtonElement<E> b : e.getButtonElements()) {
            println("MultivalueButton: " + b.getWicketId() + " -> " + b.getPrompt());
        }
    }

    @Override
    public void visitFieldsetClose() {
        indentLevel--;
    }

    @Override
    public void visitHiddenProperty(HiddenPropertyElement<E> e) {
        println("Hidden: " + e.getWicketId() + " -> " + e.getPrompt());
    }

    @Override
    public void finish() {
        println("Finish");
    }

    private void println(String text) {
        String indent = "";
        for (int i = 0; i < indentLevel; i++) {
            indent += INDENT;
        }
        System.out.println(indent + text);
    }

}
