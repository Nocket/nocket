package org.nocket.gen.domain;

import org.nocket.gen.domain.visitor.DomainElementVisitorI;

import gengui.domain.AbstractDomainReference;

public class SinglePassStrategy<E extends AbstractDomainReference> extends
        DomainProcessorStrategy<E> {

    public SinglePassStrategy(DomainElementVisitorI<E> visitor) {
        this.visitor = visitor;
    }
}