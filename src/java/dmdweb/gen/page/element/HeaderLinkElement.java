package dmdweb.gen.page.element;

import org.jsoup.nodes.Element;

import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.visitor.PageElementVisitorI;

public class HeaderLinkElement extends AbstractPageHeaderElement {

    public HeaderLinkElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public String getResource() {
        return getElement().attr("href");
    }

    @Override
    public boolean isScript() {
        return false;
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        if (!irrelevant())
            visitor.visitHeaderLink(this);
    }

}
