package org.nocket.gen.page.visitor.bind;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.nocket.util.Assert;

// TODO: Auto-generated Javadoc
/**
 * The Class ComponentRegistry.
 */
public class ComponentRegistry implements Serializable {

    /** The wicket id_component. */
    private final Map<String, Component> wicketId_component = new HashMap<String, Component>();

    /**
     * Adds the component.
     *
     * @param component the component
     */
    public void addComponent(Component component) {
        if (wicketId_component.put(component.getId(), component) != null) {
            throw new IllegalArgumentException("Duplicate entry: " + component.getId());
        }
    }

    /**
     * Gets the component.
     *
     * @param wicketId the wicket id
     * @return the component
     */
    public Component getComponent(String wicketId) {
        return wicketId_component.get(wicketId);
    }

    /**
     * Gets the components.
     *
     * @return the components
     */
    public Collection<Component> getComponents() {
        return wicketId_component.values();
    }

    /**
     * Gets the components.
     *
     * @param <T> the generic type
     * @param clazz the clazz
     * @return the components
     */
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
