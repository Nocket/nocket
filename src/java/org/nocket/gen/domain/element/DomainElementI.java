package org.nocket.gen.domain.element;

import gengui.domain.AbstractDomainReference;
import gengui.guiadapter.ConnectionReuse;

import java.lang.reflect.Method;

import org.nocket.gen.domain.ref.WrappedDomainReferenceI;
import org.nocket.gen.domain.visitor.DomainElementVisitorI;

public interface DomainElementI<E extends AbstractDomainReference> {

    WrappedDomainReferenceI<E> getAccessor();

    String getPath();

    String getPropertyName();

    /**
     * Combination of PATH+NAME; e.g. "someEmbeddedBean.someProperty" or just
     * "someProperty"
     */
    String getWicketId();

    String getPrompt();

    Method getMethod();

    void accept(DomainElementVisitorI<E> visitor);

    /**
     * True if the element is part of a collection which will repeat several
     * times and therefore is not bound to any statically definied HTML element.
     * This is important when checking a generic binding for completeness.
     * Currently MultivalueButton/Property/ColumnElements are flagged as
     * repeated elements. All others are not.
     */
    boolean repeated();

    DomainElementI<E> replicate(ConnectionReuse reuse);
}
