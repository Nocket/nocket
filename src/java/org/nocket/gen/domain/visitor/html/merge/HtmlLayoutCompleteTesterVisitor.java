package org.nocket.gen.domain.visitor.html.merge;

import gengui.domain.AbstractDomainReference;

import org.apache.ecs.Element;
import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.domain.visitor.html.AbstractHtmlComponentBuilder;

public class HtmlLayoutCompleteTesterVisitor<E extends AbstractDomainReference> extends AbstractHtmlMergeVisitor<E> {

    @SuppressWarnings("serial")
    public static class UnknownMemberFoundException extends RuntimeException {

        private final String wicketId;

        public UnknownMemberFoundException(String wicketId) {
            super("Found unknown member with wicket id = " + wicketId);
            this.wicketId = wicketId;
        }

        public String getWicketId() {
            return wicketId;
        }

    }

    public HtmlLayoutCompleteTesterVisitor(DMDWebGenContext<E> context, AbstractHtmlComponentBuilder componentBuilder) {
        super(context, componentBuilder);
    }

    protected void maybeAdd(DomainElementI<E> e, Element... components) {
        if (document.getElementsByAttributeValue("wicket:id", e.getWicketId()).isEmpty()) {
            throw new UnknownMemberFoundException(e.getWicketId());
        }
    }

    @Override
    public void finish() {
        // do nothing
    }
}
