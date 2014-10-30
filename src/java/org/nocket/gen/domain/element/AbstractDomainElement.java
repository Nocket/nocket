package org.nocket.gen.domain.element;

import gengui.domain.AbstractDomainReference;
import gengui.domain.DomainClassReference;
import gengui.guibuilder.FormBuilder;
import gengui.util.DomainProperties;
import gengui.util.ReflectionUtil;

import java.lang.reflect.Method;

import org.nocket.gen.domain.ref.WrappedDomainReferenceI;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDomainElement.
 *
 * @param <E> the element type
 */
public abstract class AbstractDomainElement<E extends AbstractDomainReference> implements DomainElementI<E> {

    /** The accessor. */
    private WrappedDomainReferenceI<E> accessor;
    
    /** The method. */
    private Method method;

    /**
     * Instantiates a new abstract domain element.
     *
     * @param accessor the accessor
     * @param method the method
     */
    public AbstractDomainElement(WrappedDomainReferenceI<E> accessor, Method method) {
	this.accessor = accessor;
	this.method = method;
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.element.DomainElementI#getAccessor()
     */
    @Override
    public WrappedDomainReferenceI<E> getAccessor() {
	return accessor;
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.element.DomainElementI#getPath()
     */
    @Override
    public String getPath() {
	return accessor.getRef().getPath();
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.element.DomainElementI#getPropertyName()
     */
    @Override
    public String getPropertyName() {
	return ReflectionUtil.removePrefix(getMethod().getName());
    }

    /**
     * Combination of PATH+NAME; e.g. "someEmbeddedBean.someProperty" or just
     * "someProperty"
     *
     * @return the wicket id
     */
    @Override
    public String getWicketId() {
	return accessor.getClassRef().buildFullName(getPropertyName());
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.element.DomainElementI#getPrompt()
     */
    @Override
    public String getPrompt() {
	return FormBuilder.buildPrompt(accessor.getClassRef(), method, getPropertyName());
    }

    /**
     * Returns a prompt which may have run through a decoration process,
     * currently only for the purpose of indicating mandatory input.
     *
     * @return the prompt formatted
     */
    public String getPromptFormatted() {
	String prompt = getPrompt();
	DomainClassReference classref = accessor.getClassRef();
	if (ReflectionUtil.isGetter(method) && FormBuilder.fieldRequiresInput(method, getPropertyName(), classref)) {
	    DomainProperties properties = new DomainProperties(classref);
	    String format = (method.getReturnType() != boolean.class) ?
		    properties.getMandatoryIndicator() : properties.getBooleanMandatoryIndicator();
		    prompt = String.format(format, prompt);
	}
	return prompt;
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.element.DomainElementI#getMethod()
     */
    @Override
    public Method getMethod() {
	return method;
    }

    /* (non-Javadoc)
     * @see org.nocket.gen.domain.element.DomainElementI#repeated()
     */
    @Override
    public boolean repeated() {
	return false;
    }

}
