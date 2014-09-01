package org.nocket.component.select;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.IModel;

/**
 * A multiple choice list component known from Jira implementation. It depends
 * on JQuery and Chosen JavaScript libraries.
 * 
 * Use it and test it exactly in the same way as standard Wicket's
 * {@link ListMultipleChoice} component.
 * 
 * @author blaz02
 * 
 * @param <T>
 *            The model object type
 */
public class DMDListMultipleChoice<T> extends ListMultipleChoice<T> {

    protected Boolean behaviorsAdded = Boolean.FALSE;

    public DMDListMultipleChoice(String id, IModel<? extends Collection<T>> model,
            IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer) {
        super(id, model, choices, renderer);
    }

    public DMDListMultipleChoice(String id, IModel<? extends Collection<T>> model,
            IModel<? extends List<? extends T>> choices) {
        super(id, model, choices);
    }

    public DMDListMultipleChoice(String id, IModel<? extends Collection<T>> object, List<? extends T> choices,
            IChoiceRenderer<? super T> renderer) {
        super(id, object, choices, renderer);
    }

    public DMDListMultipleChoice(String id, IModel<? extends Collection<T>> object, List<? extends T> choices) {
        super(id, object, choices);
    }

    public DMDListMultipleChoice(String id, IModel<? extends List<? extends T>> choices,
            IChoiceRenderer<? super T> renderer) {
        super(id, choices, renderer);
    }

    public DMDListMultipleChoice(String id, IModel<? extends List<? extends T>> choices) {
        super(id, choices);
    }

    public DMDListMultipleChoice(String id, List<? extends T> choices, IChoiceRenderer<? super T> renderer) {
        super(id, choices, renderer);
    }

    public DMDListMultipleChoice(String id, List<? extends T> choices, int maxRows) {
        super(id, choices, maxRows);
    }

    public DMDListMultipleChoice(String id, List<? extends T> choices) {
        super(id, choices);
    }

    public DMDListMultipleChoice(String id) {
        super(id);
    }

    @Override
    protected void onBeforeRender() {
        if (!behaviorsAdded) {
            add(new JSChosenBehavior("{display_selected_options : false}"));
            behaviorsAdded = true;
        }
        super.onBeforeRender();
    }

}
