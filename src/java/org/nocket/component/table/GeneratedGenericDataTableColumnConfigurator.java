package org.nocket.component.table;

import gengui.annotations.Prompt;
import gengui.domain.DomainClassReference;
import gengui.domain.DomainObjectReference;
import gengui.guibuilder.DomainClassDecoration;
import gengui.util.AnnotationHelper;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.nocket.gen.domain.element.MultivalueColumnElement;
import org.nocket.gen.i18n.I18NLabelModel;
import org.nocket.gen.page.element.FormElement;
import org.nocket.gen.page.element.TableElement;
import org.nocket.gen.page.element.synchronizer.TableButtonCallback;
import org.nocket.gen.page.element.synchronizer.TableDownloadCallback;
import org.nocket.gen.page.visitor.bind.builder.components.TableExampleStructureAdopter;

public class GeneratedGenericDataTableColumnConfigurator<T extends Serializable> extends
GenericDataTableColumnConfigurator<T> {

    private static final long serialVersionUID = 1L;
    public static final String ICON_ENABLED = "{0}.icon.enabled";
    public static final String ICON_DISABLED = "{0}.icon.disabled";

    public static final String GG_TABLE_HEADER = "{0}.table.header";

    protected final List<TableButtonCallback> tableButtons;
    protected final List<TableDownloadCallback> downloadColumns;
    protected final Form<?> form;
    protected transient TableElement e;

    public GeneratedGenericDataTableColumnConfigurator(List<String> columns, List<String> sortableColumns,
	    List<TableDownloadCallback> downloadColumns, List<TableButtonCallback> tableButtons, Form<?> form,
	    TableElement e) {
	super(columns, sortableColumns);
	this.tableButtons = tableButtons;
	this.downloadColumns = downloadColumns;
	this.form = form;
	this.e = e;
    }

    public GeneratedGenericDataTableColumnConfigurator(TableElement e) {
	this(e.getDomainElement().getPropertyColumnNames(), e.getDomainElement().getPropertyColumnNames(), e
		.getDownloadCallbacks(), e.getButtonCallbacks(), (Form<?>) e.getContext().getComponentRegistry()
		.getComponent(FormElement.DEFAULT_WICKET_ID), e);
    }

    @Override
    protected void newDataColumns(List<IColumn<T, String>> colDefs) {
	TableExampleStructureAdopter tableStructureAdopter = new TableExampleStructureAdopter(e);
	for (String column : this.columns) {
	    String sort = null;
	    if (sortable == null || sortable.contains(column)) {
		sort = column;
	    }
	    GenericDataTablePanelPropertyColumn<T> col = (GenericDataTablePanelPropertyColumn<T>) createPropertyColumn(
		    column, sort);
	    col.setColumnStyle(tableStructureAdopter.findStyleForColumn(column));
	    colDefs.add(col);
	}
    }

    @Override
    protected void newActionColumns(List<IColumn<T, String>> colDefs) {
	addDownloadColumns(colDefs);
	addTableButtonCallbacks(colDefs);
    }

    protected void addDownloadColumns(List<IColumn<T, String>> colDefs) {
	if (downloadColumns == null)
	    return;
	TableExampleStructureAdopter tableStructureAdopter = new TableExampleStructureAdopter(e);
	for (TableDownloadCallback downloadColumn : downloadColumns) {
	    final IModel<String> header = getDownloadColumnNameModel(downloadColumn);
	    final String enabledIconResource = MessageFormat.format(ICON_ENABLED, downloadColumn.getWicketId());
	    final String disabledIconResource = MessageFormat.format(ICON_DISABLED, downloadColumn.getWicketId());

	    GeneratedDownloadByPropertyIconColumn<T> column = new GeneratedDownloadByPropertyIconColumn<T>(header,
		    downloadColumn, enabledIconResource,
		    disabledIconResource);
	    column.setColumnStyle(tableStructureAdopter.findStyleForColumn(downloadColumn.getPropertyName()));
	    colDefs.add(column);
	}
    }

    protected void addTableButtonCallbacks(List<IColumn<T, String>> colDefs) {
	if (tableButtons == null)
	    return;
	TableExampleStructureAdopter tableStructureAdopter = new TableExampleStructureAdopter(e);
	for (final TableButtonCallback tableButton : tableButtons) {
	    final IModel<String> header = getButtonColumnNameModel(tableButton);
	    final String enabledIconResource = MessageFormat.format(ICON_ENABLED, tableButton.getWicketId());
	    final String disabledIconResource = MessageFormat.format(ICON_DISABLED, tableButton.getWicketId());

	    if (tableButton.isDownloadMethod()) {
		GeneratedDownloadByMethodIconColumn<T> column = new GeneratedDownloadByMethodIconColumn<T>(
			header, enabledIconResource, disabledIconResource, tableButton);
		column.setColumnStyle(tableStructureAdopter.findStyleForColumn(tableButton.getName()));
		colDefs.add(column);
	    } else {
		GeneratedAjaxSubmitActionIconColumn<T> column = new GeneratedAjaxSubmitActionIconColumn<T>(
			header, form, enabledIconResource, disabledIconResource, tableButton);
		column.setColumnStyle(tableStructureAdopter.findStyleForColumn(tableButton.getName()));
		colDefs.add(column);
	    }
	}
    }

    /**
     * Returns name of the column with button. Depends on localization mode.
     * 
     * @param button
     * 
     * @return
     */
    protected IModel<String> getButtonColumnNameModel(TableButtonCallback button) {
	if (e.getContext().getConfiguration().isLocalizationWicket()) {
	    return new ResourceModel(button.getWicketId(), button.getPrompt());
	} else {
	    String prompt = getPromptForColumn(e.getDomainElement().getColumnAccessor().getClassRef(),
		    button.getMethod(), button.getPrompt());
	    if (prompt != null) {
		return new I18NLabelModel(getDomainClassReferenceForTable().getDomainClass(), prompt, button
			.getPrompt());
	    }
	    return new I18NLabelModel(getDomainClassReferenceForTable().getDomainClass(),
		    MessageFormat.format(GG_TABLE_HEADER, button.getMethod().getName()), button.getPrompt());
	}
    }

    protected IModel<String> getDownloadColumnNameModel(TableDownloadCallback download) {
	if (e.getContext().getConfiguration().isLocalizationWicket()) {
	    return new ResourceModel(download.getPropertiesWicketId(), download.getPrompt());
	} else {
	    String prompt = getPromptForColumn(e.getDomainElement().getColumnAccessor().getClassRef(),
		    download.getMethod(), download.getPrompt());
	    if (prompt != null) {
		return new I18NLabelModel(getDomainClassReferenceForTable().getDomainClass(), prompt, download
			.getPrompt());
	    }
	    return new I18NLabelModel(getDomainClassReferenceForTable().getDomainClass(),
		    MessageFormat.format(GG_TABLE_HEADER, download.getPropertyName()), download.getPrompt());
	}
    }

    /**
     * Returns name of the column with normal property. Depends on localization
     * mode.
     * 
     * @param button
     * 
     * @return
     */
    @Override
    protected IModel<String> getColumnNameModel(String property) {
	if (e.getContext().getConfiguration().isLocalizationWicket()) {
	    return new UpperAndLowerCaseResourceModel(getTable().getId() + ".columns.", property);
	} else {
	    MultivalueColumnElement<DomainObjectReference> columnElement = getColumnElementForProperty(property);
	    String prompt = getPromptForColumn(e.getDomainElement().getColumnAccessor().getClassRef(),
		    columnElement.getMethod(), columnElement.getPrompt());
	    if (prompt != null) {
		return new I18NLabelModel(getDomainClassReferenceForTable().getDomainClass(),
			getTableHeaderKey(prompt), columnElement.getPrompt());
	    }
	    return new I18NLabelModel(getDomainClassReferenceForTable().getDomainClass(),
		    getTableHeaderKey(StringUtils.capitalize(property)), columnElement.getPrompt());
	}
    }

    protected DomainClassReference getDomainClassReferenceForTable() {
	return e.getDomainElement().getColumnAccessor().getClassRef();
    }

    protected MultivalueColumnElement<DomainObjectReference> getColumnElementForProperty(String property) {
	property = StringUtils.capitalize(property);
	List<MultivalueColumnElement<DomainObjectReference>> columns = e.getDomainElement().getColumns();
	for (MultivalueColumnElement<DomainObjectReference> column : columns) {
	    if (column.getPropertyName().equals(property)) {
		return column;
	    }
	}
	return null;
    }

    public String getPromptForColumn(DomainClassReference classRef, Method method, String defaultBase) {
	DomainClassDecoration interception = classRef.getDecorations(defaultBase);
	method = interception.getTarget(method.getName(), method);
	Prompt prompt = new AnnotationHelper(method).getAnnotation(Prompt.class);
	if (prompt != null)
	    return prompt.value();
	return null;
    }

    private String getTableHeaderKey(String key) {
	return MessageFormat.format(GG_TABLE_HEADER, key);
    }
}
