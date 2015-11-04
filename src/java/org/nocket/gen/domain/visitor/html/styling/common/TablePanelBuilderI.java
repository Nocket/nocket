package org.nocket.gen.domain.visitor.html.styling.common;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.nocket.component.table.GenericDataTableConfigurator;
import org.nocket.component.table.GenericDataTablePanel;

/**
 * Builder f�r das Panel einer generischen Tabelle in Nocket
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface TablePanelBuilderI<T extends GenericDataTablePanel> {
	
	/**
	 * Initialisierung des Table Panel.
	 * 
	 * @param wicketId Wicket-ID in der HTML-Seite
	 * @param data Model f�r die Daten der Tabelle
	 * @param config Konfiguration aus der GeneratedGenericDataTableFactory
	 */
	public void initTablePanelBuilder(String wicketId, IModel<List<?>> data, GenericDataTableConfigurator<?> config);
	
	/**
	 * Liefert das fertige TablePanel f�r die generische Tabelle
	 */
	public T getTablePanel();

}
