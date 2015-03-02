package org.nocket.gen.domain.visitor.html.merge;

import gengui.domain.AbstractDomainReference;
import gengui.util.SevereGUIException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ecs.Element;
import org.apache.ecs.html.FieldSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.element.ButtonElement;
import org.nocket.gen.domain.element.CheckboxPropertyElement;
import org.nocket.gen.domain.element.ChoicerPropertyElement;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.domain.element.HeadlineElement;
import org.nocket.gen.domain.element.HiddenPropertyElement;
import org.nocket.gen.domain.element.MultivaluePropertyElement;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;
import org.nocket.gen.domain.visitor.html.AbstractHtmlVisitor;
import org.nocket.gen.domain.visitor.html.layout.HtmlBuilderStrategyI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergeHtmlVisitor<E extends AbstractDomainReference> extends AbstractHtmlVisitor<E> {

    final private static Logger log = LoggerFactory.getLogger(MergeHtmlVisitor.class);

    private final Deque<AbstractMergeLayer<?>> panelStack = new ArrayDeque<AbstractMergeLayer<?>>();
    private final HtmlBuilderStrategyI componentBuilder;
    protected final Document document;
    private String originalHtml;

    public MergeHtmlVisitor(DMDWebGenContext<E> context, HtmlBuilderStrategyI componentBuilder) {
        super(context);
        this.componentBuilder = componentBuilder;
        try {
            String html = FileUtils.readFileToString(getHtmlFile());
            this.document = Jsoup.parse(html);
            // Store original html to compare to during finishing process
            this.originalHtml = toHtml(document);
            org.jsoup.nodes.Element rootElement = determineRootElement();
            panelStack.add(new DomElementMergeLayer(rootElement));
        } catch (IOException e) {
            throw new SevereGUIException(e);
        }
    }

    protected org.jsoup.nodes.Element determineRootElement() {
        Elements forms = document.getElementsByTag("form");
        if (forms.size() > 0) {
            return forms.get(forms.size() - 1);
        }
        Elements wicketExtends = document.getElementsByTag("wicket:extend");
        if (wicketExtends.size() > 0) {
            return wicketExtends.get(wicketExtends.size() - 1);
        }
        Elements wicketPanels = document.getElementsByTag("wicket:panel");
        if (wicketPanels.size() > 0) {
            return wicketPanels.get(wicketPanels.size() - 1);
        }
        Elements bodies = document.getElementsByTag("body");
        if (bodies.size() > 0) {
            return bodies.get(bodies.size() - 1);
        }
        return document;
    }

    protected void maybeAdd(DomainElementI<E> e, Element... components) {
        if (document.getElementsByAttributeValue("wicket:id", e.getWicketId()).isEmpty()) {
            for (Element component : components) {
                panelStack.getLast().addElement(component);
            }
        }
    }

    @Override
    public void visitSimpleProperty(SimplePropertyElement<E> e) {
        Element input = componentBuilder.createSimpleProperty(e);
        maybeAdd(e, input);
    }

    @Override
    public void visitChoicerProperty(ChoicerPropertyElement<E> e) {
        Element select = componentBuilder.createChoicerProperty(e);
        maybeAdd(e, select);
    }

    @Override
    public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {
        Element checkbox = componentBuilder.createCheckboxProperty(e);
        maybeAdd(e, checkbox);
    }

    @Override
    public void visitButton(ButtonElement<E> e) {
        Element button = componentBuilder.createButton(e);
        maybeAdd(e, button);
    }

    @Override
    public void visitResource(ResourceElement<E> e) {
        Element resource = componentBuilder.createResource(e);
        maybeAdd(e, resource);
    }

    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {
        FieldSet fieldset = componentBuilder.createFieldset(e);
        panelStack.add(new MultiPartElementMergeLayer(fieldset));
    }

    @Override
    public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {
        Element table = componentBuilder.createMultivalueProperty(e);
        maybeAdd(e, table);
    }

    @Override
    public void visitFieldsetClose() {
        // on close there should always be a fieldset on top of stack
        MultiPartElementMergeLayer fieldset = (MultiPartElementMergeLayer) panelStack.removeLast();
        if (fieldset == null) {
            throw new IllegalStateException("PanelStack improperly implemented, it should never become empty!");
        }
        if (fieldset.getCountElements() > 0) {
            // this fieldset has elements, so we don't just throw it away
            AbstractMergeLayer<?> previousFieldset = panelStack.getLast();
            previousFieldset.addElement(fieldset.getPanel());
        }
    }

    @Override
    public void visitHiddenProperty(HiddenPropertyElement<E> e) {
        // ignore
    }

    @Override
    public void finish() {
        checkAndAddGroupTabbedPanel(document);

        String gen = toHtml(document);
        File file = getHtmlFile();

        // Only save files with changes
        if (StringUtils.equals(gen, originalHtml)) {
            writeStatics(file.getName(), false, false, null);
            log.debug("No changes in file = " + file.getName());
            return;
        }

        try {
            FileUtils.forceMkdir(file.getParentFile());
            FileUtils.writeStringToFile(file, gen);
            writeStatics(file.getName(), false, true, null);
        } catch (IOException e) {
            writeStatics(file.getName(), false, false, e.getMessage());
            throw new SevereGUIException(e);
        }
    }
}
