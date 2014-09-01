package dmdweb.component.table.ajax;

import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxNavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;

import dmdweb.component.table.IRowItemSettable;
import dmdweb.component.table.ReflectionUtil;
import dmdweb.component.table.TableItemPosition;
import dmdweb.component.table.behavior.ClickAjaxEventBehavior;
import dmdweb.component.table.behavior.DblClickAjaxEventBehavior;
import dmdweb.component.table.behavior.IRowClickEventAware;

/**
 * Table with sort capability realized with the Ajax links.
 * 
 * @author blaz02
 * 
 * @param <T>
 *            Model object type following Java-Bean convention.
 */
public class DMDAjaxDataTable<T> extends DataTable<T, String> implements IRowClickEventAware<T>,
        IRowItemSettable {

    private static final long serialVersionUID = 1L;

    private IRowClickEventAware<T> rowClickDelegate;

    private Class rowItemClass;

    public DMDAjaxDataTable(String id, List<IColumn<T, String>> columns, ISortableDataProvider<T, String> dataProvider,
            int rowsPerPage, TableItemPosition navigationbarPosition) {
        super(id, columns, dataProvider, rowsPerPage);
        setOutputMarkupId(true);
        setVersioned(false);
        addToolbar(navigationbarPosition, new AjaxNavigationToolbar(this));
        addToolbar(TableItemPosition.top, new DMDAjaxHeadersToolbar(this, dataProvider));
        addToolbar(TableItemPosition.bottom, new NoRecordsToolbar(this));
    }

    protected void addToolbar(TableItemPosition position, AbstractToolbar abstractToolbar) {
        if (TableItemPosition.bottom.equals(position)) {
            addBottomToolbar(abstractToolbar);
        } else {
            addTopToolbar(abstractToolbar);
        }
    }

    @Override
    @SuppressWarnings( { "rawtypes", "unchecked" })
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

    public void setRowClickDelegate(IRowClickEventAware<T> rowClickDelegate) {
        this.rowClickDelegate = rowClickDelegate;
    }

    public DblClickAjaxEventBehavior<T> newOnDblClickEvent(IModel<T> model) {
        return rowClickDelegate == null ? null : rowClickDelegate.newOnDblClickEvent(model);
    }

    public ClickAjaxEventBehavior<T> newOnClickEvent(IModel<T> model) {
        return rowClickDelegate == null ? null : rowClickDelegate.newOnClickEvent(model);
    }

    public void setRowItemClass(Class rowItemClass) {
        this.rowItemClass = rowItemClass;
    }
}
