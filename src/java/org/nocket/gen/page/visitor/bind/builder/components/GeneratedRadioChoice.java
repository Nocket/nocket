package org.nocket.gen.page.visitor.bind.builder.components;

import org.nocket.component.select.DMDRadioChoice;
import org.nocket.gen.page.element.RadioInputElement;

public class GeneratedRadioChoice extends DMDRadioChoice<Object> {

    public GeneratedRadioChoice(RadioInputElement element) {
        super(element.getWicketId(), element.getModel(), element.getChoicesModel(), element.getChoicesRenderer());
    }
}
