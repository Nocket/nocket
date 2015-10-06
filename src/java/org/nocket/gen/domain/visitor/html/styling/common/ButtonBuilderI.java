/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.common;

import org.nocket.gen.page.element.ButtonElement;
import org.nocket.gen.page.element.synchronizer.ButtonCallback.ButtonCallbackInterceptor;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedButton;

/**
 * Builder für Wicket Buttons in Nocket. Diese müssen sich von der Klasse GeneratedButton ableiten.
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface ButtonBuilderI {
	
	public void initButtonBuilder(ButtonElement e);
	
	public void initButtonBuilder(ButtonElement e, ButtonCallbackInterceptor buttonCallbackInterceptor);
	
	/**
	 * Den fertigen Button ermitteln
	 */
	public GeneratedButton getButton();

}
