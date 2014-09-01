package dmdweb.component.table.js;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxNavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.string.Strings;

import dmdweb.component.header.jquery.JQueryHelper;
import dmdweb.component.table.IRowItemSettable;
import dmdweb.component.table.ReflectionUtil;
import dmdweb.component.table.TableItemPosition;
import dmdweb.component.table.behavior.ClickAjaxEventBehavior;
import dmdweb.component.table.behavior.DblClickAjaxEventBehavior;
import dmdweb.component.table.behavior.IRowClickEventAware;

/**
 * Table with Java-Script sort capability. It depends on JQuery Library.
 * 
 * @author blaz02
 * 
 * @param <T>
 *            Model object type following Java-Bean convention.
 * 
 */
@SuppressWarnings("serial")
public class DMDJavaScriptDataTable<T> extends DataTable<T, String> implements IRowClickEventAware<T>, IRowItemSettable {

    private IRowClickEventAware<T> rowClickDelegate;
    private SortableDataProvider<T, String> dataProvider;
    private Class rowItemClass;

    public DMDJavaScriptDataTable(String id, List<IColumn<T, String>> columns,
            SortableDataProvider<T, String> dataProvider,
            int rowsPerPage, TableItemPosition navigationbarPosition) {
        super(id, columns, dataProvider, rowsPerPage);

        this.dataProvider = dataProvider;

        addToolbar(navigationbarPosition, newNavigationToolbar());
        addToolbar(TableItemPosition.top, new JavaScriptHeaderToolbar(this, dataProvider));
        addToolbar(TableItemPosition.bottom, new NoRecordsToolbar(this));

        setOutputMarkupId(true);
    }

    protected void addToolbar(TableItemPosition position, AbstractToolbar abstractToolbar) {
        if (TableItemPosition.bottom.equals(position)) {
            addBottomToolbar(abstractToolbar);
        } else {
            addTopToolbar(abstractToolbar);
        }
    }

    /**
     * Create a navigation toolbar for the data table. Returns a
     * DMDAjaxNavigationToolbar by default. Override it with:
     * 
     * <pre>
     * return new NavigationToolbar(this);
     * </pre>
     * 
     * if you want to page the table with the classic way.
     * 
     * 
     * @return the desired navigation toolbar
     */
    protected AbstractToolbar newNavigationToolbar() {
        return new AjaxNavigationToolbar(this);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        JQueryHelper.initJQuery(response);
        response.render(JavaScriptHeaderItem.forReference(
                new PackageResourceReference(DMDJavaScriptDataTable.class, "jquery.tablesorter.min.js")));
        response.render(JavaScriptHeaderItem.forScript("Wicket.Event.add(window, \"domready\", " +
                "function(event) { " + jsInitScript() + ";});", "js_table_sorter"));
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        AjaxRequestTarget ajaxRequestTarget = RequestCycle.get().find(AjaxRequestTarget.class);
        if (ajaxRequestTarget != null) {
            ajaxRequestTarget.appendJavaScript("Wicket.Event.add(window, \"domready\", " +
                    "function(event) { " + jsInitScript() + ";});");
        }
    }

    @Override
    protected Item<T> newRowItem(String id, int index, final IModel<T> model) {
        Class clazz = rowItemClass != null ? rowItemClass : OddEvenItem.class;

        Item<T> item = (Item<T>) ReflectionUtil.newInstance(clazz,
                new Class[] { String.class, int.class, IModel.class }, new Object[] { id, index, model });

        // Add event listeners to each row

        // Doubleclick listener
        DblClickAjaxEventBehavior<T> newOnDblClicktEvent = this.newOnDblClickEvent(model);
        if (newOnDblClicktEvent != null)
            item.add(newOnDblClicktEvent);

        // Singleclick listener
        ClickAjaxEventBehavior<T> newOnClickEvent = this.newOnClickEvent(model);
        if (newOnClickEvent != null)
            item.add(newOnClickEvent);

        return item;
    }

    @Override
    public void onComponentTag(final ComponentTag tag) {
        String className = "sortable";

        CharSequence oldClassName = tag.getAttribute("class");
        if (Strings.isEmpty(oldClassName)) {
            tag.put("class", className);
        } else {
            tag.put("class", oldClassName + " " + className);
        }
        super.onComponentTag(tag);
    }

    public void setRowClickDelegate(IRowClickEventAware<T> rowClickDelegate) {
        this.rowClickDelegate = rowClickDelegate;
    }

    public DblClickAjaxEventBehavior<T> newOnDblClickEvent(IModel<T> model) {
        return rowClickDelegate == null ? null : rowClickDelegate.newOnDblClickEvent(model);
    }

    public ClickAjaxEventBehavior<T> newOnClickEvent(IModel<T> model) {
        return rowClickDelegate == null ? null : rowClickDelegate.newOnClickEvent(model);
    }

    @Override
    public void setRowItemClass(Class rowItemClass) {
        this.rowItemClass = rowItemClass;
    }

    /**
     * Returns model for proper initialization of tablesorter. Method builds
     * Java-Script snippet which must be placed after the table and rendered
     * after request (page get as well as ajax). Example:
     * 
     * <pre>
     * $('table.sortable').tablesorter({ headers: { 1: { sorter: false}, 2: {sorter: false} }, sortList: [[3,0]] });
     * </pre>
     * 
     * Second and third columns are not sortable. Initial sort is put on the
     * fourth column.
     * 
     * @return JS Snippet to initialize the JQuery Tablesorter
     */
    public String jsInitScript() {
        final StringBuilder sb = new StringBuilder();
        int columnIdx = 0;
        int sortColumnIdx = -1;
        boolean appended = false;
        String initialSortProperty = null;
        SortParam<String> sort = dataProvider.getSort();
        if (sort != null) {
            initialSortProperty = sort.getProperty();
        }
        sb.append("$('table.sortable').tablesorter({ headers: {");
        for (IColumn<?, String> column : getColumns()) {
            if (column.getSortProperty() == null) {
                if (appended)
                    sb.append(",");
                sb.append(columnIdx).append(": { sorter: false } ");
                appended = true;
            } else {
                if (column.getSortProperty().equalsIgnoreCase(initialSortProperty))
                    sortColumnIdx = columnIdx;
            }
            columnIdx++;
        }
        ;
        // Sort initial state
        if (sortColumnIdx > -1) {
            sb.append("}, sortList: [[" + sortColumnIdx + ",0]]");
        } else {
            sb.append("}");
        }
        sb.append("});");
        return sb.toString();
    }

}
