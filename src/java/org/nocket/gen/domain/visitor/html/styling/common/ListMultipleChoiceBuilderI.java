package org.nocket.gen.domain.visitor.html.styling.common;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.IModel;

/**
 * Builder für eine MultipleChoice List-Komponente.
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface ListMultipleChoiceBuilderI<T> {

	public void initMultipleChoiceBuilder(final String id);

	/**
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      List)
	 */
	public void initMultipleChoiceBuilder(final String id,
			final List<? extends T> choices);

	/**
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      List)
	 */
	public void initMultipleChoiceBuilder(final String id,
			final List<? extends T> choices, final int maxRows);

	/**
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      List,IChoiceRenderer)
	 */
	public void initMultipleChoiceBuilder(final String id,
			final List<? extends T> choices,
			final IChoiceRenderer<? super T> renderer);

	/**
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      IModel, List)
	 */
	public void initMultipleChoiceBuilder(final String id,
			IModel<? extends Collection<T>> object,
			final List<? extends T> choices);

	/**
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      IModel, List,IChoiceRenderer)
	 */
	public void initMultipleChoiceBuilder(final String id,
			IModel<? extends Collection<T>> object,
			final List<? extends T> choices,
			final IChoiceRenderer<? super T> renderer);

	/**
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      IModel)
	 */
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends List<? extends T>> choices);

	/**
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      IModel,IModel)
	 */
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends Collection<T>> model,
			IModel<? extends List<? extends T>> choices);

	/**
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      IModel,IChoiceRenderer)
	 */
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends List<? extends T>> choices,
			IChoiceRenderer<? super T> renderer);

	/**
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      IModel, IModel,IChoiceRenderer)
	 */
	public void initMultipleChoiceBuilder(String id,
			IModel<? extends Collection<T>> model,
			IModel<? extends List<? extends T>> choices,
			IChoiceRenderer<? super T> renderer);

	/**
	 * Fertig erstellte Multiple Choice Component erhalten
	 */
	public ListMultipleChoice<T> getListMultipleChoice();

}
