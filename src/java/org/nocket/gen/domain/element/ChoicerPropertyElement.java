package org.nocket.gen.domain.element;

import gengui.domain.AbstractDomainReference;
import gengui.guiadapter.ConnectionReuse;

import java.lang.reflect.Method;

import org.nocket.gen.domain.ref.WrappedDomainReferenceI;
import org.nocket.gen.domain.visitor.DomainElementVisitorI;

public class ChoicerPropertyElement<E extends AbstractDomainReference> extends AbstractDomainElement<E> {

    public ChoicerPropertyElement(WrappedDomainReferenceI<E> accessor, Method method) {
        super(accessor, method);
    }

    @Override
    public void accept(DomainElementVisitorI<E> visitor) {
        visitor.visitChoicerProperty(this);
    }

    @Override
    public DomainElementI<E> replicate(ConnectionReuse reuse) {
        return new ChoicerPropertyElement<E>(this.getAccessor().replicate(reuse), this.getMethod());
    }

}
