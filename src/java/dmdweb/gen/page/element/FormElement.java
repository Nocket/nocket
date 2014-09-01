package dmdweb.gen.page.element;

import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;

import dmdweb.component.form.DMDCompoundPropertyModel;
import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.visitor.PageElementVisitorI;

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
