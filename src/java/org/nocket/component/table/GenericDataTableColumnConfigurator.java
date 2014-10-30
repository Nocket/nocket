package org.nocket.component.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;
import org.nocket.component.table.columns.CustomRenderingPropertyTableColumn;
import org.nocket.component.table.columns.renderer.ColumnRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericDataTableColumnConfigurator.
 *
 * @param <T> the generic type
 */
public class GenericDataTableColumnConfigurator<T extends Serializable> implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant log. */
    private static final Logger log = Logger.getLogger(GenericDataTableColumnConfigurator.class);

    /** The column order. */
    protected LinkedList<ColumnType> columnOrder;
    
    /** The column renderers. */
    protected Map<String, ColumnRenderer<T>> columnRenderers;

    /** The columns. */
    protected List<String> columns;

    /** The sortable. */
    protected List<String> sortable;

    /** The table. */
    protected GenericDataTablePanel<T> table;

    /**
     * Instantiates a new generic data table column configurator.
     *
     * @param columns the columns
     * @param sortableColumns the sortable columns
     */
    public GenericDataTableColumnConfigurator(List<String> columns, List<String> sortableColumns) {
        this(columns, sortableColumns, getDefaultColumnOrder());
    }

    /**
     * Instantiates a new generic data table column configurator.
     *
     * @param columns the columns
     * @param sortableColumns the sortable columns
     * @param columnOrder the column order
     */
    public GenericDataTableColumnConfigurator(List<String> columns, List<String> sortableColumns,
            LinkedList<ColumnType> columnOrder) {
        this.columns = columns;
        this.sortable = sortableColumns == null ? new ArrayList<String>() : sortableColumns;
        this.columnOrder = columnOrder;
        columnRenderers = new HashMap<String, ColumnRenderer<T>>();
    }

    /**
     * Gets the table.
     *
     * @return the table
     */
    protected GenericDataTablePanel<T> getTable() {
        return table;
    }

    /**
     * Adds the column renderer.
     *
     * @param columnId the column id
     * @param renderer the renderer
     */
    public void addColumnRenderer(String columnId, ColumnRenderer<T> renderer) {
        columnRenderers.put(columnId, renderer);
    }

    /**
     * Construct column definitions.
     *
     * @param table the table
     * @return the list
     */
    public List<IColumn<T, String>> constructColumnDefinitions(GenericDataTablePanel<T> table) {
        this.table = table;
        return newColumnDefinitions();
    }

    /**
     * Gets the default column order.
     *
     * @return the default column order
     */
    private static LinkedList<ColumnType> getDefaultColumnOrder() {
        LinkedList<ColumnType> columnOrder = new LinkedList<ColumnType>();
        columnOrder.add(ColumnType.DATA);
        columnOrder.add(ColumnType.CHECKBOX);
        columnOrder.add(ColumnType.ACTION);
        return columnOrder;
    }

    /**
     * Sets the column order.
     *
     * @param columns the new column order
     */
    public void setColumnOrder(ColumnType... columns) {
        LinkedList<ColumnType> order = new LinkedList<ColumnType>();
        for (ColumnType col : columns) {
            order.add(col);
        }
        this.columnOrder = order;
    }

    /**
     * Return column definitions for the table.
     * 
     * @return List of IColumn instances
     */
    protected List<IColumn<T, String>> newColumnDefinitions() {
        List<IColumn<T, String>> columnDefs = new ArrayList<IColumn<T, String>>();

        for (ColumnType type : columnOrder) {
            if (ColumnType.DATA.equals(type))
                newDataColumns(columnDefs);

            if (ColumnType.CHECKBOX.equals(type))
                newCheckBoxColumn(columnDefs);

            if (ColumnType.ACTION.equals(type))
                newActionColumns(columnDefs);
        }

        return columnDefs;
    }

    /**
     * New data columns.
     *
     * @param colDefs            Instance of the list with the columns.
     */
    protected void newDataColumns(List<IColumn<T, String>> colDefs) {
        for (String column : this.columns) {
            String sort = null;
            if (sortable == null || sortable.contains(column)) {
                sort = column;
            }
            colDefs.add(createPropertyColumn(column, sort));
        }
    }

    /**
     * Creates the property column.
     *
     * @param column the column
     * @param sort the sort
     * @return the i column
     */
    protected IColumn<T, String> createPropertyColumn(final String column, String sort) {
        IModel<String> columnNameModel = getColumnNameModel(column);
        if (log.isDebugEnabled()) {
            log.debug("createPropertyModel: " + columnNameModel + "; sort: " + sort + "; column: " + column);
        }
        if (columnRenderers.containsKey(column)) {
            return new CustomRenderingPropertyTableColumn<T>(columnNameModel, sort, column, table,
                    columnRenderers.get(column));
        }
        return new GenericDataTablePanelPropertyColumn<T>(columnNameModel, sort, column, table);
    }

    /**
     * Column name is take form the properties file. Key is:
     * <i>table-id</i>.columns.<i>property-name</i>. When key is not found,
     * property name is capitalized.
     * 
     * @param property
     *            Name of the property
     * 
     * @return Name of the column
     */
    protected IModel<String> getColumnNameModel(String property) {
        // Following special model is a work-around: GenericDataTable is based on low-case started property names while
        // the generated resource files of the generic part of org.nocket are based on capital started property names
        // so we try both notations here
        return new UpperAndLowerCaseResourceModel(table.getId() + ".columns.", property);
    }

    /**
     * Method defines column with the check-box. Default implementation is
     * empty. If you need a column with the check-box add following to the list:
     * 
     * <pre>
     * columns.add(new SetCheckBoxColum&lt;T&gt;((Model.of(&quot;Column title&quot;), getSelected()));
     * </pre>
     * 
     * @param columns
     *            Instance of the list with the columns.
     */
    protected void newCheckBoxColumn(List<IColumn<T, String>> columns) {
        // By the default no column with check-box
    }

    /**
     * Method defines column with the links. Default implementation is empty. If
     * required to the list instances of ActionColumn like this:
     * 
     * <pre>
     * columns.add(new ActionColumn&lt;T&gt;(Model.of(&quot;Column title&quot;), Model.of(&quot;Link name&quot;))
     *   protected void onClick(IModel&lt;T&gt; clicked) {
     *     // Do here something with the Model of &lt;T&gt;
     *   });
     * </pre>
     *
     * @param colDefs the col defs
     */
    protected void newActionColumns(List<IColumn<T, String>> colDefs) {
        // By the default no column with action
    }

    /**
     * Gets the sortable.
     *
     * @return the sortable
     */
    public List<String> getSortable() {
        return sortable;
    }

    /**
     * Gets the columns.
     *
     * @return the columns
     */
    public List<String> getColumns() {
        return columns;
    }

}
