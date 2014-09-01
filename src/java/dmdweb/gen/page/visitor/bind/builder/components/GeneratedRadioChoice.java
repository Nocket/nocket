package dmdweb.gen.page.visitor.bind.builder.components;

import dmdweb.component.select.DMDRadioChoice;
import dmdweb.gen.page.element.RadioInputElement;

public class GeneratedRadioChoice extends DMDRadioChoice<Object> {

    public GeneratedRadioChoice(RadioInputElement element) {
        super(element.getWicketId(), element.getModel(), element.getChoicesModel(), element.getChoicesRenderer());
    }
}
