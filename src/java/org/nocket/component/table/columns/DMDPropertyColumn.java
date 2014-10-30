package org.nocket.component.table.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;

// TODO: Auto-generated Javadoc
/**
 * The Class DMDPropertyColumn.
 *
 * @param <T> the generic type
 */
public class DMDPropertyColumn<T> extends PropertyColumn<T, String> implements IDMDStyledColumn<T, String> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The colum style. */
    private String columStyle;

    /**
     * Instantiates a new DMD property column.
     *
     * @param displayModel the display model
     * @param sortProperty the sort property
     * @param propertyExpression the property expression
     */
    public DMDPropertyColumn(IModel<String> displayModel, String sortProperty, String propertyExpression) {
        super(displayModel, sortProperty, propertyExpression);
    }

    /**
     * Instantiates a new DMD property column.
     *
     * @param displayModel the display model
     * @param propertyExpression the property expression
     */
    public DMDPropertyColumn(IModel<String> displayModel, String propertyExpression) {
        super(displayModel, propertyExpression);
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
