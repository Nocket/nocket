/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.nocket.component.form.DMDTextField;
import org.nocket.gen.domain.visitor.html.styling.common.TextFieldBuilderI;
import org.nocket.gen.page.element.TextInputElement;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedDateTextField;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedNumberTextField;

/**
 * Builder für die Textfelder unter BootStrap2
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class Bootstrap2TextFieldBuilder implements TextFieldBuilderI {
	
	private TextField textField = null;
//	private GeneratedNumberTextField genTextField = null;
	
	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.TextFieldBuilderI#initTextFieldBuilder(org.nocket.gen.page.element.TextInputElement)
	 */
	@Override
	public void initTextFieldBuilder(TextInputElement e) {
		if (e.getDomainElement().isNumberType()) {
			GeneratedNumberTextField genTextField = new GeneratedNumberTextField(e);		
			genTextField.postInit(e);
			textField = genTextField;
        } else if (e.getDomainElement().isDateType()) {
            GeneratedDateTextField textInput = new GeneratedDateTextField(e);
            textInput.postInit(e);
            textField = textInput;
        } else {
        	textField = new DMDTextField<Object>(e.getWicketId(), e.getModel());
        }
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.TextFieldBuilderI#initTextFieldBuilder(java.lang.String)
	 */
	@Override
	public void initTextFieldBuilder(String wicketId) {
		textField = new DMDTextField(wicketId);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.TextFieldBuilderI#initTextFieldBuilder(java.lang.String, org.apache.wicket.model.IModel, java.lang.Class)
	 */
	@Override
	public void initTextFieldBuilder(String id, IModel model, Class type) {
		textField = new DMDTextField(id, model, type);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.TextFieldBuilderI#initTextFieldBuilder(java.lang.String, org.apache.wicket.model.IModel)
	 */
	@Override
	public void initTextFieldBuilder(String id, IModel model) {
		textField = new DMDTextField(id, model);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.TextFieldBuilderI#getTextField()
	 */
	@Override
	public TextField getTextField() {
		return textField;
	}

}
