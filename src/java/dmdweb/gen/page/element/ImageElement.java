package dmdweb.gen.page.element;

import org.jsoup.nodes.Element;

import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.visitor.PageElementVisitorI;

public class ImageElement extends LinkElement {

    public ImageElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitImage(this);
    }

}
