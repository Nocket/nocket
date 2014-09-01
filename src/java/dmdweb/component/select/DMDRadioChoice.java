package dmdweb.component.select;

import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.model.IModel;

public class DMDRadioChoice<T> extends RadioChoice<T> {

    private static final long serialVersionUID = 1L;

    public DMDRadioChoice(String id, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer) {
        super(id, choices, renderer);
        initForBootstrap();
    }

    public DMDRadioChoice(String id, IModel<? extends List<? extends T>> choices) {
        super(id, choices);
        initForBootstrap();
    }

    public DMDRadioChoice(String id, IModel<T> model, IModel<? extends List<? extends T>> choices,
            IChoiceRenderer<? super T> renderer) {
        super(id, model, choices, renderer);
        initForBootstrap();
    }

    public DMDRadioChoice(String id, IModel<T> model, IModel<? extends List<? extends T>> choices) {
        super(id, model, choices);
        initForBootstrap();
    }

    public DMDRadioChoice(String id, IModel<T> model, List<? extends T> choices, IChoiceRenderer<? super T> renderer) {
        super(id, model, choices, renderer);
        initForBootstrap();
    }

    public DMDRadioChoice(String id, IModel<T> model, List<? extends T> choices) {
        super(id, model, choices);
        initForBootstrap();
    }

    public DMDRadioChoice(String id, List<? extends T> choices, IChoiceRenderer<? super T> renderer) {
        super(id, choices, renderer);
        initForBootstrap();
    }

    public DMDRadioChoice(String id, List<? extends T> choices) {
        super(id, choices);
        initForBootstrap();
    }

    public DMDRadioChoice(String id) {
        super(id);
        initForBootstrap();
    }

    private void initForBootstrap() {
        setPrefix("<label class=\"radio\">");
        setSuffix("</label>");
    }

}
