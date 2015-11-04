/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.common;

import org.apache.wicket.Component;
import org.nocket.gen.page.element.LinkElement;

/**
 * Builder für Interne und externe Links
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface LinkBuilderI {
	
	/**
	 * Builder initialisieren auf Basis eines LinkElement
	 */
	public void initLinkBuilder(LinkElement e);
	
	/**
	 * Fertigen Link erhalten
	 */
	public Component getLink();

}
