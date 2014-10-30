package org.nocket.gen.domain.visitor.properties;

import gengui.domain.AbstractDomainReference;

import java.io.File;

import org.apache.commons.lang.BooleanUtils;
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

// TODO: Auto-generated Javadoc
/**
 * This visitor creates property file according to Wicket rules. The file is
 * placed in the same package as the domain class. If file does exists, new
 * properties will be added to it. Old properties are never overwritten.
 *
 * @author stang01
 * @param <E> the element type
 */
public class WicketPropertiesVisitor<E extends AbstractDomainReference> extends AbstractPropertiesVisitor<E> {

    /**
     * Instantiates a new wicket properties visitor.
     *
     * @param context the context
     */
    public WicketPropertiesVisitor(DMDWebGenContext<E> context) {
        super(context);
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.properties.AbstractPropertiesVisitor#getPropertiesFile()
     */
    @Override
    protected File getPropertiesFile() {
        String basePath = getContext().getSrcDir() + File.separator
                + getContext().getFileAndClassNameStrategy().getFilenamePartAsPath();
        File panelJavaSourceFile = new File(basePath + "Panel.java");
        File panelPropertiesFile = new File(basePath + "Panel.properties");
        if (panelPropertiesFile.exists() || panelJavaSourceFile.exists()
                || BooleanUtils.isTrue(getContext().getGeneratePanel())) {
            return panelPropertiesFile;
        } else {
            String pagePropertiesFile = basePath + "Page.properties";
            return new File(pagePropertiesFile);
        }
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitSimpleProperty(org.nocket.gen.domain.element.SimplePropertyElement)
     */
    @Override
    public void visitSimpleProperty(SimplePropertyElement<E> e) {
        addProperty(e.getWicketId(), e.getPromptFormatted());
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitChoicerProperty(org.nocket.gen.domain.element.ChoicerPropertyElement)
     */
    @Override
    public void visitChoicerProperty(ChoicerPropertyElement<E> e) {
        addProperty(e.getWicketId(), e.getPromptFormatted());
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitCheckboxProperty(org.nocket.gen.domain.element.CheckboxPropertyElement)
     */
    @Override
    public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {
        addProperty(e.getWicketId(), e.getPromptFormatted());
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitButton(org.nocket.gen.domain.element.ButtonElement)
     */
    @Override
    public void visitButton(ButtonElement<E> e) {
        addProperty(e.getWicketId(), e.getPrompt());
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitResource(org.nocket.gen.domain.element.ResourceElement)
     */
    @Override
    public void visitResource(ResourceElement<E> e) {
        addProperty(e.getWicketId(), e.getPrompt());
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitFieldsetOpen(org.nocket.gen.domain.element.HeadlineElement)
     */
    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {
        addProperty(e.getWicketId() + ".text", e.getPrompt());
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitMultivalueProperty(org.nocket.gen.domain.element.MultivaluePropertyElement)
     */
    @Override
    public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {
        for (MultivalueColumnElement column : e.getColumns()) {
            addProperty(column.getPropertiesWicketId(), column.getPrompt());
        }
        for (MultivalueButtonElement<E> b : e.getButtonElements()) {
            boolean buttonAdded = addProperty(b.getPropertiesWicketId(), b.getPrompt());
            if (buttonAdded) {
                // only add icon properties initially
                addProperty(b.getPropertiesWicketId() + ".icon.enabled", "");
                addProperty(b.getPropertiesWicketId() + ".icon.disabled", "");
            }
        }
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitFieldsetClose()
     */
    @Override
    public void visitFieldsetClose() {
        // ignore
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitHiddenProperty(org.nocket.gen.domain.element.HiddenPropertyElement)
     */
    @Override
    public void visitHiddenProperty(HiddenPropertyElement<E> e) {
        // ignore
    }

}
