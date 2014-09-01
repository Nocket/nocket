package dmdweb.gen.page.element;

import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;

import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.visitor.PageElementVisitorI;

public class GroupTabbedPanelElement extends AbstractNoDomainPageElement {

    public GroupTabbedPanelElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitGroupTabbedPanel(this);
    }

    @Override
    public IModel<?> getModel() {
        return getContext().getPage().getDefaultModel();
    }

}
