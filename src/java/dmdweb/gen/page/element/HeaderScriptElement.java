package dmdweb.gen.page.element;

import org.jsoup.nodes.Element;

import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.visitor.PageElementVisitorI;

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
