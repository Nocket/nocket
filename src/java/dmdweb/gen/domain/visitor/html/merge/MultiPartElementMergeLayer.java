package dmdweb.gen.domain.visitor.html.merge;

import org.apache.ecs.Element;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.html.FieldSet;

public class MultiPartElementMergeLayer extends AbstractMergeLayer<MultiPartElement> {

    public MultiPartElementMergeLayer(FieldSet panel) {
        super(panel);
    }

    @Override
    protected void innerAddElement(Element element) {
        getPanel().addElementToRegistry(element);
    }

}
