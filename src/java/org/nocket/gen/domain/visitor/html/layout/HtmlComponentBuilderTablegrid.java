package org.nocket.gen.domain.visitor.html.layout;

import org.apache.ecs.Element;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Label;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;
import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.visitor.html.create.HtmlGeneratorVisitorTablegrid;

@Deprecated
public class HtmlComponentBuilderTablegrid extends AbstractHtmlLayoutStrategy {

    public HtmlComponentBuilderTablegrid(DMDWebGenContext<?> context) {
        super(context);
    }

    protected Element wrapComponent(DomainElementI<?> e, Element component) {
        //<tr><td align="right"><label>...</label></td><td>component</td> <td></td><td></td><td></td></tr>
        TR tablerow = new TR();
        Label label = null;
        if (isInputButton(component))
            ((Input) component).setValue(e.getPrompt());
        else
            label = createLabel(e, component);
        // Label in right-aligned table cell
        tablerow.addElement(new TD().setAlign("right").addElement(label));
        tablerow.addElement(new TD().addElement(component));
        tablerow.addElement(new TD()).addElement(new TD()).addElement(new TD());
        return tablerow;
    }

    @Override
    protected Element wrapTableComponent(MultivaluePropertyElement<?> e, Table table) {
        //<tr><td colspan="5">component</td></tr>
        TR tablerow = new TR();
        tablerow.addElement(new TD().setColSpan(HtmlGeneratorVisitorTablegrid.NUMBER_GRID_ROWS).addElement(table));
        return tablerow;
    }

}
