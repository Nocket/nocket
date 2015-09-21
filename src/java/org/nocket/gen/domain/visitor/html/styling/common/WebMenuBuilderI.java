package org.nocket.gen.domain.visitor.html.styling.common;

import java.util.List;

import org.apache.wicket.markup.html.list.ListView;
import org.nocket.component.menu.MenuItem;

/**
 * Builder Interface f�r ein Nocket Hauptmen�. Dies kann je nach Styling-Strategie unterschiedlich aussehen.
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface WebMenuBuilderI {
	
	/**
	 * Initialisierung des Builder. Hier werden die Initial ben�tigten Parameter in den Builder gegeben,
	 * welche f�r den Aufbau eines Men�s ben�tigt werden.
	 * 
	 * @param wicketId Wicket-ID in der HTML-Seite
	 * @param menuItems Men�elemente f�r das Rendern des Men�s
	 */
	public void initMenuBuilder(String wicketId, List<MenuItem> menuItems);

	/**
	 * Getter f�r das fertig erstellte Men�
	 * 
	 * @return Fertig erstelltes Men�
	 */
	public ListView getMenu();
}
