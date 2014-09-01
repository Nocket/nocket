package dmdweb.gen.domain.visitor.html;

import org.apache.ecs.Element;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Label;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;

import dmdweb.gen.domain.DMDWebGenContext;
import dmdweb.gen.domain.element.DomainElementI;
import dmdweb.gen.domain.element.MultivaluePropertyElement;
import dmdweb.gen.domain.visitor.html.gen.HtmlGeneratorVisitorTablegrid;

public class HtmlComponentBuilderTablegrid extends AbstractHtmlComponentBuilder {

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
