package org.nocket.gen.page.element;

import gengui.domain.DomainObjectReference;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.jsoup.nodes.Element;
import org.nocket.gen.domain.element.SimplePropertyElement;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class TextInputElement extends AbstractDomainPageElement<Object> {

    public TextInputElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public IModel<Object> innerGetModel() {
        return new PropertyModel<Object>(getPropertyModelObject(), getPropertyExpression());
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitTextInput(this);
    }

    @Override
    public SimplePropertyElement<DomainObjectReference> getDomainElement() {
        return (SimplePropertyElement<DomainObjectReference>) super.getDomainElement();
    }

}
