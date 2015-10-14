/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import java.util.List;

import org.nocket.component.menu.MenuItem;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.components.DefaultMenuPanel;
import org.nocket.gen.domain.visitor.html.styling.common.WebMenuBuilderI;

/**
 * Menü-Builder für das Hauptmenü unter Bootstrap 2
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class DefaultWebMenuBuilder implements WebMenuBuilderI {
	
	private DefaultMenuPanel menu = null;

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.WebMenuBuilderI#initMenuBuilder(java.lang.String, java.util.List)
	 */
	@Override
	public void initMenuBuilder(String wicketId, List<MenuItem> menuItems) {
		menu = new DefaultMenuPanel(wicketId, menuItems);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.WebMenuBuilderI#getMenu()
	 */
	@Override
	public DefaultMenuPanel getMenu() {
		return menu;
	}

}
