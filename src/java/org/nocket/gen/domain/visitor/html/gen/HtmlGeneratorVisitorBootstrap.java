package org.nocket.gen.domain.visitor.html.gen;

import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.visitor.html.HtmlComponentBuilderBootstrap;

import gengui.domain.AbstractDomainReference;

public class HtmlGeneratorVisitorBootstrap<E extends AbstractDomainReference> extends AbstractHtmlGeneratorVisitor<E> {

    public HtmlGeneratorVisitorBootstrap(DMDWebGenContext<E> context) {
        super(context, new HtmlComponentBuilderBootstrap(context));
    }

}
