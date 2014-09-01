package org.nocket.component.select;

import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

/**
 * A choice implemented as a dropdown menu/list with a searching capability. It
 * depends on JQuery and Chosen JavaScript libraries.
 * 
 * Use it and test it exactly in the same way as standard Wicket's
 * {@link DropDownChoice} component.
 * 
 * @author blaz02
 * 
 * @param <T>
 *            The model object type
 */
public class DMDDropDownChoice<T> extends DropDownChoice<T> {

    private static final long serialVersionUID = 1L;

    protected Boolean behaviorsAdded = Boolean.FALSE;

    public DMDDropDownChoice(final String id, IModel<? extends List<? extends T>> choices,
            IChoiceRenderer<? super T> renderer) {
        super(id, choices, renderer);
    }

    public DMDDropDownChoice(final String id, IModel<T> model, IModel<? extends List<? extends T>> choices,
            IChoiceRenderer<? super T> renderer) {
        super(id, model, choices, renderer);
    }

    public DMDDropDownChoice(final String id, IModel<T> model, IModel<? extends List<? extends T>> choices) {
        super(id, model, choices);
    }

    public DMDDropDownChoice(final String id, IModel<T> model, final List<? extends T> choices,
            IChoiceRenderer<? super T> renderer) {
        super(id, model, choices, renderer);
    }

    public DMDDropDownChoice(final String id, IModel<T> model, final List<? extends T> choices) {
        super(id, model, choices);
    }

    public DMDDropDownChoice(final String id, IModel<? extends List<? extends T>> choices) {
        super(id, choices);
    }

    public DMDDropDownChoice(final String id, final List<? extends T> choices, IChoiceRenderer<? super T> renderer) {
        super(id, choices, renderer);
    }

    public DMDDropDownChoice(final String id, final List<? extends T> choices) {
        super(id, choices);
    }

    public DMDDropDownChoice(final String id) {
        super(id);
    }

    @Override
    protected void onBeforeRender() {
        if (!behaviorsAdded) {
            add(new JSChosenBehavior(""));
            behaviorsAdded = true;
        }
        super.onBeforeRender();
    }

}
