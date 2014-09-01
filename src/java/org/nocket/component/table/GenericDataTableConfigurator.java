package org.nocket.component.table;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This class serves as a parameter object for the {@link GenericDataTablePanel}
 * as the latter one gets more and more flexibility which is difficult to
 * express by discrete constructor parameters. The configurator provides a
 * fluent API for convenient access to the various flex points.
 * 
 * @author less02
 */
public class GenericDataTableConfigurator<T extends Serializable> {
    protected TableSortType sortType = TableSortType.AJAX;
    protected GenericDataTableColumnConfigurator<T> columnConfigurator;
    protected int rowsPerPage = 20;
    protected ColumnSortOrder initialSortOrder = ColumnSortOrder.UP;
    protected String initialSortColumn;
    protected String[] columns;
    protected String[] sortColumns;
    protected Class<?> rowItemClass;
    protected TableItemPosition navigationbarPosition = TableItemPosition.bottom;

    public GenericDataTableConfigurator<T> withSortType(TableSortType sortType) {
        this.sortType = sortType;
        return this;
    }

    public GenericDataTableConfigurator<T> withColumnConfigurator(
            GenericDataTableColumnConfigurator<T> columnConfigurator) {
        this.columnConfigurator = columnConfigurator;
        return this;
    }

    public GenericDataTableConfigurator<T> withRowItemClass(Class rowItemClass) {
        this.rowItemClass = rowItemClass;
        return this;
    }

    public GenericDataTableConfigurator<T> withRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
        return this;
    }

    public GenericDataTableConfigurator<T> withInitialSortOrder(ColumnSortOrder initialSortOrder) {
        this.initialSortOrder = initialSortOrder;
        return this;
    }

    public GenericDataTableConfigurator<T> withInitialSortColumn(String initialSortColumn) {
        this.initialSortColumn = initialSortColumn;
        return this;
    }

    public GenericDataTableConfigurator<T> withColumns(String... columns) {
        this.columns = columns;
        return this;
    }

    public GenericDataTableConfigurator<T> withSortColumns(String... sortColumns) {
        this.sortColumns = sortColumns;
        return this;
    }

    public GenericDataTableColumnConfigurator<T> getColumnConfigurator() {
        if (columnConfigurator != null)
            return columnConfigurator;
        if (columns != null)
            return new GenericDataTableColumnConfigurator<T>(Arrays.asList(columns), Arrays.asList(sortColumns));
        return null;
    }

    public GenericDataTableConfigurator<T> withNavigationBar(TableItemPosition navigationbarPosition) {
        this.navigationbarPosition = navigationbarPosition;
        return this;
    }

}
