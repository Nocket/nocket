package org.nocket.gen.domain.visitor.html.gen;

import gengui.domain.AbstractDomainReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.ecs.Element;
import org.apache.ecs.html.Col;
import org.apache.ecs.html.ColGroup;
import org.apache.ecs.html.Form;
import org.apache.ecs.html.Html;
import org.apache.ecs.html.Label;
import org.apache.ecs.html.Legend;
import org.apache.ecs.html.TBody;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;
import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.visitor.html.HtmlComponentBuilderTablegrid;

public class HtmlGeneratorVisitorTablegrid<E extends AbstractDomainReference> extends AbstractHtmlGeneratorVisitor<E> {

    public static final int NUMBER_GRID_ROWS = 5;

    protected Table gridTable;
    protected TBody grid;
    protected Stack<List<Element>> collectedButtons = new Stack<List<Element>>();

    public HtmlGeneratorVisitorTablegrid(DMDWebGenContext<E> context) {
        super(context, new HtmlComponentBuilderTablegrid(context));
        collectedButtons.push(new ArrayList<Element>());
    }

    @Override
    protected Html newHtml() {
        Html html = super.newHtml();
        addTablegrid();
        return html;
    }

    protected void addTablegrid() {
        gridTable = new Table();
        gridTable.setCellPadding(4).setCellSpacing(4).setWidth("100%").setClass("tablegrid");
        Form form = (Form) panelStack.peek();
        form.addElement(gridTable);
        ColGroup gridColumns = new ColGroup();
        gridColumns.addElement(new Col().setSpan(1).setWidth(100));
        gridColumns.addElement(new Col().setSpan(1).setWidth(250));
        gridColumns.addElement(new Col().setSpan(1).setWidth(100));
        gridColumns.addElement(new Col().setSpan(1).setWidth(250));
        gridTable.addElement(gridColumns);
        grid = new TBody();
        gridTable.addElement(grid);
        panelStack.add(grid);
    }

    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {
        TR tablerow = new TR();
        //TODO JL: Legend is not allowed any more in a table cells since HTML  although it looks fine
        Legend legend = new Legend();
        Label label = componentBuilder.createFieldsetHeaderLabel(e);
        legend.addElement(label);
        tablerow.addElement(new TD().setColSpan(5).addElement(legend));
        maybeAdd(tablerow);
        collectedButtons.push(new ArrayList<Element>());
    }

    @Override
    public void visitFieldsetClose() {
        addCollectedButtons();
    }

    protected void addCollectedButtons() {
        List<Element> collectedButtonsOnLevel = collectedButtons.pop();
        if (collectedButtonsOnLevel.size() > 0) {
            TR tablerow = new TR();
            TD tablecell = new TD().setColSpan(NUMBER_GRID_ROWS);
            for (Element button : collectedButtonsOnLevel) {
                tablecell.addElement(button);
            }
            tablerow.addElement(tablecell);
            grid.addElement(tablerow);
        }
    }

    @Override
    public void finish() {
        addCollectedButtons();
        super.finish();
    }

    @Override
    public void visitButton(ButtonElement<E> e) {
        Element button = componentBuilder.createButton(e);
        collectedButtons.peek().add(button);
    }

}
