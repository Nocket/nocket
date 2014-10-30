package org.nocket.component.form.behaviors;

import java.util.MissingResourceException;

import org.apache.wicket.Component;
import org.apache.wicket.markup.transformer.AbstractTransformerBehavior;
import org.apache.wicket.model.IWrapModel;
import org.apache.wicket.model.ResourceModel;

// TODO: Auto-generated Javadoc
/**
 * This behavior sets a special css class on the component
 * tag and provides an input hint to help the user.
 *
 * @author vocke03
 */

@SuppressWarnings("serial")
public class InputInfoStyleBehavior extends AbstractTransformerBehavior {

	/** The hint. */
	private String hint;

	/**
	 * Instantiates a new input info style behavior.
	 */
	public InputInfoStyleBehavior() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.transformer.AbstractTransformerBehavior#transform(org.apache.wicket.Component, java.lang.CharSequence)
	 */
	@Override
	public CharSequence transform(Component component, CharSequence cs) {
		CharSequence res = cs;
		if(hint != null)
			res = cs + "<p class=\"hint\">" + hint + "</p>";
		return res;
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.behavior.Behavior#bind(org.apache.wicket.Component)
	 */
	@Override
	public void bind(Component component) {
		try {
			final IWrapModel<String> model = new ResourceModel(component.getId() + ".hint", null).wrapOnAssignment(component);
			this.hint = model.getObject();
		} catch (MissingResourceException e) {
			hint = null;
		}
		super.bind(component);
	}

}
