package org.nocket.component.table.columns;

import java.io.File;
import java.util.MissingResourceException;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.time.Duration;
import org.nocket.component.table.GenericDataTablePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * Column with file download link shown as image.
 * 
 * @param <T>
 *            Model object type representing one line in
 *            {@link GenericDataTablePanel} .
 */
// TODO JL: There is a lot of copy-paste code in this class which is stolen from AjaxSubmitActionIconColumn
// This definitely needs refactoring but I'm not familiar enough with the various Wicket details at this point
// to figure out how the refactoring must look like.
@SuppressWarnings("serial")
public abstract class DownloadActionIconColumn<T> extends DMDAbstractColumn<T> {
    
    /** The Constant LOG. */
    public static final Logger LOG = LoggerFactory.getLogger(DownloadActionIconColumn.class);
    
    /** The Constant ICON_ENABLED. */
    public static final String ICON_ENABLED = AjaxSubmitActionIconColumn.ICON_ENABLED;
    
    /** The Constant ICON_DISBALED. */
    public static final String ICON_DISBALED = AjaxSubmitActionIconColumn.ICON_DISBALED;
    
    /** The Constant ACTION_COLUMN. */
    public static final String ACTION_COLUMN = AjaxSubmitActionIconColumn.ACTION_COLUMN;

    /** The enabled icon property key. */
    protected final String enabledIconPropertyKey;
    
    /** The disabled icon property key. */
    protected final String disabledIconPropertyKey;

    /** The header component. */
    private transient Component headerComponent;

    /**
     * Instantiates a new download action icon column.
     *
     * @param headerLabel the header label
     */
    public DownloadActionIconColumn(IModel<String> headerLabel) {
        this(headerLabel, null, null);
    }

    /**
     * Instantiates a new download action icon column.
     *
     * @param headerLabel the header label
     * @param enabledContextIcon the enabled context icon
     * @param disabledContextIcon the disabled context icon
     */
    public DownloadActionIconColumn(IModel<String> headerLabel, String enabledContextIcon, String disabledContextIcon) {
        super(headerLabel, null);
        this.enabledIconPropertyKey = enabledContextIcon;
        this.disabledIconPropertyKey = disabledContextIcon;
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator#populateItem(org.apache.wicket.markup.repeater.Item, java.lang.String, org.apache.wicket.model.IModel)
     */
    @Override
    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        cellItem.add(new DownloadLinkImagePanel(componentId, this, rowModel, enabledIconPropertyKey,
                disabledIconPropertyKey));
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn#getCssClass()
     */
    @Override
    public String getCssClass() {
        return ACTION_COLUMN;
    }

    /**
     * File model.
     *
     * @param rowModel the row model
     * @return the i model
     */
    protected abstract IModel<File> fileModel(IModel<T> rowModel);

    /**
     * Checks if is enabled.
     *
     * @param model the model
     * @return true, if is enabled
     */
    protected boolean isEnabled(IModel<T> model) {
        return true;
    }

    /**
     * Checks if is visible.
     *
     * @param model the model
     * @return true, if is visible
     */
    protected boolean isVisible(IModel<T> model) {
        return true;
    }

    /**
     * Gets the tooltip.
     *
     * @param model the model
     * @return the tooltip
     */
    protected String getTooltip(IModel<T> model) {
        return null;
    }

    /**
     * Returns image for the link as Wicket resource. The name of the image is
     * determined from property file. If property is not found or name is empty,
     * default icons will be displayed. Method differentiates between enabled
     * and disabled icons.
     *
     * @param model            Model of the line.
     * @param c the c
     * @return Image as Wicket resource.
     */
    protected IResource getIcon(IModel<T> model, Component c) {
        String resourceName = getIconResourceName(model, c);
        if (!StringUtils.isEmpty(resourceName)) {
            return new ContextRelativeResource(resourceName);
        }
        return getDefaultIcon(model);
    }

    /**
     * Returns name of the icon from property key. Default implementation
     * resolves it with Wickets localizer. Overwrite it if you want something
     * else.
     *
     * @param model the model
     * @param c the c
     * @return name Path to the icon from root context. For instance:
     *         "img/image.gif" will be lookup in "webapp/img/image.gif"
     */
    protected String getIconResourceName(IModel<T> model, Component c) {
        try {
            String propertyKey = this.isEnabled(model) ? enabledIconPropertyKey : disabledIconPropertyKey;
            return Localizer.get().getString(propertyKey, c.getPage());
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * Returns reference to default icons. Method differentiates between enabled
     * and disabled icons.
     * 
     * @param model
     *            Model of the line.
     * 
     * @return Image as Wicket resource.
     */

    protected IResource getDefaultIcon(IModel<T> model) {
        if (isEnabled(model)) {
            return new PackageResourceReference(AjaxSubmitActionIconColumn.class, ICON_ENABLED).getResource();
        }
        return new PackageResourceReference(AjaxSubmitActionIconColumn.class, ICON_DISBALED).getResource();
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn#getHeader(java.lang.String)
     */
    @Override
    public Component getHeader(String componentId) {
        if (headerComponent == null) {
            headerComponent = super.getHeader(componentId);
        }
        return headerComponent;
    }

    /**
     * The Class DownloadLinkImagePanel.
     */
    protected class DownloadLinkImagePanel extends Panel {
        
        /** The Constant ICON. */
        private static final String ICON = "icon";
        
        /** The Constant TITLE. */
        private static final String TITLE = "title";

        /**
         * Instantiates a new download link image panel.
         *
         * @param id the id
         * @param column the column
         * @param rowModel the row model
         * @param enabledContextIcon the enabled context icon
         * @param disabledContextIcon the disabled context icon
         */
        public DownloadLinkImagePanel(String id, final DownloadActionIconColumn<T> column, final IModel<T> rowModel,
                final String enabledContextIcon, final String disabledContextIcon) {
            super(id);

            DownloadLink link = new DownloadLink("link", fileModel(rowModel)) {
                @Override
                public boolean isEnabled() {
                    return column.isEnabled(rowModel);
                }

                @Override
                public boolean isVisible() {
                    return column.isVisible(rowModel);
                }

                /**
                 * @see org.apache.wicket.Component#onInitialize()
                 */
                @Override
                protected void onInitialize() {
                    super.onInitialize();

                    final WebComponent icon = createIcon(column, rowModel);
                    final String tooltip = column.getTooltip(rowModel);
                    if (tooltip != null) {
                        icon.add(new AttributeAppender(TITLE, tooltip));
                    }
                    add(icon);
                }

                private WebComponent createIcon(final DownloadActionIconColumn<T> column, final IModel<T> rowModel) {
                    return new Image(ICON, column.getIcon(rowModel, this)) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        protected boolean shouldAddAntiCacheParameter() {
                            return WebApplication.get().usesDevelopmentConfig();
                        }
                    };
                }

                @Override
                public void onClick() {
                    try {
                        super.onClick();
                    } catch (IllegalStateException e) {
                        LOG.warn("Download method of class " + rowModel.getObject().getClass() + " returned no file.");
                    }
                }

            };
            link.setCacheDuration(Duration.NONE);
            add(link);

        }
    }

}
