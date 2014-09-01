package org.nocket.component.table.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;

public class DMDPropertyColumn<T> extends PropertyColumn<T, String> implements IDMDStyledColumn<T, String> {

    private static final long serialVersionUID = 1L;

    private String columStyle;

    public DMDPropertyColumn(IModel<String> displayModel, String sortProperty, String propertyExpression) {
        super(displayModel, sortProperty, propertyExpression);
    }

    public DMDPropertyColumn(IModel<String> displayModel, String propertyExpression) {
        super(displayModel, propertyExpression);
    }

    public void setColumnStyle(String style) {
        this.columStyle = style;
    }

    @Override
    public String getCssStyleAttribute() {
        return columStyle;
    }
}
