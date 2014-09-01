package dmdweb.component.table.columns;

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

import dmdweb.component.table.GenericDataTablePanel;

/**
 * Column with ajax link shown as image.
 * 
 * @param <T>
 *            Model object type representing one line in
 *            {@link GenericDataTablePanel} .
 */
@SuppressWarnings("serial")
public abstract class AjaxActionIconColumn<T> extends DMDAbstractColumn<T> {

    private final static String ICON_ENABLED = "icon-enabled.png";
    private final static String ICON_DISBALED = "icon-disabled.png";

    public AjaxActionIconColumn(IModel<String> headerLabel) {
        this(headerLabel, null);
    }

    public AjaxActionIconColumn(IModel<String> headerLabel, String image) {
        super(headerLabel);
    }

    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        cellItem.add(new LinkImagePanel<T>(componentId, this, rowModel));
    }

    @Override
    public String getCssClass() {
        return "dmdweb-action-column";
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
     * @param model
     *            Model of the line.
     */
    protected abstract void onClick(IModel<T> model, AjaxRequestTarget target);

    @SuppressWarnings("hiding")
    private class LinkImagePanel<T> extends Panel {

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
