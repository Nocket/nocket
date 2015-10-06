/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.model.IModel;
import org.nocket.gen.domain.visitor.html.styling.common.PasswordTextFieldBuilderI;

/**
 * Builder für Passwortfelder unter Bootstrap2
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class Bootstrap2PasswordTextFieldBuilder implements
		PasswordTextFieldBuilderI {
	
	private PasswordTextField field = null;

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.PasswordTextFieldBuilderI#initPasswordTextFieldBuilder(java.lang.String)
	 */
	@Override
	public void initPasswordTextFieldBuilder(String wicketId) {
		field = new PasswordTextField(wicketId);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.PasswordTextFieldBuilderI#initPasswordTextFieldBuilder(java.lang.String, org.apache.wicket.model.IModel)
	 */
	@Override
	public void initPasswordTextFieldBuilder(String wicketId,
			IModel<String> model) {
		field = new PasswordTextField(wicketId, model);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.PasswordTextFieldBuilderI#getPasswordField()
	 */
	@Override
	public PasswordTextField getPasswordField() {
		return field;
	}

}
