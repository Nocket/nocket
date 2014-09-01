package org.nocket.gen.domain.visitor.html.merge;

import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.visitor.html.HtmlComponentBuilderBootstrap;

import gengui.domain.AbstractDomainReference;

public class HtmlMergeVisitorBootstrap<E extends AbstractDomainReference> extends AbstractHtmlMergeVisitor<E> {

    public HtmlMergeVisitorBootstrap(DMDWebGenContext<E> context) {
        super(context, new HtmlComponentBuilderBootstrap(context));
    }

}
