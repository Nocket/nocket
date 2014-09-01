package org.nocket.gen.domain.ref;

import gengui.domain.AbstractDomainReference;
import gengui.domain.DomainClassReference;
import gengui.guiadapter.ConnectionReuse;

public interface WrappedDomainReferenceI<E extends AbstractDomainReference> {

    E getRef();

    DomainClassReference getClassRef();

    WrappedDomainReferenceI<E> replicate(ConnectionReuse reuse);
}
