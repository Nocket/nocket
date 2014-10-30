package org.nocket.component.table.js;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.nocket.component.table.behavior.CssClassAttributeBehaviour;
import org.nocket.component.table.behavior.CssStyleAttributeBehavior;
import org.nocket.component.table.columns.IDMDStyledColumn;

// TODO: Auto-generated Javadoc
/**
 * Creates table headers for JavaScript Tables and enables sorting by clicking
 * on table headers. This class also takes care about disabling the sort
 * function for specific headers
 *
 * @author blaz02
 */

public class JavaScriptHeaderToolbar extends AbstractToolbar {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new java script header toolbar.
     *
     * @param table the table
     * @param stateLocator the state locator
     */
    public JavaScriptHeaderToolbar(final DataTable<?, String> table, final ISortStateLocator<String> stateLocator) {
        super(table);

        RepeatingView headers = new RepeatingView("headers");
        add(headers);

        final List<? extends IColumn<?, ?>> columns = table.getColumns();
        for (final IColumn<?, ?> column : columns) {
            WebMarkupContainer item = new WebMarkupContainer(headers.newChildId());
            headers.add(item);

            WebMarkupContainer header = null;
            header = new WebMarkupContainer("header");
            if (!column.isSortable()) {
                header.add(new AttributeModifier("class", "sorttable_nosort"));
            } else {
                header.add(new AttributeModifier("class", " "));
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
            Component lab = column.getHeader("label");
            lab.setRenderBodyOnly(true);
            header.add(lab);

        }
    }

}
