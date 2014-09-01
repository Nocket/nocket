package org.nocket.gen.page.element;

import org.jsoup.nodes.Element;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class ImageElement extends LinkElement {

    public ImageElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitImage(this);
    }

}
