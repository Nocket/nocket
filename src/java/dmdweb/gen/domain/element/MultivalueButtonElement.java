package dmdweb.gen.domain.element;

import gengui.domain.AbstractDomainReference;
import gengui.guiadapter.ConnectionReuse;
import gengui.util.ReflectionUtil;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import dmdweb.gen.domain.ref.WrappedDomainReferenceI;
import dmdweb.gen.domain.visitor.DomainElementVisitorI;

public class MultivalueButtonElement<E extends AbstractDomainReference> extends AbstractDomainElement<E> {

    public MultivalueButtonElement(WrappedDomainReferenceI<E> accessor, Method method) {
        super(accessor, method);
    }

    public String getPropertiesWicketId() {
        String replace = "columns." + getPropertyName();
        if (isRemover()) {
            replace = StringUtils.removeStart(getPropertyName(), ReflectionUtil.REMOVER_PREFIX) + "." + replace;
        }
        return super.getWicketId().replace(getPropertyName(), replace);
    }

    @Deprecated
    @Override
    public void accept(DomainElementVisitorI<E> visitor) {
        throw new UnsupportedOperationException();
    }

    public boolean isRemover() {
        return getMethod().getName().startsWith(ReflectionUtil.REMOVER_PREFIX);
    }

    @Override
    public boolean repeated() {
        return true;
    }

    @Override
    public DomainElementI<E> replicate(ConnectionReuse reuse) {
        return new MultivalueButtonElement<E>(this.getAccessor().replicate(reuse), this.getMethod());
    }

}
