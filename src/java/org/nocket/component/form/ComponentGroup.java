package org.nocket.component.form;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.border.Border;

/**
 * Simple border for building component groups. ValidationErrors coming from children
 * components, will be shown under the group. Use it for groups with radio and check-box
 * buttons and for input text fields shown in one line.
 *
 * @author blaz02
 */
public class ComponentGroup extends Border {

	public enum CssClass {
		RADIOCHECKBOX("radio-checkbox-group"),
		//RADIOCHECKBOX("control-group"),
		FORMINLINE("form-inline");

		private String name;

		private CssClass(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private static final long serialVersionUID = 1L;

	public ComponentGroup(String id, ComponentGroup.CssClass cssClass) {
		super(id);
		add(new AttributeModifier("class", cssClass));
	}

}
