/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.common;

import org.apache.wicket.markup.html.panel.Panel;

/**
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface ModalWindowBuilderI {

	/**
	 * Initialisieren des Builder mit der Wicket-ID
	 * @param wicketId
	 */
	public void initModalWindowBuilder(String wicketId);
	
	/**
	 * Methode gibt das fertig erstellte Panel für den modalen Dialog zurück
	 */
	public Panel getModalWindow();
}
