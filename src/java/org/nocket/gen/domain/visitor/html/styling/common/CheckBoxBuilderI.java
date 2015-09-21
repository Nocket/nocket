/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.common;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.IModel;

/**
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface CheckBoxBuilderI {
	
	/**
	 * Builder für Checkboxen initialisieren
	 */
	public void initCheckBoxBuilder(String wicketId);

	/**
	 * Builder für Checkboxen initialisieren
	 */
	public void initCheckBoxBuilder(String wicketId, IModel<Boolean> model);

	/**
	 * Fertige Checkbox erhalten
	 */
	public CheckBox getCheckBox();

}
