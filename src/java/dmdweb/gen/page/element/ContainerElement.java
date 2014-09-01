package dmdweb.gen.page.element;

import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;

import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.visitor.PageElementVisitorI;

public class ContainerElement extends AbstractDomainPageElement<Void> {

    public ContainerElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Deprecated
    @Override
    public IModel<Void> innerGetModel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitContainerOpen(this);
    }

}
