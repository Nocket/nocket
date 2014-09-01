package dmdweb.gen.domain.visitor.html.merge;

import gengui.domain.AbstractDomainReference;
import dmdweb.gen.domain.DMDWebGenContext;
import dmdweb.gen.domain.visitor.html.HtmlComponentBuilderTablegrid;

public class HtmlMergeVisitorTablegrid<E extends AbstractDomainReference> extends AbstractHtmlMergeVisitor<E> {

    public HtmlMergeVisitorTablegrid(DMDWebGenContext<E> context) {
        super(context, new HtmlComponentBuilderTablegrid(context));
    }

}
