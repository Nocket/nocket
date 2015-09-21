/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.common;

import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

/**
 * Builder für List-Views
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface RepeatingViewBuilderI {

	/**
	 * Builder initialisieren
	 */
	public void initRepeatingViewBuilder(String id);
	
	/**
	 * Builder initialisieren
	 */
	public void initRepeatingViewBuilder(String id, IModel<?> model);
	
	/**
	 * Fertige View erhalten
	 */
	public RepeatingView getRepeatingView();

}
