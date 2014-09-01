package org.nocket.component.table.columns;

import java.io.Serializable;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.nocket.component.table.GenericDataTablePanel;
import org.nocket.component.table.GenericDataTablePanelPropertyColumn;
import org.nocket.component.table.columns.renderer.ColumnRenderer;

public class CustomRenderingPropertyTableColumn<T> extends GenericDataTablePanelPropertyColumn<T> implements
        Serializable {

    private static final long serialVersionUID = 1L;
    private ColumnRenderer renderer;

    public CustomRenderingPropertyTableColumn(IModel<String> displayModel, String sortProperty,
            String propertyExpression, GenericDataTablePanel genericDataTablePanel, ColumnRenderer renderer) {
        super(displayModel, sortProperty, propertyExpression, genericDataTablePanel);
        this.renderer = renderer;
    }

    @Override
    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        cellItem.add(renderer.getComponentForTableCell(componentId, rowModel, this));
    }
}
