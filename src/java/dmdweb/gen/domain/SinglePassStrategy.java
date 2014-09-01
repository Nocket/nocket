package dmdweb.gen.domain;

import gengui.domain.AbstractDomainReference;
import dmdweb.gen.domain.visitor.DomainElementVisitorI;

public class SinglePassStrategy<E extends AbstractDomainReference> extends
        DomainProcessorStrategy<E> {

    public SinglePassStrategy(DomainElementVisitorI<E> visitor) {
        this.visitor = visitor;
    }
}