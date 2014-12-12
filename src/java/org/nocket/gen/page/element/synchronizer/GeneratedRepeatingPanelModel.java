package org.nocket.gen.page.element.synchronizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;
import org.nocket.gen.page.element.RepeatingPanelElement;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneratedRepeatingPanelModel.
 */
@SuppressWarnings("serial")
public class GeneratedRepeatingPanelModel extends LoadableDetachableModel<List<?>> {

    /** The helper. */
    private final SynchronizerHelper helper;

    /**
     * Instantiates a new generated repeating panel model.
     *
     * @param element the element
     */
    public GeneratedRepeatingPanelModel(RepeatingPanelElement element) {
        this.helper = new SynchronizerHelper(element);
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.model.LoadableDetachableModel#load()
     */
    @Override
    protected List<?> load() {
        Object value = helper.invokeGetterMethod();
        if (value == null) {
            return Collections.emptyList();
        } else if (value instanceof Object[]) {
            return Arrays.asList((Object[]) value);
        } else if (value instanceof List) {
            return (List) value;
        } else if (value instanceof Collection) {
            return new ArrayList((Collection) value);
        } else {
            return Arrays.asList(value);
        }
    }

}
