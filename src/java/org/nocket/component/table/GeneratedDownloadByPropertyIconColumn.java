package org.nocket.component.table;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.nocket.component.table.columns.DownloadActionIconColumn;
import org.nocket.gen.page.element.synchronizer.TableDownloadCallback;

/**
 * Column with file download link shown as image generated for read-only
 * properties of the table entry with return type: java.io.File.
 * 
 * @param <T>
 *            Model object type representing one line in
 *            {@link GenericDataTablePanel} .
 */
public class GeneratedDownloadByPropertyIconColumn<T> extends DownloadActionIconColumn<T> {

    private static final long serialVersionUID = 1L;

    protected final TableDownloadCallback downloadCallback;

    public GeneratedDownloadByPropertyIconColumn(IModel<String> header, TableDownloadCallback downloadCallback,
            String enabledIconResource, String disabledIconResource) {
        super(header, enabledIconResource, disabledIconResource);
        this.downloadCallback = downloadCallback;
    }

    @Override
    protected IModel<File> fileModel(IModel<T> rowModel) {
        return new PropertyModel<File>(rowModel.getObject(), StringUtils.uncapitalize(downloadCallback
                .getPropertyName()));
    }

    @Override
    protected boolean isEnabled(IModel<T> model) {
        return downloadCallback.isEnabled(model.getObject());
    }

    @Override
    protected String getTooltip(IModel<T> model) {
        return downloadCallback.getTooltip(model.getObject());
    }

    @Override
    protected String getIconResourceName(IModel<T> model, Component c) {
        return downloadCallback.getIconResourceName(model.getObject(), c);
    }
};
