package dmdweb.component.table.columns;

import org.apache.wicket.model.IModel;

public final class NumericPropertyColumn<T> extends DMDPropertyColumn<T> {
    private static final long serialVersionUID = 1L;

    public NumericPropertyColumn(IModel<String> displayModel, String sortProperty, String propertyExpression) {
        super(displayModel, sortProperty, propertyExpression);
    }

    @Override
    public String getCssClass() {
        return "dmdweb-numeric";
    }
}