/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.common;

import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.model.IModel;

/**
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface ImageBuilderI {
	
	/**
	 * Builder f�r die Bilder initialisieren
	 */
	public void initImageBuilder(String wicketId, IModel contextRelativePath);
	
	/**
	 * Builder f�r die Bilder initialisieren
	 */
	public void initImageBuilder(String wicketId, String contextRelativePath);

	/**
	 * Fertiges Bildelement erhalten
	 */
	public ContextImage getImage();
	
}
