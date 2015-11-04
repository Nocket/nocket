/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.common;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.nocket.gen.page.element.TextInputElement;

/**
 * Builder für Texteingabefelder
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface TextFieldBuilderI {
	
	/**
	 * Initialisieren des Builder (Parameter sind hier die Parameter des Konstruktors der TextField Klasse)
	 */
	public void initTextFieldBuilder(TextInputElement e);

	/**
	 * Initialisieren des Builder (Parameter sind hier die Parameter des Konstruktors der TextField Klasse)
	 */
	public void initTextFieldBuilder(String wicketId);

	/**
	 * Initialisieren des Builder (Parameter sind hier die Parameter des Konstruktors der TextField Klasse)
	 */
	public void initTextFieldBuilder(String id, IModel model, Class type);

	/**
	 * Initialisieren des Builder (Parameter sind hier die Parameter des Konstruktors der TextField Klasse)
	 */
	public void initTextFieldBuilder(String id, IModel model);

	/**
	 * Fertiges Textfeld erhalten
	 */
	public TextField getTextField();
}
