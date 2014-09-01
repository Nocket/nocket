package org.nocket.gen.page.element;

import org.jsoup.nodes.Element;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class HeaderScriptElement extends AbstractPageHeaderElement {

    public HeaderScriptElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public String getResource() {
        return getElement().attr("src");
    }

    @Override
    public boolean isScript() {
        return true;
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        if (!irrelevant())
            visitor.visitHeaderScript(this);
    }

}
