package org.nocket.gen.domain.visitor.html.merge;

import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.visitor.html.HtmlComponentBuilderTablegrid;

import gengui.domain.AbstractDomainReference;

public class HtmlMergeVisitorTablegrid<E extends AbstractDomainReference> extends AbstractHtmlMergeVisitor<E> {

    public HtmlMergeVisitorTablegrid(DMDWebGenContext<E> context) {
        super(context, new HtmlComponentBuilderTablegrid(context));
    }

}
