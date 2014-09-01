package org.nocket.gen.page.element;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.jsoup.nodes.Element;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class UnknownDomainElement extends AbstractDomainPageElement<Object> implements UnknownPageElementI<Object> {

    public UnknownDomainElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public IModel<Object> innerGetModel() {
        return new PropertyModel<Object>(getPropertyModelObject(), getPropertyExpression());
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitUnknown(this);
    }

}
