package dmdweb.gen.domain.element;

import gengui.domain.AbstractDomainReference;
import gengui.guiadapter.ConnectionReuse;
import gengui.util.ReflectionUtil;

import java.lang.reflect.Method;

import dmdweb.gen.domain.ref.WrappedDomainReferenceI;
import dmdweb.gen.domain.visitor.DomainElementVisitorI;
import dmdweb.gen.page.element.synchronizer.SynchronizerHelper;

/**
 * A simple textfield
 * 
 */
public class SimplePropertyElement<E extends AbstractDomainReference> extends AbstractDomainElement<E> {

    public SimplePropertyElement(WrappedDomainReferenceI<E> accessor, Method method) {
	super(accessor, method);
    }

    @Override
    public void accept(DomainElementVisitorI<E> visitor) {
	visitor.visitSimpleProperty(this);
    }

    public boolean isDateType() {
	return SynchronizerHelper.isDateType(getMethod());
    }

    public boolean isNumberType() {
	return SynchronizerHelper.isNumberType(getMethod());
    }

    public boolean isFileType() {
	return SynchronizerHelper.isFileType(getMethod());
    }

    public boolean isInputStreamType() {
	return SynchronizerHelper.isInputStreamType(getMethod());
    }

    public boolean isReadonlyFileType() {
	if (!isFileType())
	    return false;
	Method setterMethod = getSetterMethod();
	return getMethod() != null
		&& (setterMethod == null || ReflectionUtil.isStaticallyDisabled(setterMethod, getAccessor()
			.getClassRef())); //
    }

    protected Method getSetterMethod() {
	return getAccessor().getClassRef().findPropertyAccessor(getPropertyName(), ReflectionUtil.SETTER_PREFIX);
    }

    /**
     * Can be used to check if range validation is possible on this number.
     */
    public boolean isRangedNumberType() {
	return SynchronizerHelper.isRangedNumberType(getMethod());
    }

    @Override
    public DomainElementI<E> replicate(ConnectionReuse reuse) {
	return new SimplePropertyElement<E>(this.getAccessor().replicate(reuse), this.getMethod());
    }

}
