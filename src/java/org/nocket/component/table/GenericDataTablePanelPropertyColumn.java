package org.nocket.component.table;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.nocket.component.table.GenericDataTablePanel.Tupel;
import org.nocket.component.table.columns.DMDPropertyColumn;

public class GenericDataTablePanelPropertyColumn<T> extends DMDPropertyColumn<T> implements Serializable {

    protected final GenericDataTablePanel genericDataTablePanel;
    protected final String column;
    protected transient Component headerComponent;

    public GenericDataTablePanelPropertyColumn(IModel<String> displayModel, String sortProperty,
            String propertyExpression, GenericDataTablePanel genericDataTablePanel) {
        super(displayModel, sortProperty, propertyExpression);
        this.genericDataTablePanel = genericDataTablePanel;
        this.column = propertyExpression;
    }

    @Override
    public void populateItem(Item<ICellPopulator<T>> item, String componentId, IModel<T> rowModel) {
        item.add(new Label(componentId, createLabelModel(rowModel)) {
            @Override
            public IConverter getConverter(Class type) {
                String columnName = column;
                if (genericDataTablePanel.getColumnConverter().containsKey(columnName)) {
                    GenericDataTablePanel.Tupel t = (Tupel) genericDataTablePanel.getColumnConverter().get(columnName);
                    if (type.isAssignableFrom(type) && t.two != null) {
                        return t.two;
                    }
                }
                return super.getConverter(type);
            }
        });
    }

    @Override
    public Component getHeader(String componentId) {
        // We keep the header component in transient mind for later manipulation
        // to support HTML-formated labels. The appropriate modifcation can't be performed
        // here because as long as the label ist not attached to its parent, the resource access
        // won't work.
        if (headerComponent == null) {
            headerComponent = super.getHeader(componentId);
            headerComponent.add(new AttributeAppender("class", "header" + genericDataTablePanel.getColumnCount())
                    .setSeparator(" "));
            genericDataTablePanel.setColumnCount(genericDataTablePanel.getColumnCount() + 1);
        }
        return headerComponent;
    }

}
