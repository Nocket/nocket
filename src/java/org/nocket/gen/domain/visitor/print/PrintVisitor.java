package org.nocket.gen.domain.visitor.print;

import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.CheckboxPropertyElement;
import org.nocket.gen.domain.element.ChoicerPropertyElement;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.element.HiddenPropertyElement;
import org.nocket.gen.domain.element.MultivalueButtonElement;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;
import org.nocket.gen.domain.visitor.AbstractDomainElementVisitor;

import gengui.domain.AbstractDomainReference;

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
