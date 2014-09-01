package dmdweb.gen.page.element;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;

import dmdweb.gen.domain.visitor.html.AbstractHtmlComponentBuilder;
import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.element.synchronizer.LoadableDetachablePropertyModelObject;

public abstract class AbstractPageElement<M> implements PageElementI<M> {

    private transient final DMDWebGenPageContext context;
    private transient final Element element;
    private transient IModel<Object> propertyModelObject;

    public AbstractPageElement(DMDWebGenPageContext context, Element element) {
        this.context = context;
        this.element = element;
    }

    @Override
    public DMDWebGenPageContext getContext() {
        return context;
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public IModel<Object> getPropertyModelObject() {
        if (propertyModelObject == null) {
            propertyModelObject = new LoadableDetachablePropertyModelObject(getContext());
        }
        return propertyModelObject;
    }

    @Override
    public String getWicketId() {
        String wicketId = element.attr(AbstractHtmlComponentBuilder.ATTR_WICKET_ID);
        if (StringUtils.isBlank(wicketId)) {
            return null;
        } else {
            return wicketId;
        }
    }

    @Override
    public boolean enableThoughUnmodifiable() {
        return false;
    }

}
