/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.nocket.component.table.GenericDataTableConfigurator;
import org.nocket.component.table.GenericDataTablePanel;
import org.nocket.gen.domain.visitor.html.styling.common.TablePanelBuilderI;

/**
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class Bootstrap2TablePanelBuilder implements TablePanelBuilderI<GenericDataTablePanel> {

	private GenericDataTablePanel panel = null;
	
	@Override
	public void initTablePanelBuilder(String wicketId, IModel<List<?>> data,
			GenericDataTableConfigurator<?> config) {
		panel = new GenericDataTablePanel(wicketId, data, config);
	}

	@Override
	public GenericDataTablePanel getTablePanel() {
		return panel;
	}
	

}
