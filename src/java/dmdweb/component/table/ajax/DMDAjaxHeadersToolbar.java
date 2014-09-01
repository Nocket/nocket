package dmdweb.component.table.ajax;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.sort.AjaxFallbackOrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;

import dmdweb.component.table.behavior.CssClassAttributeBehaviour;
import dmdweb.component.table.behavior.CssStyleAttributeBehavior;
import dmdweb.component.table.columns.IDMDStyledColumn;

public class DMDAjaxHeadersToolbar extends AbstractToolbar {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * 
     * @param table
     * @param stateLocator
     */
    public DMDAjaxHeadersToolbar(final DataTable<?, String> table, final ISortStateLocator<String> stateLocator) {
        super(table);

        RepeatingView headers = new RepeatingView("headers");
        add(headers);

        final List<? extends IColumn<?, String>> columns = table.getColumns();
        for (final IColumn<?, String> column : columns) {
            AbstractItem item = new AbstractItem(headers.newChildId());
            headers.add(item);

            WebMarkupContainer header = null;
            if (column.isSortable()) {
                header = newSortableHeader("header", column.getSortProperty(), stateLocator);
            } else {
                header = new WebMarkupContainer("header");
            }

            if (column instanceof IStyledColumn<?, ?>) {
                header.add(new CssClassAttributeBehaviour()
                {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected String getCssClass()
                    {
                        return ((IStyledColumn<?, ?>) column).getCssClass();
                    }
                });
            }

            if (column instanceof IDMDStyledColumn<?, ?>) {
                header.add(new CssStyleAttributeBehavior()
                {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected String getColumnCssStyleAttribute()
                    {
                        return ((IDMDStyledColumn<?, ?>) column).getCssStyleAttribute();
                    }
                });

            }

            item.add(header);
            item.setRenderBodyOnly(true);
            header.add(column.getHeader("label"));
        }
        table.setOutputMarkupId(true);
    }

    protected WebMarkupContainer newSortableHeader(final String borderId, final String property,
            final ISortStateLocator locator) {
        return new AjaxFallbackOrderByBorder(borderId, property, locator, getAjaxCallListener()) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onAjaxClick(final AjaxRequestTarget target) {
                target.add(getTable());
            }

            @Override
            protected void onSortChanged() {
                super.onSortChanged();
                getTable().setCurrentPage(0);
            }
        };
    }

    /**
     * Returns a decorator that will be used to decorate ajax links used in
     * sortable headers
     * 
     * @return decorator or null for none
     */
    protected IAjaxCallListener getAjaxCallListener() {
        return null;
    }

}
