/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.model.IModel;
import org.nocket.component.select.DMDRadioChoice;
import org.nocket.gen.domain.visitor.html.styling.common.RadioChoiceBuilderI;

/**
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class Bootstrap2RadioChoiceBuilder implements RadioChoiceBuilderI<Object> {

	private DMDRadioChoice<Object> radioChoice = null;
	
	@Override
	public void initRadioChoiceBuilder(String id) {
		radioChoice = new DMDRadioChoice<Object>(id);
	}

	@Override
	public void initRadioChoiceBuilder(String id, List<? extends Object> choices) {
		radioChoice = new DMDRadioChoice<Object>(id, choices);
	}

	@Override
	public void initRadioChoiceBuilder(String id,
			List<? extends Object> choices,
			IChoiceRenderer<? super Object> renderer) {
		radioChoice = new DMDRadioChoice<Object>(id, choices, renderer);
	}

	@Override
	public void initRadioChoiceBuilder(String id, IModel<Object> model,
			List<? extends Object> choices) {
		radioChoice = new DMDRadioChoice<Object>(id, model, choices);
	}

	@Override
	public void initRadioChoiceBuilder(String id, IModel<Object> model,
			List<? extends Object> choices,
			IChoiceRenderer<? super Object> renderer) {
		radioChoice = new DMDRadioChoice<Object>(id, model, choices, renderer);
	}

	@Override
	public void initRadioChoiceBuilder(String id,
			IModel<? extends List<? extends Object>> choices) {
		radioChoice = new DMDRadioChoice<Object>(id, choices);
	}

	@Override
	public void initRadioChoiceBuilder(String id, IModel<Object> model,
			IModel<? extends List<? extends Object>> choices) {
		radioChoice = new DMDRadioChoice<Object>(id, model, choices);
	}

	@Override
	public void initRadioChoiceBuilder(String id,
			IModel<? extends List<? extends Object>> choices,
			IChoiceRenderer<? super Object> renderer) {
		radioChoice = new DMDRadioChoice<Object>(id, choices, renderer);
	}

	@Override
	public void initRadioChoiceBuilder(String id, IModel<Object> model,
			IModel<? extends List<? extends Object>> choices,
			IChoiceRenderer<? super Object> renderer) {
		radioChoice = new DMDRadioChoice<Object>(id, model, choices, renderer);
	}

	@Override
	public RadioChoice<Object> getRadioChoice() {
		return radioChoice;
	}

}
