package dmdweb.gen.domain.visitor.properties;

import gengui.domain.AbstractDomainReference;

import java.io.File;

import org.apache.commons.lang.BooleanUtils;

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

/**
 * This visitor creates property file according to Wicket rules. The file is
 * placed in the same package as the domain class. If file does exists, new
 * properties will be added to it. Old properties are never overwritten.
 * 
 * @author stang01
 */
public class WicketPropertiesVisitor<E extends AbstractDomainReference> extends AbstractPropertiesVisitor<E> {

    public WicketPropertiesVisitor(DMDWebGenContext<E> context) {
        super(context);
    }

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

    @Override
    public void visitSimpleProperty(SimplePropertyElement<E> e) {
        addProperty(e.getWicketId(), e.getPromptFormatted());
    }

    @Override
    public void visitChoicerProperty(ChoicerPropertyElement<E> e) {
        addProperty(e.getWicketId(), e.getPromptFormatted());
    }

    @Override
    public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {
        addProperty(e.getWicketId(), e.getPromptFormatted());
    }

    @Override
    public void visitButton(ButtonElement<E> e) {
        addProperty(e.getWicketId(), e.getPrompt());
    }

    @Override
    public void visitResource(ResourceElement<E> e) {
        addProperty(e.getWicketId(), e.getPrompt());
    }

    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {
        addProperty(e.getWicketId() + ".text", e.getPrompt());
    }

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

    @Override
    public void visitFieldsetClose() {
        // ignore
    }

    @Override
    public void visitHiddenProperty(HiddenPropertyElement<E> e) {
        // ignore
    }

}
