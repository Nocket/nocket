package org.nocket.gen.domain.visitor.properties;

import gengui.annotations.Prompt;
import gengui.domain.AbstractDomainReference;
import gengui.domain.DomainClassReference;
import gengui.guibuilder.DomainClassDecoration;
import gengui.guibuilder.FormBuilder;
import gengui.guibuilder.ResourceBundleAccess;
import gengui.util.AnnotationHelper;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.AbstractDomainElement;
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
 * placed in "resources" folder. Default name is "gengui_de.properties". If file
 * does exists, new properties are added to it. Old properties are never
 * overwritten.
 *
 * @author blaz02
 * @param <E> the element type
 */
public class GenguiPropertiesVisitor<E extends AbstractDomainReference> extends AbstractPropertiesVisitor<E> {

    /** The Constant TABLE_HEADER. */
    private static final String TABLE_HEADER = "%s.%s.table.header";
    
    /** The last property file. */
    protected File lastPropertyFile;

    /**
     * Instantiates a new gengui properties visitor.
     *
     * @param context the context
     */
    public GenguiPropertiesVisitor(DMDWebGenContext<E> context) {
	super(context);
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.properties.AbstractPropertiesVisitor#getPropertiesFile()
     */
    @Override
    protected File getPropertiesFile() {
	String propertiesFilePath = ResourceBundleAccess.determineResourceFileForCurrentLocaleFromClasspath();
	return new File(propertiesFilePath);
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.properties.AbstractPropertiesVisitor#finish()
     */
    @Override
    public void finish() {
	super.finish();
	ResourceBundleAccess.clearResourceBundleCache();
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitSimpleProperty(org.nocket.gen.domain.element.SimplePropertyElement)
     */
    @Override
    public void visitSimpleProperty(SimplePropertyElement<E> e) {
	addProperty(getPropertyKey(e), e.getPromptFormatted());
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitChoicerProperty(org.nocket.gen.domain.element.ChoicerPropertyElement)
     */
    @Override
    public void visitChoicerProperty(ChoicerPropertyElement<E> e) {
	addProperty(getPropertyKey(e), e.getPromptFormatted());
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitCheckboxProperty(org.nocket.gen.domain.element.CheckboxPropertyElement)
     */
    @Override
    public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {
	addProperty(getPropertyKey(e), e.getPromptFormatted());
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitButton(org.nocket.gen.domain.element.ButtonElement)
     */
    @Override
    public void visitButton(ButtonElement<E> e) {
	addProperty(getPropertyKey(e), e.getPrompt());
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitResource(org.nocket.gen.domain.element.ResourceElement)
     */
    @Override
    public void visitResource(ResourceElement<E> e) {
	addProperty(getPropertyKey(e), e.getPrompt());
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitFieldsetOpen(org.nocket.gen.domain.element.HeadlineElement)
     */
    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {
	// add prompt
	String key = FormBuilder.buildPromptIdentifier(e.getAccessor().getClassRef(), e.getMethod(), e.getPrompt());
	addProperty(key, e.getPrompt());
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.visitor.DomainElementVisitorI#visitMultivalueProperty(org.nocket.gen.domain.element.MultivaluePropertyElement)
     */
    @Override
    public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {
	for (MultivalueColumnElement<E> column : e.getColumns()) {
	    addProperty(getPropertyKeyForTableHeader(column, true), column.getPrompt());
	}
	for (MultivalueButtonElement<E> button : e.getButtonElements()) {
	    String key = getPropertyKeyForTableHeader(button, false);
	    boolean buttonAdded = addProperty(key, button.getPrompt());
	    if (buttonAdded) {
		// only add icon properties initially
		String newKey = StringUtils.removeEnd(key, ".table.header");
		addProperty(newKey + ".icon.enabled", "");
		addProperty(newKey + ".icon.disabled", "");
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

    /**
     * Returns property key according to gengui rules. This will be either the
     * value of @Prompt annotation or key will be assembled in this way:
     * package.Class._propertyname.text
     *
     * @param e the e
     * @return the property key
     */
    private String getPropertyKey(AbstractDomainElement<E> e) {
	String key = FormBuilder.buildPromptIdentifier(e.getAccessor().getClassRef(), e.getMethod(),
		e.getPrompt());
	return key;
    }

    /**
     * Returns property key for the table column or table method according to
     * gengui rules.
     * 
     * @param e
     *            Column element
     * 
     * @param capitalize
     *            If true, property name will be capitalized while assembling
     *            full property key
     * 
     * @return This will be either the value of @Prompt annotation or key will
     *         be assembled in this way:
     *         package.Class.propertyname.table.header.
     */
    private String getPropertyKeyForTableHeader(AbstractDomainElement<E> e, boolean capitalize) {
	DomainClassReference classRef = e.getAccessor().getClassRef();
	Method method = e.getMethod();
	DomainClassDecoration interception = classRef.getDecorations("");
	method = interception.getTarget(method.getName(), method);
	Prompt prompt = new AnnotationHelper(method).getAnnotation(Prompt.class);
	if (prompt != null)
	    return prompt.value();
	return String.format(TABLE_HEADER, classRef.getDomainClass().getName(), capitalize ?
		StringUtils.capitalize(e.getPropertyName()) : e.getPropertyName());
    }

}
