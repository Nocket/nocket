package org.nocket.component.table.columns;

import java.util.Set;

import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.model.IModel;

// TODO: Auto-generated Javadoc
/**
 * Column adds check-box per table item. Information about the checked state is
 * hold in the Set.
 * 
 * @author blaz02
 * 
 * @param <T>
 *            Model object type following Java-Bean convention.
 */
@SuppressWarnings("serial")
public class SetCheckBoxColum<T> extends CheckBoxColumn<T> {

    /** The selected. */
    private Set<T> selected;

    /**
     * Instantiates a new sets the check box colum.
     *
     * @param displayModel the display model
     * @param selected the selected
     */
    public SetCheckBoxColum(IModel<String> displayModel, Set<T> selected) {
        super(displayModel);
        this.selected = selected;
    }

    /* (non-Javadoc)
     * @see org.nocket.component.table.columns.CheckBoxColumn#newCheckBoxModel(org.apache.wicket.model.IModel)
     */
    @Override
    protected IModel<Boolean> newCheckBoxModel(final IModel<T> rowModel) {
        return new AbstractCheckBoxModel() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isSelected() {
                return selected.contains(rowModel.getObject());
            }

            @Override
            public void unselect() {
                selected.remove(rowModel.getObject());
            }

            @Override
            public void select() {
                selected.add(rowModel.getObject());
            }

            @Override
            public void detach() {
                rowModel.detach();
            }
        };
    }

}
