package org.nocket.gen.domain.visitor.constants;

import gengui.domain.AbstractDomainReference;
import gengui.util.SevereGUIException;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
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
import org.nocket.gen.domain.visitor.AbstractDomainElementVisitor;
import org.nocket.util.Assert;

/**
 * This visitor generates a utility class with constants definitions for the
 * wicketIds that have been generated into HTML to make coding the controller
 * easier and typesafe.
 */
public class ConstantsVisitor<E extends AbstractDomainReference> extends AbstractDomainElementVisitor<E> {

    private static final String INDENT = "    ";
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
    // Skip headline for multivalue elements, on all other circumstances,
    // generate constant for it!
    private HeadlineElement<E> lastHeadline;

    public ConstantsVisitor(DMDWebGenContext<E> context) {
        super(context);
    }

    private File getJavaFile() {
        String path = getContext().getGenDir() + File.separator
                + getContext().getFileAndClassNameStrategy().getFilenamePartAsPath() + "Constants.java";

        return new File(path);
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
        for (MultivalueColumnElement c : e.getColumns()) {
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

        String lineStr = createVariableLine(e, prompt, variableName, wicketId);
        sortKey_wicketIdLine.put(variableName, lineStr);

        if (duplicatePropertyFilter.add(property)) {
            StringBuilder proppertyLine = createPropertyLine(prompt, property);
            sortKey_propertyLine.put(property, proppertyLine.toString());
        }
    }

    protected StringBuilder createPropertyLine(String prompt, String property) {
        StringBuilder proppertyLine = new StringBuilder(INDENT);
        proppertyLine.append(INDENT);
        proppertyLine.append("/** ");
        proppertyLine.append(prompt);
        proppertyLine.append(" */\n");
        proppertyLine.append(INDENT);
        proppertyLine.append(INDENT);
        proppertyLine.append("public static final String ");
        proppertyLine.append(property);
        proppertyLine.append(" = \"");
        proppertyLine.append(property);
        proppertyLine.append("\";");
        return proppertyLine;
    }

    protected String createVariableLine(DomainElementI<E> e, String prompt, String variableName, String wicketId) {
        StringBuilder line = new StringBuilder(INDENT);
        line.append("/** " + e.getClass().getSimpleName() + ": ");
        line.append(prompt);
        line.append(" */\n");
        line.append(INDENT);
        line.append("public static final String ");
        line.append(variableName);
        line.append(" = \"");
        line.append(wicketId);
        line.append("\";");
        String lineStr = line.toString();
        return lineStr;
    }

    @Override
    public void finish() {
        if (!sortKey_wicketIdLine.isEmpty()) {
            String gen = "package " + getContext().getFileAndClassNameStrategy().getJavaClassPackageName() + ";\n";
            gen += "\n";
            gen += "// CHECKSTYLE_OFF\n";
            gen += "public final class " + getJavaClassName() + " {\n";
            gen += "\n";
            gen += INDENT + "private " + getJavaClassName() + "() {}\n";
            gen += "\n";
            for (Entry<String, String> entry : sortKey_wicketIdLine.entrySet()) {
                gen += entry.getValue() + "\n";
            }
            gen += "\n";
            gen += INDENT + "public final class Properties {\n";
            gen += "\n";
            gen += INDENT + INDENT + "private Properties() {}\n";
            gen += "\n";
            for (Entry<String, String> entry : sortKey_propertyLine.entrySet()) {
                gen += entry.getValue() + "\n";
            }
            gen += INDENT + "}\n";
            gen += "\n";
            gen += "}\n";
            File file = getJavaFile();
            FileUtils.deleteQuietly(file);
            try {
                FileUtils.forceMkdir(file.getParentFile());
                FileUtils.writeStringToFile(file, gen);
            } catch (IOException e) {
                throw new SevereGUIException(e);
            }
        }
    }

    private String getJavaClassName() {
        return getContext().getFileAndClassNameStrategy().getJavaClassNamePart() + "Constants";
    }

}
