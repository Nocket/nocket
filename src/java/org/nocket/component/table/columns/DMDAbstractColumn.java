package org.nocket.component.table.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.model.IModel;

// TODO: Auto-generated Javadoc
/**
 * The Class DMDAbstractColumn.
 *
 * @param <T> the generic type
 */
public abstract class DMDAbstractColumn<T> extends AbstractColumn<T, String> implements IDMDStyledColumn<T, String> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The colum style. */
    private String columStyle;

    /**
     * Instantiates a new DMD abstract column.
     *
     * @param displayModel the display model
     */
    public DMDAbstractColumn(IModel<String> displayModel) {
        super(displayModel);
    }

    /**
     * Instantiates a new DMD abstract column.
     *
     * @param displayModel the display model
     * @param sortProperty the sort property
     */
    public DMDAbstractColumn(final IModel<String> displayModel, final String sortProperty) {
        super(displayModel, sortProperty);
    }

    /**
     * Sets the column style.
     *
     * @param style the new column style
     */
    public void setColumnStyle(String style) {
        this.columStyle = style;
    }

    /* (non-Javadoc)
     * @see org.nocket.component.table.columns.IDMDStyledColumn#getCssStyleAttribute()
     */
    @Override
    public String getCssStyleAttribute() {
        return columStyle;
    }

}
