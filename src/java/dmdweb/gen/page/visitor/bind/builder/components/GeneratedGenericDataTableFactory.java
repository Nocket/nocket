package dmdweb.gen.page.visitor.bind.builder.components;

import gengui.domain.DomainObjectReference;
import gengui.util.SevereGUIException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.convert.IConverter;

import dmdweb.component.table.ColumnSortOrder;
import dmdweb.component.table.GeneratedGenericDataTableColumnConfigurator;
import dmdweb.component.table.GenericDataTableColumnConfigurator;
import dmdweb.component.table.GenericDataTableConfigurator;
import dmdweb.component.table.GenericDataTablePanel;
import dmdweb.component.table.TableItemPosition;
import dmdweb.component.table.TableSortType;
import dmdweb.gen.domain.element.MultivalueColumnElement;
import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.element.FormElement;
import dmdweb.gen.page.element.TableElement;
import dmdweb.gen.page.element.synchronizer.SynchronizerHelper;
import dmdweb.gen.page.element.synchronizer.TableButtonCallback;
import dmdweb.gen.page.element.synchronizer.TableDownloadCallback;

public class GeneratedGenericDataTableFactory {

    protected TableElement e;
    protected Class<? extends GenericDataTablePanel> clazz = GenericDataTablePanel.class;
    protected String[] overrideSortableColumns;
    protected final GenericDataTableConfigurator<?> config;

    public GeneratedGenericDataTableFactory(TableElement e) {
        this.e = e;
        this.config = new GenericDataTableConfigurator();
        new TableExampleStructureAdopter(e).populate(config);
        new TableAttributeAdopter(e).populate(config);
    }

    public GeneratedGenericDataTableFactory(TableElement e, GenericDataTableConfigurator config) {
        this.e = e;
        this.config = config;
    }

    public GeneratedGenericDataTableFactory withTableClass(Class<? extends GenericDataTablePanel> clazz) {
        this.clazz = clazz;
        return this;
    }

    public GenericDataTablePanel<?> createTable() {
        IModel<List<?>> data = e.getModel();
        DMDWebGenPageContext context = e.getContext();
        config.withColumnConfigurator(createColumnConfigurator());
        GenericDataTablePanel<?> table = createTable(clazz, e.getWicketId(), data, config);
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

    public static GenericDataTablePanel createTable(Class<? extends GenericDataTablePanel> clazz, String wicketId,
            IModel<List<?>> data, GenericDataTableConfigurator<?> config) {

        if (clazz == null) {
            return new GenericDataTablePanel(wicketId, data, config);
        }

        Class<?>[] paramTypes = new Class<?>[] { String.class, IModel.class, GenericDataTableConfigurator.class };

        Object[] params = new Object[] { wicketId, data, config };

        try {
            Constructor<? extends GenericDataTablePanel> constructor = clazz.getConstructor(paramTypes);
            return constructor.newInstance(params);
        } catch (NoSuchMethodException e) {
            throw new SevereGUIException(e);
        } catch (SecurityException e) {
            throw new SevereGUIException(e);
        } catch (InstantiationException e) {
            throw new SevereGUIException(e);
        } catch (IllegalAccessException e) {
            throw new SevereGUIException(e);
        } catch (IllegalArgumentException e) {
            throw new SevereGUIException(e);
        } catch (InvocationTargetException e) {
            throw new SevereGUIException(e);
        }
    }

    protected List<String> createSortableColumns() {
        if (overrideSortableColumns != null)
            return Arrays.asList(overrideSortableColumns);
        return e.getDomainElement().getPropertyColumnNames();
    }

    protected GenericDataTableColumnConfigurator createColumnConfigurator() {
        GenericDataTableColumnConfigurator columnConfigurator = config.getColumnConfigurator();
        if (columnConfigurator == null) {
            List<String> columns = e.getDomainElement().getPropertyColumnNames();
            List<String> sortableColumns = createSortableColumns();
            List<TableDownloadCallback> downloadColumns = e.getDownloadCallbacks();
            List<TableButtonCallback> tableButtons = e.getButtonCallbacks();
            Form<?> form = (Form<?>) e.getContext().getComponentRegistry().getComponent(FormElement.DEFAULT_WICKET_ID);
            columnConfigurator = new GeneratedGenericDataTableColumnConfigurator(columns, sortableColumns,
                    downloadColumns, tableButtons, form, e);
        }
        return columnConfigurator;
    }

    /************************************************************************************
     * C o n v e n i e n c e w r a p p e r m e t h o d s f o r t h e c o n f i g
     * u r a t o r //
     ************************************************************************************/

    public GenericDataTableConfigurator config() {
        return config;
    }

    public GeneratedGenericDataTableFactory withSortType(TableSortType sortType) {
        config.withSortType(sortType);
        return this;
    }

    public GeneratedGenericDataTableFactory withColumnConfigurator(GenericDataTableColumnConfigurator columnConfigurator) {
        config.withColumnConfigurator(columnConfigurator);
        return this;
    }

    public GeneratedGenericDataTableFactory withRowsPerPage(int rowsPerPage) {
        config.withRowsPerPage(rowsPerPage);
        return this;
    }

    public GeneratedGenericDataTableFactory withInitialSortOrder(ColumnSortOrder initialSortOrder) {
        config.withInitialSortOrder(initialSortOrder);
        return this;
    }

    public GeneratedGenericDataTableFactory withInitialSortColumn(String initialSortColumn) {
        config.withInitialSortColumn(initialSortColumn);
        return this;
    }

    public GeneratedGenericDataTableFactory withColumns(String... columns) {
        config.withColumns(columns);
        return this;
    }

    public GeneratedGenericDataTableFactory withSortColumns(String... sortColumns) {
        config.withSortColumns(sortColumns);
        return this;
    }

    public GeneratedGenericDataTableFactory withRowItemClass(Class rowItemClass) {
        config.withRowItemClass(rowItemClass);
        return this;
    }

    public GeneratedGenericDataTableFactory withNavigationBar(TableItemPosition navigationbarPosition) {
        config.withNavigationBar(navigationbarPosition);
        return this;
    }

}
