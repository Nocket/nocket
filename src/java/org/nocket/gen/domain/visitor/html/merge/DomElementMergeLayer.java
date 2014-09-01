package org.nocket.gen.domain.visitor.html.merge;

import org.apache.ecs.Element;

public class DomElementMergeLayer extends AbstractMergeLayer<org.jsoup.nodes.Element> {

    public DomElementMergeLayer(org.jsoup.nodes.Element panel) {
        super(panel);
    }

    @Override
    protected void innerAddElement(Element element) {
        getPanel().append(element.toString());
    }
    
}
