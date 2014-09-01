package dmdweb.gen.domain.visitor.html.gen;

import gengui.domain.AbstractDomainReference;
import dmdweb.gen.domain.DMDWebGenContext;
import dmdweb.gen.domain.visitor.html.HtmlComponentBuilderBootstrap;

public class HtmlGeneratorVisitorBootstrap<E extends AbstractDomainReference> extends AbstractHtmlGeneratorVisitor<E> {

    public HtmlGeneratorVisitorBootstrap(DMDWebGenContext<E> context) {
        super(context, new HtmlComponentBuilderBootstrap(context));
    }

}
