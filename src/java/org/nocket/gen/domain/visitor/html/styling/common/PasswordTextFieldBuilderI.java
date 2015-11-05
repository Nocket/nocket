/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.common;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.model.IModel;

/**
 * Builder für Passwortfelder auf der Webseite
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface PasswordTextFieldBuilderI {
	
	/**
	 * Builder initialisieren
	 */
	public void initPasswordTextFieldBuilder(String wicketId);
	
	/**
	 * Builder initialisieren
	 */
	public void initPasswordTextFieldBuilder(String wicketId, IModel<String> model);
	
	/**
	 * Fertiges Passwortfeld erhalten
	 */
	public PasswordTextField getPasswordField();

}
