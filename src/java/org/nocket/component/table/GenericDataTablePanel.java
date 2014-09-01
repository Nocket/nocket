package org.nocket.component.table;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.nocket.component.table.ajax.DMDAjaxDataTable;
import org.nocket.component.table.behavior.ClickAjaxEventBehavior;
import org.nocket.component.table.behavior.DblClickAjaxEventBehavior;
import org.nocket.component.table.behavior.IRowClickEventAware;
import org.nocket.component.table.js.DMDJavaScriptDataTable;

/**
 * Generic panel displaying tables. The tables are sortable. Depending on type
 * parameter tables can be sorted either via JavaScript in the browser or on the
 * server (Ajax and non-Ajax are supported).
 * 
 * @author blaz02
 * @author vocke03
 * 
 * @param <T>
 *            Model object type following Java-Bean convention.
 */
@SuppressWarnings("serial")
public class GenericDataTablePanel<T extends Serializable> extends Panel implements IPageable, IRowClickEventAware<T> {
    private static final Logger log = Logger.getLogger(GenericDataTablePanel.class);

    private DataTable<T, String> dataTable;
    private Set<T> selected = new HashSet<T>();

    private Map<String, Tupel> columnConverter;

    protected String cssClassName;
    private int columnCount = 0;

    private int rowsPerPage;

    /**
     * Defines in which direction the table is initially sorted. If the value is
     * NONE, the table is initially not sorted, assuming that the data is
     * provided in a reasonable way. However, the user may still change the
     * sorting by clicking the header cells
     */
    private ColumnSortOrder initialSortOrder;

    private String initialSortColumn;

    private GenericDataTableColumnConfigurator<T> columnConfigurator;

    /**
     * Constructor for the table panel.
     * 
     * @param id
     *            Wicket id of the comonent.
     * @param data
     *            Model with the list of objects, which must be show in the
     *            table. Model objects must follow Java-Bean conventions.
     * @param config
     *            Various configuration parameters. See class
     *            {@link GenericDataTableConfigurator}.
     */
    public GenericDataTablePanel(String id, IModel<List<T>> data, GenericDataTableConfigurator<T> config) {
        super(id, data);

        this.rowsPerPage = config.rowsPerPage;
        this.columnConverter = new HashMap<String, Tupel>();
        this.columnConfigurator = config.getColumnConfigurator();
        this.initialSortOrder = config.initialSortOrder;
        this.initialSortColumn = config.initialSortColumn;

        List<IColumn<T, String>> columnDefinitions = columnConfigurator.constructColumnDefinitions(this);
        SortableDataProvider<T, String> provider = newDataProvider();

        switch (config.sortType) {
        case AJAX:
            DMDAjaxDataTable<T> ajaxDataTable = new DMDAjaxDataTable<T>("table", columnDefinitions, provider,
                    rowsPerPage, config.navigationbarPosition);
            ajaxDataTable.setRowClickDelegate(this);
            dataTable = ajaxDataTable;
            break;
        case JS:
            DMDJavaScriptDataTable<T> jsDataTable = new DMDJavaScriptDataTable<T>("table", columnDefinitions, provider,
                    rowsPerPage, config.navigationbarPosition);
            jsDataTable.setRowClickDelegate(this);
            dataTable = jsDataTable;
            break;
        default:
            dataTable = new DMDDefaultDataTable<T>("table", columnDefinitions, provider, rowsPerPage,
                    config.navigationbarPosition);
            break;
        }

        if (config.rowItemClass != null)
            setRowItemClass(config.rowItemClass);

        add(dataTable);

        // adds a CSS Class to the table based on the id of the
        // GenericDataTablePanel instance.
        addCssClassToTable("gdtp_" + id);

        setOutputMarkupId(true);
    }

    public void htmlizeTableHeaders() {
        List<? extends IColumn<T, String>> columnDefinitions = dataTable.getColumns();
        for (IColumn<T, String> column : columnDefinitions) {
            Component headerComponent = column.getHeader(LabelHtmlizer.NO_LABEL);
            if (!headerComponent.getId().equals(LabelHtmlizer.NO_LABEL))
                LabelHtmlizer.enableHtml(headerComponent);
        }
    }

    /**
     * Older constructor which is just around for compatibility reasons.
     */
    @Deprecated
    public GenericDataTablePanel(String id, IModel<List<T>> data, String[] columns, String[] sortableColumns) {
        this(id, data, new GenericDataTableConfigurator<T>()
                .withColumnConfigurator(new GenericDataTableColumnConfigurator<T>(Arrays.asList(columns), Arrays
                        .asList(sortableColumns))));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        //Replace table tag with div tag to ensure wicket generating the table INSIDE the elements tag, not besides it!
        //Thus prevent buggy behaviour when updating this component on AjaxRequestTarget.
        tag.setName("div");
    }

    public void setRowItemClass(Class clazz) {
        ((IRowItemSettable) dataTable).setRowItemClass(clazz);
    }

    /**
     * Adds a CSS class to the markup class attribute to enable more precise css
     * selectors for styling.
     * 
     * @param className
     *            The css class name which should be added to the table tag.
     */
    public void addCssClassToTable(String className) {
        cssClassName = className;
        dataTable.add(new AttributeAppender("class", className).setSeparator(" "));
    }

    /**
     * Method defines ISortableDataProvider instance for the table. Default
     * implementation provides entities form the list specified in the
     * constructor of GenericDataTablePanel. First property of the
     */
    protected SortableDataProvider<T, String> newDataProvider() {
        SortParam<String> sortParam = null;
        if (columnConfigurator.getSortable().size() > 0 && getDefaultSortOrder() != ColumnSortOrder.NONE) {
            String initialSortColumn = getInitialSortColumn();
            if (initialSortColumn == null) {
                initialSortColumn = columnConfigurator.getSortable().get(0);
            }
            sortParam = new SortParam<String>(initialSortColumn, (getDefaultSortOrder() == ColumnSortOrder.UP));
        }
        return new SortableGenericDataProvider<T, String>((IModel<List<T>>) getDefaultModel(), sortParam);
    }

    protected String getInitialSortColumn() {
        return initialSortColumn;
    }

    protected ColumnSortOrder getDefaultSortOrder() {
        return initialSortOrder;
    }

    /**
     * Override this method to provide handler for click events. This works for
     * JavaScript and Ajax tables only.
     * 
     */
    @Override
    public ClickAjaxEventBehavior<T> newOnClickEvent(IModel<T> model) {
        return null;
    }

    /**
     * Override this method to provide handler for double-click events. This
     * works for JavaScript and Ajax tables only.
     * 
     * @return New instance of DblClickAjaxEventBehavior
     */
    @Override
    public DblClickAjaxEventBehavior<T> newOnDblClickEvent(IModel<T> model) {
        return null;
    }

    /**
     * @return Returns set with references to the instances chosen with the
     *         check-box.
     */
    public Set<T> getSelected() {
        return this.selected;
    }

    /*
     * Delegate methods to IPageable in order to be able to add pager directly
     * to the GenericDataTablePanel.
     */

    public long getCurrentPage() {
        return dataTable.getCurrentPage();
    }

    public void setCurrentPage(long page) {
        dataTable.setCurrentPage(page);
    }

    public long getPageCount() {
        return dataTable.getPageCount();
    }

    public void setConverterForColumn(Class<?> type, String columnName, IConverter<?> converter) {
        Tupel tupel = new Tupel();
        tupel.one = type;
        tupel.two = converter;
        this.columnConverter.put(columnName, tupel);
    }

    public void setColumnConverter(Map<String, Tupel> columnConverter) {
        this.columnConverter = columnConverter;
    }

    public Map<String, Tupel> getColumnConverter() {
        return columnConverter;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public List<String> getColumns() {
        return columnConfigurator.getColumns();
    }

    public int getColumnCount() {
        return columnCount;
    }

    public static class Tupel implements Serializable {
        public Class<?> one;
        public IConverter<?> two;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public ColumnSortOrder getInitialSortOrder() {
        return initialSortOrder;
    }

    public void setInitialSortOrder(ColumnSortOrder initialSortOrder) {
        this.initialSortOrder = initialSortOrder;
    }
}
