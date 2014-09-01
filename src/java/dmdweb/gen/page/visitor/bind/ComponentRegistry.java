package dmdweb.gen.page.visitor.bind;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;

import de.bertelsmann.coins.general.error.Assert;

public class ComponentRegistry implements Serializable {

    private final Map<String, Component> wicketId_component = new HashMap<String, Component>();

    public void addComponent(Component component) {
        if (wicketId_component.put(component.getId(), component) != null) {
            throw new IllegalArgumentException("Duplicate entry: " + component.getId());
        }
    }

    public Component getComponent(String wicketId) {
        return wicketId_component.get(wicketId);
    }

    public Collection<Component> getComponents() {
        return wicketId_component.values();
    }

    @SuppressWarnings("unchecked")
    public <T> Collection<? extends T> getComponents(Class<T> clazz) {
        Assert.notNull(clazz, "Clazz is null");

        List<T> result = new ArrayList<T>();
        for (Component component : wicketId_component.values()) {
            if (clazz.isAssignableFrom(component.getClass())) {
                result.add((T) component);
            }
        }
        return result;
    }

}
