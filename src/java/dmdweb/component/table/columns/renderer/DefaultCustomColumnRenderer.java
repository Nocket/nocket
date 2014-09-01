package dmdweb.component.table.columns.renderer;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import dmdweb.component.table.columns.CustomRenderingPropertyTableColumn;

public class DefaultCustomColumnRenderer<T> implements ColumnRenderer<T> {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getComponentForTableCell(String componentId, IModel<T> model,
            CustomRenderingPropertyTableColumn<T> column) {
        Label label = new Label(componentId, new PropertyModel(model, column.getPropertyExpression()));
        return label;
    }
}
