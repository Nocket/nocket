/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import org.nocket.component.modal.AbstractModalWindow;
import org.nocket.component.modal.DMDModalWindow;
import org.nocket.gen.domain.visitor.html.styling.common.ModalWindowBuilderI;

/**
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class DefaultModalWindowBuilder implements ModalWindowBuilderI {
	
	private DMDModalWindow window = null;

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.ModalWindowBuilderI#initModalWindowBuilder(java.lang.String)
	 */
	@Override
	public void initModalWindowBuilder(String wicketId) {
		window = new DMDModalWindow(wicketId);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.ModalWindowBuilderI#getModalWindow()
	 */
	@Override
	public AbstractModalWindow getModalWindow() {
		return window;
	}

}
