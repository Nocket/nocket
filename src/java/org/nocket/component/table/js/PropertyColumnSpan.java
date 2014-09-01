package org.nocket.component.table.js;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

public class PropertyColumnSpan<T> extends PropertyColumn<T, String> {

    private static final long serialVersionUID = 1L;
    private boolean renderSpan;

    public PropertyColumnSpan(IModel<String> displayModel, String propertyExpression) {
        this(displayModel, propertyExpression, true);
    }

    public PropertyColumnSpan(IModel<String> displayModel, String sortProperty, String propertyExpression) {
        this(displayModel, sortProperty, propertyExpression, true);
    }

    public PropertyColumnSpan(IModel<String> displayModel, String propertyExpression, boolean renderSpan) {
        super(displayModel, propertyExpression);
        this.renderSpan = renderSpan;
    }

    public PropertyColumnSpan(IModel<String> displayModel, String sortProperty, String propertyExpression,
            boolean renderSpan) {
        super(displayModel, sortProperty, propertyExpression);
        this.renderSpan = renderSpan;
    }

    @Override
    public void populateItem(Item<ICellPopulator<T>> item, String componentId, IModel<T> rowModel) {
        Label l = new Label(componentId, getDataModel(rowModel));
        l.setRenderBodyOnly(!renderSpan);
        item.add(l);
    }

}
