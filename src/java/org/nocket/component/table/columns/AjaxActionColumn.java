package org.nocket.component.table.columns;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.nocket.component.table.GenericDataTablePanel;

// TODO: Auto-generated Javadoc
/**
 * Column with Ajax link.
 * 
 * @param <T>
 *            Model object type representing one line in
 *            {@link GenericDataTablePanel} .
 */
@SuppressWarnings("serial")
public abstract class AjaxActionColumn<T> extends DMDAbstractColumn<T> {

    /**
     * Instantiates a new ajax action column.
     *
     * @param headerLabel the header label
     */
    public AjaxActionColumn(IModel<String> headerLabel) {
        super(headerLabel);
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator#populateItem(org.apache.wicket.markup.repeater.Item, java.lang.String, org.apache.wicket.model.IModel)
     */
    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        cellItem.add(new LinkPanel<T>(componentId, this, rowModel));
    }

    /**
     * Defines whether link is enabled. You may decide to implement special
     * behavior for each line, depending on the model object.
     * 
     * @param model
     *            Model of the line.
     * 
     * @return Whether link is enabled. Default is true.
     */
    protected boolean isEnabled(IModel<T> model) {
        return true;
    }

    /**
     * Defines whether link is visible. You may decide to implement special
     * behavior for each line, depending on the model object.
     * 
     * @param model
     *            Model of the line.
     * 
     * @return Whether link is visible. Default is true.
     */
    protected boolean isVisible(IModel<T> model) {
        return true;
    }

    /**
     * Returns label for the link.
     * 
     * @param model
     *            Model of the line.
     * 
     * @return Label for the string.
     */
    protected abstract IModel<String> getCellLabel(IModel<T> model);

    /**
     * Called when a link is clicked.
     *
     * @param clicked the clicked
     * @param target the target
     */
    protected abstract void onClick(IModel<T> clicked, AjaxRequestTarget target);

    /**
     * Returns tool-tip for the icon.
     * 
     * @param model
     *            Model of the line.
     * 
     * @return Default is null.
     */
    protected String getTooltip(IModel<T> model) {
        return null;
    }

    /**
     * The Class LinkPanel.
     *
     * @param <T> the generic type
     */
    @SuppressWarnings("hiding")
    private class LinkPanel<T> extends Panel {

        /**
         * Instantiates a new link panel.
         *
         * @param id the id
         * @param column the column
         * @param rowModel the row model
         */
        public LinkPanel(String id, final AjaxActionColumn<T> column, IModel<T> rowModel) {
            super(id);
            AjaxLink<T> link = new AjaxLink<T>("link", rowModel) {

                @Override
                public void onClick(AjaxRequestTarget target) {
                    column.onClick(getModel(), target);

                }

                @Override
                public boolean isEnabled() {
                    return column.isEnabled(getModel());
                }

                @Override
                public boolean isVisible() {
                    return column.isVisible(getModel());
                }

            };
            add(link);

            String tooltip = column.getTooltip(rowModel);
            if (tooltip != null) {
                link.add(new AttributeAppender("title", tooltip));
            }

            link.add(new Label("label", column.getCellLabel(rowModel)));
        }

    }

}
