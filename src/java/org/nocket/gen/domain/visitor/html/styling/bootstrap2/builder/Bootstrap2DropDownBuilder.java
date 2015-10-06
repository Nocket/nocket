/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.nocket.gen.domain.visitor.html.styling.common.DropDownBuilderI;

/**
 * Builder für Dropdown-Listen
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Bootstrap2DropDownBuilder implements DropDownBuilderI {
	
	private DropDownChoice field = null;

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.DropDownBuilderI#initDropDownBuilder(java.lang.String)
	 */
	@Override
	public void initDropDownBuilder(String id) {
		field = new DropDownChoice(id);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.DropDownBuilderI#initDropDownBuilder(java.lang.String, java.util.List)
	 */
	@Override
	public void initDropDownBuilder(String id, List choices) {
		field = new DropDownChoice(id, choices);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.DropDownBuilderI#initDropDownBuilder(java.lang.String, java.util.List, org.apache.wicket.markup.html.form.IChoiceRenderer)
	 */
	@Override
	public void initDropDownBuilder(String id, List choices,
			IChoiceRenderer renderer) {
		field = new DropDownChoice(id, choices, renderer);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.DropDownBuilderI#initDropDownBuilder(java.lang.String, org.apache.wicket.model.IModel, java.util.List)
	 */
	@Override
	public void initDropDownBuilder(String id, IModel model, List choices) {
		field = new DropDownChoice(id, model, choices);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.DropDownBuilderI#initDropDownBuilder(java.lang.String, org.apache.wicket.model.IModel, java.util.List, org.apache.wicket.markup.html.form.IChoiceRenderer)
	 */
	@Override
	public void initDropDownBuilder(String id, IModel model, List choices,
			IChoiceRenderer renderer) {
		field = new DropDownChoice(id, model, choices, renderer);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.DropDownBuilderI#initDropDownBuilder(java.lang.String, org.apache.wicket.model.IModel)
	 */
	@Override
	public void initDropDownBuilder(String id, IModel<? extends List> choices) {
		field = new DropDownChoice(id, choices);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.DropDownBuilderI#initDropDownBuilder(java.lang.String, org.apache.wicket.model.IModel, org.apache.wicket.model.IModel)
	 */
	@Override
	public void initDropDownBuilder(String id, IModel model,
			IModel<? extends List> choices) {
		field = new DropDownChoice(id, model, choices);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.DropDownBuilderI#initDropDownBuilder(java.lang.String, org.apache.wicket.model.IModel, org.apache.wicket.markup.html.form.IChoiceRenderer)
	 */
	@Override
	public void initDropDownBuilder(String id, IModel<? extends List> choices,
			IChoiceRenderer renderer) {
		field = new DropDownChoice(id, choices, renderer);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.DropDownBuilderI#initDropDownBuilder(java.lang.String, org.apache.wicket.model.IModel, org.apache.wicket.model.IModel, org.apache.wicket.markup.html.form.IChoiceRenderer)
	 */
	@Override
	public void initDropDownBuilder(String id, IModel model,
			IModel<? extends List> choices, IChoiceRenderer renderer) {
		field = new DropDownChoice(id, model, choices, renderer);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.DropDownBuilderI#getDropDown()
	 */
	@Override
	public DropDownChoice getDropDown() {
		return field;
	}

}
