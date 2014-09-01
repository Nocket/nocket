package org.nocket.component.table;

import java.io.File;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.nocket.component.table.columns.DownloadActionIconColumn;
import org.nocket.gen.page.element.synchronizer.TableButtonCallback;

/**
 * Column with file download link shown as image generated for method (not the
 * property !!!) of the table entry with return type: {@link java.io.File}.
 * 
 * @param <T>
 *            Model object type representing one line in
 *            {@link GenericDataTablePanel} .
 */
public class GeneratedDownloadByMethodIconColumn<T> extends DownloadActionIconColumn<T> {

    private static final long serialVersionUID = 1L;

    protected final TableButtonCallback buttonCallback;

    public GeneratedDownloadByMethodIconColumn(IModel<String> header,
            String enabledIconResource, String disabledIconResource, TableButtonCallback buttonCallback) {
        super(header, enabledIconResource, disabledIconResource);
        this.buttonCallback = buttonCallback;
    }

    @Override
    protected IModel<File> fileModel(final IModel<T> rowModel) {
        return new LoadableDetachableModel<File>() {
            private static final long serialVersionUID = 1L;

            @Override
            protected File load() {
                return buttonCallback.onDownloadMethod(rowModel);
            }
        };
    }

    @Override
    protected boolean isEnabled(IModel<T> model) {
        return buttonCallback.isEnabled(model.getObject());
    }

    @Override
    protected String getTooltip(IModel<T> model) {
        return buttonCallback.getTooltip(model.getObject());
    }

    @Override
    protected String getIconResourceName(IModel<T> model, Component c) {
        return buttonCallback.getIconResourceName(model.getObject());
    }
};
