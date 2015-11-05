package org.nocket.gen.domain.visitor.html.styling.common;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.nocket.component.table.GenericDataTableConfigurator;
import org.nocket.component.table.GenericDataTablePanel;
import org.nocket.gen.page.element.TableElement;

/**
 * Builder f?r das Panel einer generischen Tabelle in Nocket
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface TableBuilderI<T extends GenericDataTablePanel, E extends GenericDataTableConfigurator> {
	
	/**
	 * Initialisierung des Builder.
	 * 
	 * @param element Das Tabellenelement, was dargestellt werden soll
	 */
	public void initTableBuilder(TableElement element);

	/**
	 * Initialisierung des Builder
	 * 
	 * @param wicketId Wicket-ID der Tabelle
	 * @param data Daten der Tabelle
	 * @param config TableConfigurator
	 */
	public void initTableBuilder(String wicketId, IModel<List<?>> data, GenericDataTableConfigurator<?> config);
	
	/** 
	 * Liefert das fertige TablePanel f?r die generische Tabelle
	 */
	public T getTablePanel();

	/**
	 * Liefert den zur Tabelle passenden Konfigurator
	 */
	public E getTableConfigurator();
}
