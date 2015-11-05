package org.nocket.gen.page.visitor.bind.builder.components;

import gengui.domain.DomainObjectReference;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.convert.IConverter;
import org.nocket.component.table.ColumnSortOrder;
import org.nocket.component.table.GenericDataTableColumnConfigurator;
import org.nocket.component.table.GenericDataTableConfigurator;
import org.nocket.component.table.GenericDataTablePanel;
import org.nocket.component.table.TableItemPosition;
import org.nocket.component.table.TableSortType;
import org.nocket.gen.domain.element.MultivalueColumnElement;
import org.nocket.gen.domain.visitor.html.styling.StylingFactory;
import org.nocket.gen.domain.visitor.html.styling.common.TableBuilderI;
import org.nocket.gen.page.element.TableElement;
import org.nocket.gen.page.element.synchronizer.SynchronizerHelper;

public class GeneratedGenericDataTableFactory {

    protected TableElement e;
    protected String[] overrideSortableColumns;
    protected TableBuilderI<?, ?> tableBuilder;

    public GeneratedGenericDataTableFactory(TableElement e) {
        this.e = e;
        tableBuilder = StylingFactory.getStylingStrategy().getTableBuilder();
        tableBuilder.initTableBuilder(e);
        new TableExampleStructureAdopter(e).populate(tableBuilder.getTableConfigurator());
        new TableAttributeAdopter(e).populate(tableBuilder.getTableConfigurator());
    }

    public GenericDataTablePanel<?> createTable() {        
        GenericDataTablePanel<?> table = tableBuilder.getTablePanel();
        addCellContentConverters(table);
        return table;
    }

    protected void addCellContentConverters(GenericDataTablePanel table) {
        for (MultivalueColumnElement<DomainObjectReference> col : e.getDomainElement().getColumns()) {
            addColumnContentConverter(table, col);
        }
    }

    protected void addColumnContentConverter(GenericDataTablePanel table,
            MultivalueColumnElement<DomainObjectReference> col) {
        String format = new SynchronizerHelper(e.getContext(), col).getFormat(col.getMethod());
        Class<?> returnType = col.getMethod().getReturnType();
        IConverter<?> converter = createColumnConverter(col, format, returnType);
        if (converter != null) {
            table.setConverterForColumn(returnType, col.getColumnName(), converter);
        }
    }

    protected IConverter<?> createColumnConverter(MultivalueColumnElement<DomainObjectReference> col, String format,
            Class<?> returnType) {
        if (col.isDateType()) {
            return createDateConverter(format, returnType);
        } else if (col.isNumberType()) {
            return createNumberConverter(format, returnType);
        } else if (returnType.isEnum()) {
            return createEnumConverter(format, returnType);
        }
        return createOtherConverter(format, returnType);
    }

    protected IConverter<?> createOtherConverter(String format, Class<?> returnType) {
        return null;
    }

    protected IConverter<?> createEnumConverter(String format, Class<?> returnType) {
        return new EnumConverter(e.getContext());
    }

    protected IConverter<?> createDateConverter(String format, Class<?> returnType) {
        return (format != null) ? new SimpleDateConverter(format) : null;
    }

    protected IConverter<?> createNumberConverter(String format, Class<?> returnType) {
        if (format != null) {
            IConverter<?> converter = WebApplication.get().getConverterLocator().getConverter(returnType);
            GeneratedNumberTextField.customizeConverter(converter, format);
            return converter;
        }
        return null;
    }

    public static GenericDataTablePanel createTable(String wicketId,
            IModel<List<?>> data, GenericDataTableConfigurator<?> config) {
        TableBuilderI builder = StylingFactory.getStylingStrategy().getTableBuilder();
        builder.initTableBuilder(wicketId, data, config);
        return builder.getTablePanel();
    }


    /************************************************************************************
     * C o n v e n i e n c e w r a p p e r m e t h o d s f o r t h e c o n f i g
     * u r a t o r //
     ************************************************************************************/

    public GenericDataTableConfigurator config() {
        return tableBuilder.getTableConfigurator();
    }

    public GeneratedGenericDataTableFactory withSortType(TableSortType sortType) {
    	tableBuilder.getTableConfigurator().withSortType(sortType);
        return this;
    }

    public GeneratedGenericDataTableFactory withColumnConfigurator(GenericDataTableColumnConfigurator columnConfigurator) {
    	tableBuilder.getTableConfigurator().withColumnConfigurator(columnConfigurator);
        return this;
    }

    public GeneratedGenericDataTableFactory withRowsPerPage(int rowsPerPage) {
    	tableBuilder.getTableConfigurator().withRowsPerPage(rowsPerPage);
        return this;
    }

    public GeneratedGenericDataTableFactory withInitialSortOrder(ColumnSortOrder initialSortOrder) {
    	tableBuilder.getTableConfigurator().withInitialSortOrder(initialSortOrder);
        return this;
    }

    public GeneratedGenericDataTableFactory withInitialSortColumn(String initialSortColumn) {
    	tableBuilder.getTableConfigurator().withInitialSortColumn(initialSortColumn);
        return this;
    }

    public GeneratedGenericDataTableFactory withColumns(String... columns) {
    	tableBuilder.getTableConfigurator().withColumns(columns);
        return this;
    }

    public GeneratedGenericDataTableFactory withSortColumns(String... sortColumns) {
    	tableBuilder.getTableConfigurator().withSortColumns(sortColumns);
        return this;
    }

    public GeneratedGenericDataTableFactory withRowItemClass(Class rowItemClass) {
    	tableBuilder.getTableConfigurator().withRowItemClass(rowItemClass);
        return this;
    }

    public GeneratedGenericDataTableFactory withNavigationBar(TableItemPosition navigationbarPosition) {
    	tableBuilder.getTableConfigurator().withNavigationBar(navigationbarPosition);
        return this;
    }

}
