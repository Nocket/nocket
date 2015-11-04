/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.common;

import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

/**
 * Builder für DropDown Felder
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface DropDownBuilderI {

	public void initDropDownBuilder(final String id);

	public void initDropDownBuilder(final String id, final List choices);

	public void initDropDownBuilder(final String id, final List choices,
			final IChoiceRenderer renderer);

	public void initDropDownBuilder(final String id, IModel model,
			final List choices);

	public void initDropDownBuilder(final String id, IModel model,
			final List choices, final IChoiceRenderer renderer);

	public void initDropDownBuilder(String id, IModel<? extends List> choices);

	public void initDropDownBuilder(String id, IModel model,
			IModel<? extends List> choices);

	public void initDropDownBuilder(String id, IModel<? extends List> choices,
			IChoiceRenderer renderer);

	public void initDropDownBuilder(String id, IModel model,
			IModel<? extends List> choices, IChoiceRenderer renderer);
	
	/**
	 * Fertiges DropDown Element erhalten
	 */
	public DropDownChoice getDropDown();
}
