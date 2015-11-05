package org.nocket.gen.domain.visitor.java;

import gengui.domain.AbstractDomainReference;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.CheckboxPropertyElement;
import org.nocket.gen.domain.element.ChoicerPropertyElement;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.element.HiddenPropertyElement;
import org.nocket.gen.domain.element.MultivalueButtonElement;
import org.nocket.gen.domain.element.MultivalueColumnElement;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;
import org.nocket.util.Assert;

/**
 * This visitor generates a utility class with constants definitions for the
 * wicketIds that have been generated into HTML to make coding the controller
 * easier and typesafe.
 */
public class JavaConstantsGeneratorVisitor<E extends AbstractDomainReference> extends AbstractJavaGeneratorVisitor<E> {

    private static final String SUFFIX = "Constants";

	private final Comparator<? super String> keyComparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    };
    
    private final Map<String, String> sortKey_wicketIdLine = new TreeMap<String, String>(keyComparator);
    private final Set<String> duplicateWicketIdFilter = new HashSet<String>();
    private final Map<String, String> sortKey_propertyLine = new TreeMap<String, String>(keyComparator);
    private final Set<String> duplicatePropertyFilter = new HashSet<String>();
    
    // Skip headline for multivalue elements, on all other circumstances, generate constant for it!
    private HeadlineElement<E> lastHeadline;

    public JavaConstantsGeneratorVisitor(DMDWebGenContext<E> context) {
        super(context);
    }
    
    @Override
    public void visitSimpleProperty(SimplePropertyElement<E> e) {
        appendConstant(e);
    }

    @Override
    public void visitChoicerProperty(ChoicerPropertyElement<E> e) {
        appendConstant(e);
    }

    @Override
    public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {
        appendConstant(e);
    }

    @Override
    public void visitButton(ButtonElement<E> e) {
        appendConstant(e);
    }

    @Override
    public void visitResource(ResourceElement<E> e) {
        appendConstant(e);
    }

    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {
        if (lastHeadline != null) {
            appendConstant(lastHeadline);
        }
        lastHeadline = e;
    }

    @Override
    public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {
        lastHeadline = null;
        appendConstant(e);
        for (MultivalueColumnElement<E> c : e.getColumns()) {
            appendConstant(c, c.getPrompt(), c.getPropertiesWicketId().replace(".", "_"), c.getColumnName(),
                    c.getPropertyName());
        }
        for (MultivalueButtonElement<E> b : e.getButtonElements()) {
            appendConstant(b, b.getPrompt(), b.getPropertiesWicketId().replace(".", "_"), b.getPropertyName(),
                    b.getPropertyName());
        }
    }

    @Override
    public void visitFieldsetClose() {
        // Ignore
    }

    @Override
    public void visitHiddenProperty(HiddenPropertyElement<E> e) {
        appendConstant(e);
    }

    private void appendConstant(DomainElementI<E> e) {
        appendConstant(e, e.getPrompt(), e.getWicketId().replace(".", "_"), e.getWicketId(), e.getPropertyName());
    }

    private void appendConstant(DomainElementI<E> e, String prompt, String variableName, String wicketId,
            String property) {
        if (lastHeadline != null) {
            HeadlineElement<E> copy = lastHeadline;
            lastHeadline = null;
            appendConstant(copy);
        }

        Assert.notNull(duplicateWicketIdFilter.add(variableName), "A constant for '" + variableName
                + "' isn't allowed to be created.");

        StringBuilder line = createVariableLine(e, prompt, variableName, wicketId);
        sortKey_wicketIdLine.put(variableName, line.toString());

        if (duplicatePropertyFilter.add(property)) {
            StringBuilder proppertyLine = createPropertyLine(prompt, property);
            sortKey_propertyLine.put(property, proppertyLine.toString());
        }
    }

    protected StringBuilder createPropertyLine(String prompt, String property) {
        StringBuilder proppertyLine = new StringBuilder(INDENT_1);
        proppertyLine.append(INDENT_1);
        proppertyLine.append("/** ").append(prompt).append(" */\n");
        proppertyLine.append(INDENT_2).append("public static final String ");
        proppertyLine.append(property).append(" = \"").append(property).append("\";");
        return proppertyLine;
    }

    protected StringBuilder createVariableLine(DomainElementI<E> e, String prompt, String variableName, String wicketId) {
        StringBuilder line = new StringBuilder(INDENT_1);
        line.append("/** " + e.getClass().getSimpleName() + ": ");
        line.append(prompt);
        line.append(" */\n");
        line.append(INDENT_1).append("public static final String ");
        line.append(variableName).append(" = \"").append(wicketId).append("\";");
        return line;
    }

    @Override
    public void finish() {
        if (!sortKey_wicketIdLine.isEmpty()) {
            StringBuilder gen = new StringBuilder();
            gen.append("package ").append(getPojoPackageName()).append(";\n\n");
            gen.append("// CHECKSTYLE_OFF\n");
            gen.append("public final class ").append(getJavaClassName()).append(" {\n\n");            
            gen.append(INDENT_1).append("private ").append(getJavaClassName()).append("() {}\n\n");
            for (Entry<String, String> entry : sortKey_wicketIdLine.entrySet()) {
                gen.append(entry.getValue()).append("\n");
            }
            gen.append("\n");
            gen.append(INDENT_1).append("public final class Properties {\n\n");
            gen.append(INDENT_2).append("private Properties() {}\n\n");
            for (Entry<String, String> entry : sortKey_propertyLine.entrySet()) {
                gen.append(entry.getValue()).append("\n");
            }
            gen.append(INDENT_1).append("}\n\n}\n");
            writeToFile(getOutputFileName(), gen);
        }
    }

    protected String getOutputFileName() {
        return getJavaClassName() + JAVAEXT;
	}
    
    protected String getJavaClassName() {
        return getPojoClassName() + SUFFIX;
    }

}
