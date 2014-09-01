package org.nocket.gen.domain.visitor.html;

import org.apache.ecs.Element;
import org.apache.ecs.html.Div;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Label;
import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.DomainElementI;

public class HtmlComponentBuilderBootstrap extends AbstractHtmlComponentBuilder {

    public HtmlComponentBuilderBootstrap(DMDWebGenContext<?> context) {
        super(context);
    }

    protected Element wrapComponent(DomainElementI<?> e, Element element) {
        //<div class="control-group">
        Div controlGroup = new Div();
        controlGroup.addAttribute(ATTR_CLASS, "control-group");
        if (isInputButton(element))
            ((Input) element).setValue(e.getPrompt());
        else {
            //  <label for="vertrag.Vertragsnummer" class="control-label">Salutation</label>
            Label label = createLabel(e, element);
            label.addAttribute(ATTR_CLASS, "control-label");
            controlGroup.addElement(label);
        }
        //  <div class="controls controls-row">
        Div controlsRow = new Div();
        controlsRow.addAttribute(ATTR_CLASS, "controls");
        //    <select class="span4" wicket:id="vertrag.Vertragsnummer" id="vertrag.Vertragsnummer" size="1"><option>[option]</option></select>
        controlsRow.addElement(element);
        //  </div>
        controlGroup.addElement(controlsRow);
        //</div> 
        return controlGroup;
    }

}
