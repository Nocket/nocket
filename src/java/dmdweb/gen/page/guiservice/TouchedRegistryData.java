package dmdweb.gen.page.guiservice;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import dmdweb.gen.page.element.synchronizer.TouchedListenerModelWrapper;

public class TouchedRegistryData implements Serializable {

    private Map<String, TouchedListenerModelWrapper<?>> models = new HashMap<String, TouchedListenerModelWrapper<?>>();

    public void registerModel(TouchedListenerModelWrapper<?> model) {
        if (this.models.put(model.getWicketId(), model) != null) {
            throw new IllegalArgumentException("Model for " + model.getWicketId() + " has already been added!");
        }
    }

    public Map<String, TouchedListenerModelWrapper<?>> getModels() {
        return models;
    }

}
