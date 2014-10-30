package org.nocket.component.table.columns;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.nocket.component.table.GenericDataTablePanel;

// TODO: Auto-generated Javadoc
/**
 * Column with ajax link shown as image.
 * 
 * @param <T>
 *            Model object type representing one line in
 *            {@link GenericDataTablePanel} .
 */
@SuppressWarnings("serial")
public abstract class AjaxActionIconColumn<T> extends DMDAbstractColumn<T> {

    /** The Constant ICON_ENABLED. */
    private final static String ICON_ENABLED = "icon-enabled.png";
    
    /** The Constant ICON_DISBALED. */
    private final static String ICON_DISBALED = "icon-disabled.png";

    /**
     * Instantiates a new ajax action icon column.
     *
     * @param headerLabel the header label
     */
    public AjaxActionIconColumn(IModel<String> headerLabel) {
        this(headerLabel, null);
    }

    /**
     * Instantiates a new ajax action icon column.
     *
     * @param headerLabel the header label
     * @param image the image
     */
    public AjaxActionIconColumn(IModel<String> headerLabel, String image) {
        super(headerLabel);
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator#populateItem(org.apache.wicket.markup.repeater.Item, java.lang.String, org.apache.wicket.model.IModel)
     */
    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        cellItem.add(new LinkImagePanel<T>(componentId, this, rowModel));
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn#getCssClass()
     */
    @Override
    public String getCssClass() {
        return "org.nocket-action-column";
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
     * Returns reference to icon. Method should differentiate between enabled
     * and disabled icons.
     * 
     * @param model
     *            Model of the line.
     * 
     * @return Whether link is visible. Default is true.
     */
    protected ResourceReference getIcon(IModel<T> model) {
        if (isEnabled(model)) {
            return new PackageResourceReference(AjaxActionIconColumn.class, ICON_ENABLED);
        }
        return new PackageResourceReference(AjaxActionIconColumn.class, ICON_DISBALED);
    }

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
     * Called when a link is clicked.
     *
     * @param model            Model of the line.
     * @param target the target
     */
    protected abstract void onClick(IModel<T> model, AjaxRequestTarget target);

    /**
     * The Class LinkImagePanel.
     *
     * @param <T> the generic type
     */
    @SuppressWarnings("hiding")
    private class LinkImagePanel<T> extends Panel {

        /**
         * Instantiates a new link image panel.
         *
         * @param id the id
         * @param column the column
         * @param rowModel the row model
         */
        public LinkImagePanel(String id, final AjaxActionIconColumn<T> column, IModel<T> rowModel) {
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

            Image icon = new Image("icon", column.getIcon(rowModel)) {

                @Override
                protected boolean shouldAddAntiCacheParameter() {
                    return WebApplication.get().usesDevelopmentConfig();
                }

            };

            String tooltip = column.getTooltip(rowModel);
            if (tooltip != null) {
                icon.add(new AttributeAppender("title", tooltip));
            }

            link.add(icon);
        }

    }

}
