package org.nocket.gen.page.element;

import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class ModalElement extends AbstractNoDomainPageElement<Void> {

    public static final String DEFAULT_WICKET_ID = "modal";

    public ModalElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Deprecated
    @Override
    public IModel<Void> getModel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitModal(this);
    }

}
