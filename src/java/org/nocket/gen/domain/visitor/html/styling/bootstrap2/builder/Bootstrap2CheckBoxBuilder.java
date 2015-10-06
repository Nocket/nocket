/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.IModel;
import org.nocket.gen.domain.visitor.html.styling.common.CheckBoxBuilderI;

/**
 * Builder für Checkboxen unter Bootstrap2
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class Bootstrap2CheckBoxBuilder implements CheckBoxBuilderI {
	
	private CheckBox field = null;

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.CheckBoxBuilderI#initCheckBoxBuilder(java.lang.String)
	 */
	@Override
	public void initCheckBoxBuilder(String wicketId) {
		field = new CheckBox(wicketId);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.CheckBoxBuilderI#initCheckBoxBuilder(java.lang.String, org.apache.wicket.model.IModel)
	 */
	@Override
	public void initCheckBoxBuilder(String wicketId, IModel<Boolean> model) {
		field = new CheckBox(wicketId, model);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.CheckBoxBuilderI#getCheckBox()
	 */
	@Override
	public CheckBox getCheckBox() {
		return field;
	}

}
