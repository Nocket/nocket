/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.IModel;
import org.nocket.component.select.DMDListMultipleChoice;
import org.nocket.gen.domain.visitor.html.styling.common.ListMultipleChoiceBuilderI;

/**
 * Builder für Multiple Choice Listen, welche die Default Implementierung des bisherigen
 * Nocket nutzt bevor Nocket auf Stylingstrategien umgestellt wurde
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class Bootstrap2ListMultipleChoiceBuilder implements
		ListMultipleChoiceBuilderI<String> {
	
	private DMDListMultipleChoice<String> choice = null;

	@Override
	public void initMultipleChoiceBuilder(String id) {
		choice = new DMDListMultipleChoice<String>(id);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			List<? extends String> choices) {
		choice = new DMDListMultipleChoice<String>(id, choices);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			List<? extends String> choices, int maxRows) {
		choice = new DMDListMultipleChoice<String>(id, choices, maxRows);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			List<? extends String> choices,
			IChoiceRenderer<? super String> renderer) {
		choice = new DMDListMultipleChoice<String>(id, choices, renderer);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends Collection<String>> object,
			List<? extends String> choices) {
		choice = new DMDListMultipleChoice<String>(id, object, choices);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends Collection<String>> object,
			List<? extends String> choices,
			IChoiceRenderer<? super String> renderer) {
		choice = new DMDListMultipleChoice<String>(id, object, choices, renderer);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends List<? extends String>> choices) {
		choice = new DMDListMultipleChoice<String>(id, choices);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends Collection<String>> model,
			IModel<? extends List<? extends String>> choices) {
		choice = new DMDListMultipleChoice<String>(id, model, choices);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends List<? extends String>> choices,
			IChoiceRenderer<? super String> renderer) {
		choice = new DMDListMultipleChoice<String>(id, choices, renderer);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends Collection<String>> model,
			IModel<? extends List<? extends String>> choices,
			IChoiceRenderer<? super String> renderer) {
		choice = new DMDListMultipleChoice<String>(id, model, choices, renderer);
	}

	@Override
	public ListMultipleChoice<String> getListMultipleChoice() {
		return choice;
	}

}
