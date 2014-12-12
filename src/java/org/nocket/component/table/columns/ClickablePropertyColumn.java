package org.nocket.component.table.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

// TODO: Auto-generated Javadoc
/**
 * The Class ClickablePropertyColumn.
 *
 * @param <T> the generic type
 */
public abstract class ClickablePropertyColumn<T> extends DMDAbstractColumn<T> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The property. */
    private final String property;

    /**
     * Instantiates a new clickable property column.
     *
     * @param displayModel the display model
     * @param property the property
     */
    public ClickablePropertyColumn(IModel<String> displayModel, String property) {
        this(displayModel, property, null);
    }

    /**
     * Instantiates a new clickable property column.
     *
     * @param displayModel the display model
     * @param property the property
     * @param sort the sort
     */
    public ClickablePropertyColumn(IModel<String> displayModel, String property, String sort) {
        super(displayModel, sort);
        this.property = property;
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator#populateItem(org.apache.wicket.markup.repeater.Item, java.lang.String, org.apache.wicket.model.IModel)
     */
    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        cellItem.add(new LinkPanel(componentId, rowModel, new PropertyModel<Object>(rowModel, property)));
    }

    /**
     * On click.
     *
     * @param clicked the clicked
     */
    protected abstract void onClick(IModel<T> clicked);

    /**
     * The Class LinkPanel.
     */
    private class LinkPanel extends Panel {
        
        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1L;

        /**
         * Instantiates a new link panel.
         *
         * @param id the id
         * @param rowModel the row model
         * @param labelModel the label model
         */
        public LinkPanel(String id, IModel<T> rowModel, IModel<?> labelModel) {
            super(id);
            Link<T> link = new Link<T>("link", rowModel) {
                private static final long serialVersionUID = 1L;

                @Override
                public void onClick() {
                    ClickablePropertyColumn.this.onClick(getModel());
                }
            };
            add(link);
            link.add(new Label("label", labelModel));
        }
    }
}