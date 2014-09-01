package dmdweb.gen.page.visitor.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import dmdweb.gen.page.element.BodyElement;
import dmdweb.gen.page.element.PageElementI;

public class PageRegistry {

    private final Map<String, PageElementI<?>> wicketId_element = new HashMap<String, PageElementI<?>>();
    private BodyElement bodyElement;

    void addElement(PageElementI<?> element) {
        if (wicketId_element.put(element.getWicketId(), element) != null) {
            throw new IllegalArgumentException("Duplicate entry: "
                    + element.getWicketId());
        }
    }

    public PageElementI<?> getElement(String wicketId) {
        return wicketId_element.get(wicketId);
    }

    public Collection<PageElementI<?>> getElements() {
        return wicketId_element.values();
    }

    public void setBodyElement(BodyElement bodyElement) {
        this.bodyElement = bodyElement;
    }

    public BodyElement getBodyElement() {
        return bodyElement;
    }

}
