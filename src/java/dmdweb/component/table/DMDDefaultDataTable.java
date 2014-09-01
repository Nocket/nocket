package dmdweb.component.table;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;

public class DMDDefaultDataTable<T> extends DataTable<T, String> implements IRowItemSettable {

    private static final long serialVersionUID = 1L;

    private Class rowItemClass;

    public DMDDefaultDataTable(String id, List<IColumn<T, String>> columns,
            ISortableDataProvider<T, String> dataProvider,
            int rowsPerPage, TableItemPosition navigationbarPosition) {
        super(id, columns, dataProvider, rowsPerPage);

        addToolbar(navigationbarPosition, new NavigationToolbar(this));
        addToolbar(TableItemPosition.top, new DMDHeadersToolbar(this, dataProvider));
        addToolbar(TableItemPosition.bottom, new NoRecordsToolbar(this));
    }

    protected void addToolbar(TableItemPosition position, AbstractToolbar abstractToolbar) {
        if (TableItemPosition.bottom.equals(position)) {
            addBottomToolbar(abstractToolbar);
        } else {
            addTopToolbar(abstractToolbar);
        }
    }

    public void setRowItemClass(Class rowItemClass) {
        this.rowItemClass = rowItemClass;
    }

    @Override
    @SuppressWarnings( { "rawtypes", "unchecked" })
    protected Item<T> newRowItem(final String id, final int index, final IModel<T> model) {
        Class clazz = rowItemClass != null ? rowItemClass : OddEvenItem.class;

        return (Item<T>) ReflectionUtil.newInstance(clazz, new Class[] { String.class, int.class, IModel.class },
                new Object[] { id, index, model });
    }
}
