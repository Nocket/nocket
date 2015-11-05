/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.common;

import org.nocket.component.modal.AbstractModalWindow;

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
	public AbstractModalWindow getModalWindow();
}
