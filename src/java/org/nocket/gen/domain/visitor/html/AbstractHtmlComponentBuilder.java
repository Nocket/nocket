package org.nocket.gen.domain.visitor.html;

import gengui.guibuilder.FormBuilder;
import gengui.util.ReflectionUtil;

import org.apache.ecs.Element;
import org.apache.ecs.html.A;
import org.apache.ecs.html.Button;
import org.apache.ecs.html.FieldSet;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Label;
import org.apache.ecs.html.Legend;
import org.apache.ecs.html.Select;
import org.apache.ecs.html.Span;
import org.apache.ecs.html.TBody;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TH;
import org.apache.ecs.html.THead;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;
import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.WebDomainProperties;
import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.CheckboxPropertyElement;
import org.nocket.gen.domain.element.ChoicerPropertyElement;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.element.MultivalueButtonElement;
import org.nocket.gen.domain.element.MultivalueColumnElement;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;
import org.nocket.gen.page.element.SelectElement;

abstract public class AbstractHtmlComponentBuilder {

    public static final String ATTR_WICKET_ID = "wicket:id";
    public static final String ATTR_WICKET_FOR = "wicket:for";
    public static final String ATTR_ID = "id";
    public static final String ATTR_FOR = "for";
    public static final String ATTR_CLASS = "class";
    public static final String ATTR_TYPE = "type";
    public static final String ATTR_STYLE = "style";
    public static final String ATTR_VALUE_ACTIONCOLUMN = "actionColumn";
    public static final String ATTR_VALUE_WICKET_ORDER_UP = "wicket_orderUp";
    public static final String ATTR_VALUE_WICKET_ORDER_NONE = "wicket_orderNone";
    public static final String ATTR_VALUE_WICKET_ORDER_DOWN = "wicket_orderDown";

    protected final DMDWebGenContext<?> context;

    protected AbstractHtmlComponentBuilder(DMDWebGenContext<?> context) {
        this.context = context;
    }

    public Label createLabel(DomainElementI<?> de, Element e) {
        Label label = new Label();
        String prompt = de.getPrompt();
        // Wicket doesn't support Labels for button-types input fields which we are using for file downloads
        if (isInputButton(e)) {
            label.setTagText(prompt);
        } else {
            label.addAttribute(ATTR_WICKET_ID, de.getWicketId() + ".label");
            label.addAttribute("for", de.getWicketId());
            label.addElement(de.getPrompt());
        }
        return label;
    }

    protected boolean isInputButton(Element e) {
        return (e instanceof Input) && ((Input) e).getAttribute(ATTR_TYPE) != null
                && ((Input) e).getAttribute(ATTR_TYPE).equalsIgnoreCase(Input.button);
    }

    public Element createSimpleProperty(SimplePropertyElement<?> e) {
        // <input type="text" wicket:id="vertrag.Vertragsnummer" />
        final String inputType;
        if (e.isNumberType()) {
            inputType = "text";
        } else if (e.isFileType()) {
            if (e.isReadonlyFileType())
                inputType = Input.button; // Download link
            else
                inputType = Input.file;
        } else {
            inputType = Input.text;
        }
        Input input = new Input();
        input.addAttribute(ATTR_WICKET_ID, e.getWicketId());
        input.setID(e.getWicketId());
        input.setType(inputType);
        return wrapInputComponent(e, input);
    }

    protected Element wrapInputComponent(SimplePropertyElement<?> e, Input input) {
        return wrapComponent(e, input);
    }

    /**
     * Wraps a component into a surrounding HTML bed, which includes the
     * creating of a corresponding prompt label an the creating of HTML
     * structures for a proper side-by-side layout of label and actual
     * component. the implementation of the wrapping operation is very different
     * depending on the layout strategy. The default implementation here simply
     * does nothing but returns the passed Element as is.
     */
    protected Element wrapComponent(DomainElementI<?> e, Element component) {
        return component;
    }

    public Element createChoicerProperty(ChoicerPropertyElement<?> e) {
        // <select wicket:id="laenderkennzeichen">
        Select select = new Select();
        select.addAttribute(ATTR_WICKET_ID, e.getWicketId());
        if (ReflectionUtil.isMultivalued(e.getMethod()))
            select.addAttribute(SelectElement.SELECTIONTYPE_ATTRIBUTE, SelectElement.MULTISELECT_VALURE);
        select.setID(e.getWicketId());
        return wrapSelectComponent(e, select);
    }

    private Element wrapSelectComponent(ChoicerPropertyElement<?> e, Select select) {
        return wrapComponent(e, select);
    }

    public Element createCheckboxProperty(CheckboxPropertyElement<?> e) {
        // <input type="checkbox" wicket:id="vertrag.Vertragsnummer" />
        Input input = new Input();
        input.addAttribute(ATTR_WICKET_ID, e.getWicketId());
        input.setID(e.getWicketId());
        input.setType(Input.checkbox);
        return wrapCheckboxComponent(e, input);
    }

    protected Element wrapCheckboxComponent(CheckboxPropertyElement<?> e, Input input) {
        return wrapComponent(e, input);
    }

    public Element createButton(ButtonElement<?> e) {
        // <button id="someButtonToo" class="btn" type="submit"
        // wicket:id="someButtonToo"><span wicket:id="someButtonToo.label">Some
        // Button Too</span></button>
        Button button = new Button();
        button.addAttribute(ATTR_WICKET_ID, e.getWicketId());
        button.addAttribute(ATTR_CLASS, "btn");
        button.setID(e.getWicketId());
        button.setType(Button.submit);
        Span label = new Span();
        label.addAttribute(ATTR_WICKET_ID, e.getWicketId() + ".label");
        label.addElement(e.getPrompt());
        button.addElement(label);
        return wrapButtonComponent(e, button);
    }

    public Element createResource(ResourceElement<?> e) {
        A aTag = new A();
        aTag.addAttribute(ATTR_WICKET_ID, e.getWicketId());
        aTag.addAttribute(ATTR_CLASS, "btn");
        aTag.setID(e.getWicketId());
        Span label = new Span();
        label.addAttribute(ATTR_WICKET_ID, e.getWicketId() + ".label");
        label.addElement(e.getPrompt());
        aTag.addElement(label);
        return wrapATagComponent(e, aTag);
    }

    protected Element wrapATagComponent(ResourceElement<?> e, A anchor) {
        return anchor;
    }

    protected Element wrapButtonComponent(ButtonElement<?> e, Button button) {
        return button;
    }

    public FieldSet createFieldset(HeadlineElement<?> headlineElement) {
        // <fieldset><legend><label>my.text</label></legend></fieldset>
        FieldSet fieldset = new FieldSet();
        Label label = createFieldsetHeaderLabel(headlineElement);
        fieldset.addElement(new Legend(label));
        return fieldset;
    }

    public Label createFieldsetHeaderLabel(HeadlineElement<?> headlineElement) {
        Label label = new Label();
        WebDomainProperties configuration = context.getDomainProperties();
        String key;
        if (configuration.isLocalizationWicket()) {
            key = headlineElement.getWicketId() + ".text";
        } else {
            // package.ClassName._property.text
            key = FormBuilder.buildPromptIdentifier(headlineElement.getAccessor().getClassRef(),
                    headlineElement.getMethod(), headlineElement.getPrompt());
        }
        label.addElement(key);
        return label;
    }

    public Element createMultivalueProperty(MultivaluePropertyElement<?> e) {
        // <table wicket:id="tabelle">
        //   <thead>
        //      <tr class="headers">
        //          <th class="wicket_orderUp"><a href="#">Spalte 1</a></th>
        //          <th><a href="#">Spalte2</a></th>
        //          <th><a href="#">Spalte3</a></th>
        //          <th class="actionColumn">Button Tabelle</th>
        //      </tr>
        //   </thead>
        //   <tbody>
        //      <tr class="even">
        //          <td>&nbsp;</td><td></td><td></td><td></td>
        //      </tr>
        //      <tr class="odd">
        //          <td>&nbsp;</td><td></td><td></td><td></td>
        //      </tr>
        //    </tbody>
        // </table>
        Table table = new Table();
        table.addAttribute(ATTR_WICKET_ID, e.getWicketId());
        table.setID(e.getWicketId());
        if (context.getDomainProperties().getHTMLTableExampleContent()) {
            //TODO JL: CSS-Klasse ist kopiert aus enericDataTablePanel.html. Das müssen wir statt dessen rausparsen
            table.addAttribute(ATTR_CLASS, "table table-striped table-hover table-condensed");
            table.addElement(createExampleTableHeader(e));
            table.addElement(createExampleTableBody(e));
        }
        return wrapTableComponent(e, table);
    }

    protected THead createExampleTableHeader(MultivaluePropertyElement<?> e) {
        THead thead = new THead();
        TR tr = new TR();
        // TODO JL: CSS-Klasse stammt aus Analyse zur Applikationslaufzeit. Lässt die sich auch irgendwo rausparsen?
        tr.addAttribute(ATTR_CLASS, "header");
        boolean isFirstColumn = true;
        for (MultivalueColumnElement dataColumn : e.getColumns()) {
            TH th = new TH();
            // TODO JL: Link und class darf nur rein, wenn man später auch sortieren kann (List vs. Collection)
            th.addAttribute(ATTR_CLASS, isFirstColumn ? "wicket_orderUp" : "wicket_orderNone");
            th.setID(dataColumn.getPropertyName());
            A sortLink = new A();
            sortLink.setHref("#");
            sortLink.setTagText(dataColumn.getPrompt());
            th.addElement(sortLink);
            tr.addElement(th);
            isFirstColumn = false;
        }
        for (MultivalueButtonElement button : e.getButtonElements()) {
            TH th = new TH();
            th.addAttribute(ATTR_CLASS, ATTR_VALUE_ACTIONCOLUMN);
            th.setTagText(button.getPrompt());
            th.setID(button.getPropertyName());
            tr.addElement(th);
        }
        thead.addElement(tr);
        return thead;
    }

    protected TBody createExampleTableBody(MultivaluePropertyElement<?> e) {
        TBody tbody = new TBody();
        // Adding two lines shows the alternatiing color effect and gives a better impression in general
        tbody.addElement(createExampleTableBodyLine(e, "even"));
        tbody.addElement(createExampleTableBodyLine(e, "odd"));
        return tbody;
    }

    private TR createExampleTableBodyLine(MultivaluePropertyElement<?> e, String rowClass) {
        TR tr = new TR();
        int numberOfCells = e.getColumns().size() + e.getButtonElements().size();
        // Adding a non-breakable space in the first cell causes the line to become a realistic height
        tr.addElement(new TD().setTagText("&nbsp;"));
        for (int i = 1; i < numberOfCells; i++)
            tr.addElement(new TD());
        return tr;
    }

    protected Element wrapTableComponent(MultivaluePropertyElement<?> e, Table table) {
        return table;
    }

}
