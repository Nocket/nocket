package dmdweb.component.table.columns.renderer;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import dmdweb.component.table.columns.CustomRenderingPropertyTableColumn;

public interface ColumnRenderer<T> extends Serializable {

    /**
     * Get the wicket component that should be rendered in the table cell for
     * which this renderer has been defined
     * 
     */
    public Component getComponentForTableCell(String componentId, IModel<T> model,
            CustomRenderingPropertyTableColumn<T> column);

}
