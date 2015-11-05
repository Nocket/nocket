/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import org.nocket.gen.domain.visitor.html.styling.common.ButtonBuilderI;
import org.nocket.gen.page.element.ButtonElement;
import org.nocket.gen.page.element.synchronizer.ButtonCallback.ButtonCallbackInterceptor;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedButton;

/**
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class DefaultButtonBuilder implements ButtonBuilderI {

	private GeneratedButton button = null;
	
	@Override
	public void initButtonBuilder(ButtonElement e) {
		button = new GeneratedButton(e);
	}

	@Override
	public void initButtonBuilder(ButtonElement e,
			ButtonCallbackInterceptor buttonCallbackInterceptor) {
		button = new GeneratedButton(e, buttonCallbackInterceptor);
	}

	@Override
	public GeneratedButton getButton() {
		return button;
	}
	

}
