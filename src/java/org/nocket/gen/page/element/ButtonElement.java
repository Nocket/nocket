package org.nocket.gen.page.element;

import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.synchronizer.ButtonCallback;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class ButtonElement extends AbstractDomainPageElement<Object> {

    public ButtonElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    public ButtonCallback getButtonCallback() {
        return new ButtonCallback(this);
    }

    @Deprecated
    @Override
    public IModel<Object> innerGetModel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitButton(this);
    }

}
