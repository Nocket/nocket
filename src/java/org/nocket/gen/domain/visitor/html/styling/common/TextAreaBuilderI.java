/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.common;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

/**
 * Builder für Textfelder auf der Seite
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface TextAreaBuilderI {
	
	/**
	 * Builder initialisieren
	 */
	public void initTextAreaBuilder(String wicketId);

	/**
	 * Builder initialisieren
	 */
	public void initTextAreaBuilder(String wicketId, IModel model);

	/**
	 * Fertiges Textfeld erhalten
	 */
	public TextArea getTextArea();

}
