package dmdweb.component.form.behaviors;

import java.util.MissingResourceException;

import org.apache.wicket.Component;
import org.apache.wicket.markup.transformer.AbstractTransformerBehavior;
import org.apache.wicket.model.IWrapModel;
import org.apache.wicket.model.ResourceModel;

/**
 * This behavior sets a special css class on the component
 * tag and provides an input hint to help the user
 * 
 * @author vocke03
 *
 */

@SuppressWarnings("serial")
public class InputInfoStyleBehavior extends AbstractTransformerBehavior {

	private String hint;

	public InputInfoStyleBehavior() {
		super();
	}

	@Override
	public CharSequence transform(Component component, CharSequence cs) {
		CharSequence res = cs;
		if(hint != null)
			res = cs + "<p class=\"hint\">" + hint + "</p>";
		return res;
	}

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
