package org.nocket.gen.page.element;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jsoup.nodes.Element;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class DivElement extends AbstractNoDomainPageElement<String> {

    public DivElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitDiv(this);
    }

    //    @Override
    //    protected IModel<String> innerGetModel() {
    //        return Model.of("");
    //    }
    //
    @Override
    public IModel<String> getModel() {
        return Model.of("");
    }

}
