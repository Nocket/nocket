package org.nocket.gen.page.element;

import gengui.domain.DomainObjectReference;

import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.domain.ref.WrappedDomainReferenceI;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public interface PageElementI<M> {

    DMDWebGenPageContext getContext();

    Element getElement();

    String getWicketId();

    IModel<M> getModel();

    boolean isDomainElement();

    /**
     * Throws UnsupportedOperationException if isDomainElement() returns false.
     */
    WrappedDomainReferenceI<DomainObjectReference> getAccessor();

    /**
     * Throws UnsupportedOperationException if isDomainElement() returns false.
     */
    IModel<Object> getPropertyModelObject();

    /**
     * Throws UnsupportedOperationException if isDomainElement() returns false.
     */
    String getPropertyExpression();

    String getBeanValidationExpression();

    String getBeanValidationPropertyName();

    /**
     * Throws UnsupportedOperationException if isDomainElement() returns false.
     */
    DomainElementI<DomainObjectReference> getDomainElement();

    void accept(PageElementVisitorI visitor);

    /**
     * Returns true, if the page element is supposed to be enabled altough it
     * can't be modified. Domain object properties are modifiable when they have
     * a setter and when the setter is not explicitely declared to be
     * unaccessible for UI usage (directly or indirectly). There are only a very
     * few page elements which need to be kept enabled anyway, e.g. download
     * buttons and links.
     */
    boolean enableThoughUnmodifiable();

}
