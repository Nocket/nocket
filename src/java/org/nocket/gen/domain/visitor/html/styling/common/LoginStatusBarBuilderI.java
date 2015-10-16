/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.common;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;

/**
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface LoginStatusBarBuilderI extends Serializable {
	
	/**
	 * Status Bar generieren unter angegebener Wicket-ID
	 * @param wicketId Wicket-ID der komponente
	 * @param username Benutzername, der angemeldet ist
	 * @param loginPage die Seite, welche für den Login verantwortlich ist
	 */
	public void init(String wicketId, String username, Class<? extends WebPage> loginPage);
	
	/**
	 * Fertige LoginStatusBar erhalten
	 */
	public Panel getLoginStatusBar();

}
