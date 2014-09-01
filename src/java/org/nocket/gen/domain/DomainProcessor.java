package org.nocket.gen.domain;

import org.nocket.gen.domain.visitor.DomainElementVisitorI;

import gengui.domain.AbstractDomainReference;

public class DomainProcessor<E extends AbstractDomainReference> {

    private final DMDWebGenContext<E> context;

    private final DomainProcessorStrategy<E> strategy;

    public DomainProcessor(DMDWebGenContext<E> context, DomainProcessorStrategy<E> strategy) {
        this.context = context;
        this.strategy = strategy;
    }

    public DomainProcessor(DMDWebGenContext<E> context, DomainElementVisitorI<E> visitor) {
        this.context = context;
        this.strategy = new SinglePassStrategy<E>(visitor);
    }

    public void process() {
        strategy.process(context, context.getRefFactory().getRootReference());
    }
}
