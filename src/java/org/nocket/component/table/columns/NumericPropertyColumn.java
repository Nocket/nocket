package org.nocket.component.table.columns;

import org.apache.wicket.model.IModel;

// TODO: Auto-generated Javadoc
/**
 * The Class NumericPropertyColumn.
 *
 * @param <T> the generic type
 */
public final class NumericPropertyColumn<T> extends DMDPropertyColumn<T> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new numeric property column.
     *
     * @param displayModel the display model
     * @param sortProperty the sort property
     * @param propertyExpression the property expression
     */
    public NumericPropertyColumn(IModel<String> displayModel, String sortProperty, String propertyExpression) {
        super(displayModel, sortProperty, propertyExpression);
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn#getCssClass()
     */
    @Override
    public String getCssClass() {
        return "org.nocket-numeric";
    }
}