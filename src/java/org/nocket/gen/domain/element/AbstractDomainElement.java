package org.nocket.gen.domain.element;

import gengui.domain.AbstractDomainReference;
import gengui.domain.DomainClassReference;
import gengui.guibuilder.FormBuilder;
import gengui.util.DomainProperties;
import gengui.util.ReflectionUtil;

import java.lang.reflect.Method;

import org.nocket.gen.domain.ref.WrappedDomainReferenceI;

public abstract class AbstractDomainElement<E extends AbstractDomainReference> implements DomainElementI<E> {

    private WrappedDomainReferenceI<E> accessor;
    private Method method;

    public AbstractDomainElement(WrappedDomainReferenceI<E> accessor, Method method) {
	this.accessor = accessor;
	this.method = method;
    }

    @Override
    public WrappedDomainReferenceI<E> getAccessor() {
	return accessor;
    }

    @Override
    public String getPath() {
	return accessor.getRef().getPath();
    }

    @Override
    public String getPropertyName() {
	return ReflectionUtil.removePrefix(getMethod().getName());
    }

    /**
     * Combination of PATH+NAME; e.g. "someEmbeddedBean.someProperty" or just
     * "someProperty"
     */
    @Override
    public String getWicketId() {
	return accessor.getClassRef().buildFullName(getPropertyName());
    }

    @Override
    public String getPrompt() {
	return FormBuilder.buildPrompt(accessor.getClassRef(), method, getPropertyName());
    }

    /**
     * Returns a prompt which may have run through a decoration process,
     * currently only for the purpose of indicating mandatory input
     * 
     * @return
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

    @Override
    public Method getMethod() {
	return method;
    }

    @Override
    public boolean repeated() {
	return false;
    }

}
