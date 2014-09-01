package org.nocket.gen.page.element;

import org.jsoup.nodes.Element;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.visitor.PageElementVisitorI;

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
