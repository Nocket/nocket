package org.nocket.gen.domain.visitor.html.styling.common;

import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.model.IModel;

/**
 * Builder für Radio-Button Auswahlelemente in einer Styling-Strategie
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface RadioChoiceBuilderI<T> {

	/**
	 * @see org.apache.wicket.Component#Component(String)
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String)
	 */
	public void initRadioChoiceBuilder(final String id);

	/**
	 * @see org.apache.wicket.Component#Component(String)
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      List)
	 */
	public void initRadioChoiceBuilder(final String id,
			final List<? extends T> choices);

	/**
	 * @see org.apache.wicket.Component#Component(String)
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      List,IChoiceRenderer)
	 */
	public void initRadioChoiceBuilder(final String id,
			final List<? extends T> choices,
			final IChoiceRenderer<? super T> renderer);

	/**
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      IModel, List)
	 */
	public void initRadioChoiceBuilder(final String id, IModel<T> model,
			final List<? extends T> choices);

	/**
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      IModel, List,IChoiceRenderer)
	 */
	public void initRadioChoiceBuilder(final String id, IModel<T> model,
			final List<? extends T> choices,
			final IChoiceRenderer<? super T> renderer);

	/**
	 * @see org.apache.wicket.Component#Component(String)
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      IModel)
	 */
	public void initRadioChoiceBuilder(String id,
			IModel<? extends List<? extends T>> choices);

	/**
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      IModel,IModel)
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public void initRadioChoiceBuilder(String id, IModel<T> model,
			IModel<? extends List<? extends T>> choices);

	/**
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      IModel,IChoiceRenderer)
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public void initRadioChoiceBuilder(String id,
			IModel<? extends List<? extends T>> choices,
			IChoiceRenderer<? super T> renderer);

	/**
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 * @see org.apache.wicket.markup.html.form.AbstractChoice#AbstractChoice(String,
	 *      IModel, IModel,IChoiceRenderer)
	 */
	public void initRadioChoiceBuilder(String id, IModel<T> model,
			IModel<? extends List<? extends T>> choices,
			IChoiceRenderer<? super T> renderer);
	
	/**
	 * Fertig gebautes Radio-Choice element erhalten
	 */
	public RadioChoice<T> getRadioChoice();

}
