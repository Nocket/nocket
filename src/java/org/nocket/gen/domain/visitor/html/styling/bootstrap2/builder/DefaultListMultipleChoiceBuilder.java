/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.IModel;
import org.nocket.gen.domain.visitor.html.styling.common.ListMultipleChoiceBuilderI;

/**
 * Builder für Multiple Choice Listen, welche die Default Implementierung des bisherigen
 * Nocket nutzt bevor Nocket auf Stylingstrategien umgestellt wurde
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class DefaultListMultipleChoiceBuilder implements
		ListMultipleChoiceBuilderI<String> {
	
	private ListMultipleChoice<String> choice = null;

	@Override
	public void initMultipleChoiceBuilder(String id) {
		choice = new ListMultipleChoice<String>(id);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			List<? extends String> choices) {
		choice = new ListMultipleChoice<String>(id, choices);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			List<? extends String> choices, int maxRows) {
		choice = new ListMultipleChoice<String>(id, choices, maxRows);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			List<? extends String> choices,
			IChoiceRenderer<? super String> renderer) {
		choice = new ListMultipleChoice<String>(id, choices, renderer);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends Collection<String>> object,
			List<? extends String> choices) {
		choice = new ListMultipleChoice<String>(id, object, choices);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends Collection<String>> object,
			List<? extends String> choices,
			IChoiceRenderer<? super String> renderer) {
		choice = new ListMultipleChoice<String>(id, object, choices, renderer);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends List<? extends String>> choices) {
		choice = new ListMultipleChoice<String>(id, choices);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends Collection<String>> model,
			IModel<? extends List<? extends String>> choices) {
		choice = new ListMultipleChoice<String>(id, model, choices);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends List<? extends String>> choices,
			IChoiceRenderer<? super String> renderer) {
		choice = new ListMultipleChoice<String>(id, choices, renderer);
	}

	@Override
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends Collection<String>> model,
			IModel<? extends List<? extends String>> choices,
			IChoiceRenderer<? super String> renderer) {
		choice = new ListMultipleChoice<String>(id, model, choices, renderer);
	}

	@Override
	public ListMultipleChoice<String> getListMultipleChoice() {
		return choice;
	}

}
