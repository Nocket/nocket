package dmdweb.gen.domain.visitor.html.merge;

import gengui.domain.AbstractDomainReference;
import dmdweb.gen.domain.DMDWebGenContext;
import dmdweb.gen.domain.visitor.html.HtmlComponentBuilderBootstrap;

public class HtmlMergeVisitorBootstrap<E extends AbstractDomainReference> extends AbstractHtmlMergeVisitor<E> {

    public HtmlMergeVisitorBootstrap(DMDWebGenContext<E> context) {
        super(context, new HtmlComponentBuilderBootstrap(context));
    }

}
