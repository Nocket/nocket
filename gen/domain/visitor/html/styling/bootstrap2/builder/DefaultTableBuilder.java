/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.nocket.component.table.GeneratedGenericDataTableColumnConfigurator;
import org.nocket.component.table.GenericDataTableColumnConfigurator;
import org.nocket.component.table.GenericDataTableConfigurator;
import org.nocket.component.table.GenericDataTablePanel;
import org.nocket.gen.domain.visitor.html.styling.common.TableBuilderI;
import org.nocket.gen.page.element.FormElement;
import org.nocket.gen.page.element.TableElement;
import org.nocket.gen.page.element.synchronizer.TableButtonCallback;
import org.nocket.gen.page.element.synchronizer.TableDownloadCallback;

/**
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class DefaultTableBuilder implements TableBuilderI<GenericDataTablePanel, GenericDataTableConfigurator> {

	private GenericDataTablePanel panel = null;
	private GenericDataTableConfigurator<?> config = null;
	private TableElement element = null;
	
	@Override
	public void initTableBuilder(TableElement element) {
		this.element = element;
		
		config = new GenericDataTableConfigurator();
        config.withColumnConfigurator(createColumnConfigurator());
        
		panel = new GenericDataTablePanel(element.getWicketId(), element.getModel(), config);
	}
	
	@Override
	public void initTableBuilder(String wicketId, IModel<List<?>> data, GenericDataTableConfigurator<?> config) {
		this.config = config;
		this.panel = new GenericDataTablePanel(wicketId, data, config);
	}

	@Override
	public GenericDataTablePanel getTablePanel() {
		return panel;
	}


	@Override
	public GenericDataTableConfigurator getTableConfigurator() {
		return config;
	}
	

	
	

    protected GenericDataTableColumnConfigurator createColumnConfigurator() {
        GenericDataTableColumnConfigurator columnConfigurator = config.getColumnConfigurator();
        if (columnConfigurator == null) {
            List<String> columns = element.getDomainElement().getPropertyColumnNames();
            List<String> sortableColumns = createSortableColumns();
            List<TableDownloadCallback> downloadColumns = element.getDownloadCallbacks();
            List<TableButtonCallback> tableButtons = element.getButtonCallbacks();
            Form<?> form = (Form<?>) element.getContext().getComponentRegistry().getComponent(FormElement.DEFAULT_WICKET_ID);
            columnConfigurator = new GeneratedGenericDataTableColumnConfigurator(columns, sortableColumns,
                    downloadColumns, tableButtons, form, element);
        }
        return columnConfigurator;
    }
    
    protected List<String> createSortableColumns() {
        return element.getDomainElement().getPropertyColumnNames();
    }
}
