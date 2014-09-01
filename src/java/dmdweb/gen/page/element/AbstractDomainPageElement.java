package dmdweb.gen.page.element;

import gengui.domain.DomainObjectReference;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;

import dmdweb.gen.domain.element.DomainElementI;
import dmdweb.gen.domain.ref.WrappedDomainReferenceI;
import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.element.synchronizer.TouchedListenerModelWrapperFactory;

public abstract class AbstractDomainPageElement<M> extends AbstractPageElement<M> {

    protected transient IModel<M> model;

    public AbstractDomainPageElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public WrappedDomainReferenceI<DomainObjectReference> getAccessor() {
        return getDomainElement().getAccessor();
    }

    @Override
    public IModel<M> getModel() {
        if (model == null) {
            model = TouchedListenerModelWrapperFactory.create(this, innerGetModel());
        }
        return model;
    }

    protected abstract IModel<M> innerGetModel();

    @Override
    public String getPropertyExpression() {
        String expression = "";
        String[] pathElements = getWicketId().split("\\.");
        for (String pathElement : pathElements) {
            expression += StringUtils.uncapitalize(pathElement) + ".";
        }
        expression = StringUtils.removeEnd(expression, ".");
        return expression;
    }

    @Override
    public DomainElementI<DomainObjectReference> getDomainElement() {
        String wicketId = getWicketId();
        if (wicketId != null) {
            return getContext().getDomainRegistry().getElement(wicketId);
        } else {
            return null;
        }
    }

    @Override
    public boolean isDomainElement() {
        return true;
    }

    @Override
    public String getBeanValidationExpression() {
        // forscher.dmdweb.page.gen.MergeInner.feld1
        String path = getAccessor().getClassRef().getDomainClass().getName() + "." + getBeanValidationPropertyName();
        return path;
    }

    @Override
    public String getBeanValidationPropertyName() {
        return StringUtils.uncapitalize(getDomainElement().getPropertyName());
    }

}
