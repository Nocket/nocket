package org.nocket.gen.page.element;

import gengui.domain.DomainObjectReference;

import org.jsoup.nodes.Element;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.domain.ref.WrappedDomainReferenceI;
import org.nocket.gen.page.DMDWebGenPageContext;

public abstract class AbstractNoDomainPageElement<M> extends AbstractPageElement<M> {

    public AbstractNoDomainPageElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Deprecated
    @Override
    public WrappedDomainReferenceI<DomainObjectReference> getAccessor() {
        throw new UnsupportedOperationException(getWicketId());
    }

    @Deprecated
    @Override
    public String getPropertyExpression() {
        throw new UnsupportedOperationException(getWicketId());
    }

    @Deprecated
    @Override
    public DomainElementI<DomainObjectReference> getDomainElement() {
        throw new UnsupportedOperationException(getWicketId());
    }

    @Deprecated
    @Override
    public String getBeanValidationExpression() {
        throw new UnsupportedOperationException(getWicketId());
    }

    @Deprecated
    @Override
    public String getBeanValidationPropertyName() {
        throw new UnsupportedOperationException(getWicketId());
    }

    @Override
    public boolean isDomainElement() {
        return false;
    }

}
