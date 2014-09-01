package org.nocket.gen.domain.element;

import gengui.domain.AbstractDomainReference;
import gengui.guiadapter.ConnectionReuse;

import java.lang.reflect.Method;

import org.nocket.gen.domain.ref.WrappedDomainReferenceI;
import org.nocket.gen.domain.visitor.DomainElementVisitorI;

public class HiddenPropertyElement<E extends AbstractDomainReference> extends
        AbstractDomainElement<E> {

    public HiddenPropertyElement(WrappedDomainReferenceI<E> accessor,
            Method method) {
        super(accessor, method);
    }

    @Override
    public void accept(DomainElementVisitorI<E> visitor) {
        visitor.visitHiddenProperty(this);
    }

    @Override
    public DomainElementI<E> replicate(ConnectionReuse reuse) {
        return new HiddenPropertyElement<E>(this.getAccessor().replicate(reuse), this.getMethod());
    }

}
