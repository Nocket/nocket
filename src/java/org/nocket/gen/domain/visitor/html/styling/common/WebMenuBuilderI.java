package org.nocket.gen.domain.visitor.html.styling.common;

import java.util.List;

import org.apache.wicket.markup.html.list.ListView;
import org.nocket.component.menu.MenuItem;

/**
 * Builder Interface für ein Nocket Hauptmenü. Dies kann je nach Styling-Strategie unterschiedlich aussehen.
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface WebMenuBuilderI {
	
	/**
	 * Initialisierung des Builder. Hier werden die Initial benötigten Parameter in den Builder gegeben,
	 * welche für den Aufbau eines Menüs benötigt werden.
	 * 
	 * @param wicketId Wicket-ID in der HTML-Seite
	 * @param menuItems Menüelemente für das Rendern des Menüs
	 */
	public void initMenuBuilder(String wicketId, List<MenuItem> menuItems);

	/**
	 * Getter für das fertig erstellte Menü
	 * 
	 * @return Fertig erstelltes Menü
	 */
	public ListView getMenu();
}
