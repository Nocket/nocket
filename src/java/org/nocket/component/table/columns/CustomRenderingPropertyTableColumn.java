package org.nocket.component.table.columns;

import java.io.Serializable;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.nocket.component.table.GenericDataTablePanel;
import org.nocket.component.table.GenericDataTablePanelPropertyColumn;
import org.nocket.component.table.columns.renderer.ColumnRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomRenderingPropertyTableColumn.
 *
 * @param <T> the generic type
 */
public class CustomRenderingPropertyTableColumn<T> extends GenericDataTablePanelPropertyColumn<T> implements
        Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The renderer. */
    private ColumnRenderer renderer;

    /**
     * Instantiates a new custom rendering property table column.
     *
     * @param displayModel the display model
     * @param sortProperty the sort property
     * @param propertyExpression the property expression
     * @param genericDataTablePanel the generic data table panel
     * @param renderer the renderer
     */
    public CustomRenderingPropertyTableColumn(IModel<String> displayModel, String sortProperty,
            String propertyExpression, GenericDataTablePanel genericDataTablePanel, ColumnRenderer renderer) {
        super(displayModel, sortProperty, propertyExpression, genericDataTablePanel);
        this.renderer = renderer;
    }

    /* (non-Javadoc)
     * @see org.nocket.component.table.GenericDataTablePanelPropertyColumn#populateItem(org.apache.wicket.markup.repeater.Item, java.lang.String, org.apache.wicket.model.IModel)
     */
    @Override
    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        cellItem.add(renderer.getComponentForTableCell(componentId, rowModel, this));
    }
}
