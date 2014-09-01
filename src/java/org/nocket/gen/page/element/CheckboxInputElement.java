package org.nocket.gen.page.element;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.jsoup.nodes.Element;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class CheckboxInputElement extends AbstractDomainPageElement<Boolean> {

    public CheckboxInputElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public IModel<Boolean> innerGetModel() {
        return new PropertyModel<Boolean>(getPropertyModelObject(), getPropertyExpression());
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitCheckboxInput(this);
    }

}
