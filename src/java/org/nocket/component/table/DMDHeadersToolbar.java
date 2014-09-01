package org.nocket.component.table;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.nocket.component.table.behavior.CssClassAttributeBehaviour;
import org.nocket.component.table.behavior.CssStyleAttributeBehavior;
import org.nocket.component.table.columns.IDMDStyledColumn;

public class DMDHeadersToolbar extends AbstractToolbar {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * 
     * @param <T>
     *            the column data type
     * @param table
     *            data table this toolbar will be attached to
     * @param stateLocator
     *            locator for the ISortState implementation used by sortable
     *            headers
     */
    public DMDHeadersToolbar(final DataTable<?, String> table, final ISortStateLocator<String> stateLocator) {
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
    }

    /**
     * Factory method for sortable header components. A sortable header
     * component must have id of <code>headerId</code> and conform to markup
     * specified in <code>HeadersToolbar.html</code>
     * 
     * @param headerId
     *            header component id
     * @param property
     *            property this header represents
     * @param locator
     *            sort state locator
     * @return created header component
     */
    protected WebMarkupContainer newSortableHeader(final String headerId, final String property,
            final ISortStateLocator locator) {
        return new OrderByBorder(headerId, property, locator) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSortChanged() {
                getTable().setCurrentPage(0);
            }
        };
    }

}
