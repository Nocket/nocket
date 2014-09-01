package dmdweb.gen.domain.visitor.html.merge;

import org.apache.ecs.Element;

public abstract class AbstractMergeLayer<E> {
    
    private final E panel;
    private int countElements;
    
    public AbstractMergeLayer(E panel){
        this.panel = panel;
    }
    
    public void addElement(Element element){
        innerAddElement(element);
        countElements++;
    }
    
    protected abstract void innerAddElement(Element element);

    public E getPanel() {
        return panel;
    }
    
    public int getCountElements() {
        return countElements;
    }
}
