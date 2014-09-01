package dmdweb.gen.page.element;

import gengui.domain.DomainObjectReference;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.jsoup.nodes.Element;

import dmdweb.gen.domain.element.SimplePropertyElement;
import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.visitor.PageElementVisitorI;

public class LabelElement extends AbstractDomainPageElement<Object> {

    public LabelElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public IModel<Object> innerGetModel() {
        return new PropertyModel<Object>(getPropertyModelObject(), getPropertyExpression());
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitLabel(this);
    }

    @Override
    public SimplePropertyElement<DomainObjectReference> getDomainElement() {
        return (SimplePropertyElement<DomainObjectReference>) super.getDomainElement();
    }

}
