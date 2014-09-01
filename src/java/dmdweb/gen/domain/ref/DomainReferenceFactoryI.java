package dmdweb.gen.domain.ref;

import gengui.domain.AbstractDomainReference;

import java.lang.reflect.Method;

public interface DomainReferenceFactoryI<E extends AbstractDomainReference> {

    WrappedDomainReferenceI<E> getRootReference();

    WrappedDomainReferenceI<E> newSubReference(
            WrappedDomainReferenceI<E> upper, Method getter);

}
