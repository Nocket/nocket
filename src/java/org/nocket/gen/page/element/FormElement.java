package org.nocket.gen.page.element;

import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;
import org.nocket.component.form.DMDCompoundPropertyModel;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class FormElement extends AbstractNoDomainPageElement<Object> {

    public static final String DEFAULT_WICKET_ID = "form";

    public FormElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public IModel<Object> getModel() {
        return new DMDCompoundPropertyModel<Object>(getPropertyModelObject());
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitForm(this);
    }

}
