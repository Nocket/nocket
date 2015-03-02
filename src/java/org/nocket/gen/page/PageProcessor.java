package org.nocket.gen.page;

import gengui.domain.DomainObjectReference;
import gengui.guiadapter.ElementNotFoundException;
import gengui.util.SevereGUIException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nocket.gen.domain.element.DomainElementI;
import org.nocket.gen.domain.visitor.html.layout.AbstractHtmlLayoutStrategy;
import org.nocket.gen.page.element.AbstractNoDomainPageElement;
import org.nocket.gen.page.element.BodyElement;
import org.nocket.gen.page.element.ButtonElement;
import org.nocket.gen.page.element.CheckboxInputElement;
import org.nocket.gen.page.element.ContainerElement;
import org.nocket.gen.page.element.DivElement;
import org.nocket.gen.page.element.FeedbackElement;
import org.nocket.gen.page.element.FileDownloadElement;
import org.nocket.gen.page.element.FileInputElement;
import org.nocket.gen.page.element.FormElement;
import org.nocket.gen.page.element.GroupTabbedPanelElement;
import org.nocket.gen.page.element.HeaderLinkElement;
import org.nocket.gen.page.element.HeaderScriptElement;
import org.nocket.gen.page.element.ImageElement;
import org.nocket.gen.page.element.LabelElement;
import org.nocket.gen.page.element.LinkElement;
import org.nocket.gen.page.element.ModalElement;
import org.nocket.gen.page.element.PageElementI;
import org.nocket.gen.page.element.PasswordInputElement;
import org.nocket.gen.page.element.PromptElement;
import org.nocket.gen.page.element.RadioInputElement;
import org.nocket.gen.page.element.RepeatingPanelElement;
import org.nocket.gen.page.element.SelectElement;
import org.nocket.gen.page.element.TableElement;
import org.nocket.gen.page.element.TextAreaElement;
import org.nocket.gen.page.element.TextInputElement;
import org.nocket.gen.page.element.UnknownDomainElement;
import org.nocket.gen.page.element.UnknownElement;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class PageProcessor {

    private PageElementVisitorI visitor;

    public PageProcessor(PageElementVisitorI visitor) {
        this.visitor = visitor;
    }

    public DMDWebGenPageContext getContext() {
        return visitor.getContext();
    }

    public void process() throws ElementNotFoundException {
        Document doc = getContext().getHtmlDocument();
        traverseHead(doc);
        traverse(doc);

        // finish
        List<DomainElementI<DomainObjectReference>> unboundDomainElements = new ArrayList<DomainElementI<DomainObjectReference>>();
        for (DomainElementI<DomainObjectReference> e : getContext().getDomainRegistry().getElements()) {
            if (!e.repeated() && getContext().getComponentRegistry().getComponent(e.getWicketId()) == null) {
                unboundDomainElements.add(e);
            }
        }
        visitor.finish(unboundDomainElements);
    }

    private void traverseHead(Document doc) {
        Elements bodys = doc.getElementsByTag("body");
        // Jedes HTML hat ein Body-Element
        BodyElement body = new BodyElement(getContext(), bodys.first());
        body.accept(visitor);

        Elements heads = doc.getElementsByTag("head");
        for (Element head : heads) {
            for (Element headElement : head.children()) {
                if (headElement.tagName().equals("link")) {
                    HeaderLinkElement le = new HeaderLinkElement(getContext(), headElement);
                    le.accept(visitor);
                } else if (headElement.tagName().equals("script")) {
                    HeaderScriptElement se = new HeaderScriptElement(getContext(), headElement);
                    se.accept(visitor);
                }
            }
        }
    }

    private void traverse(Element element) {
        boolean elementVisited = false;
        try {
            PageElementI<?> pageElement = detectPageElement(element);
            if (pageElement != null) {
                // not in domain object
                if (isUnknownElement(pageElement)) {
                    pageElement = new UnknownElement(getContext(), element);
                }
                // open container
                if (element.children().size() > 0) {
                    new ContainerElement(getContext(), element).accept(visitor);
                }
                // visit
                pageElement.accept(visitor);
                elementVisited = true;
            }
        } catch (Throwable t) {
            throw new SevereGUIException("On element: " + element.toString(), t);
        }
        // visit children
        for (Element child : element.children()) {
            traverse(child);
        }
        if (elementVisited && element.children().size() > 0) {
            // close container
            visitor.visitContainerClose();
        }
    }

    protected boolean isUnknownElement(PageElementI<?> pageElement) {
        return !(pageElement instanceof AbstractNoDomainPageElement)
                && getContext().getDomainRegistry().getElement(pageElement.getWicketId()) == null;
    }

    private PageElementI<?> detectPageElement(Element element) {
        String wicketId = element.attr(AbstractHtmlLayoutStrategy.ATTR_WICKET_ID);
        if (StringUtils.isBlank(wicketId)) {
            return null;
        }

        if (element.tagName().equals("div")) {
            if (element.attr(AbstractHtmlLayoutStrategy.ATTR_WICKET_ID).equals(FeedbackElement.DEFAULT_WICKET_ID)) {
                return new FeedbackElement(getContext(), element);
            }
            if (element.attr(AbstractHtmlLayoutStrategy.ATTR_WICKET_ID).equals(ModalElement.DEFAULT_WICKET_ID)) {
                return new ModalElement(getContext(), element);
            } else {
                return new DivElement(getContext(), element);
            }
        }
        if (element.tagName().equals("ol")) {
            return new RepeatingPanelElement(getContext(), element);
        }
        if (element.tagName().equals("ul")) {
            return new GroupTabbedPanelElement(getContext(), element);
        }
        if (element.tagName().equals("form")) {
            return new FormElement(getContext(), element);
        }
        if (element.tagName().equals("label") && !(element.hasAttr("wicket:for") || element.hasAttr("for"))) {
            return new LabelElement(getContext(), element);
        }
        if (element.tagName().equals("label") && element.hasAttr("for")) {
            return new PromptElement(getContext(), element);
        }
        if (element.tagName().equals("input")) {
            String inputType = element.attr("type");
            if (inputType.equals("text") || inputType.equals("number")) {
                return new TextInputElement(getContext(), element);
            }
            if (inputType.equals("file")) {
                return new FileInputElement(getContext(), element);
            }
            if (inputType.equals("button")) {
                return new FileDownloadElement(getContext(), element);
            }
            if (inputType.equals("checkbox")) {
                return new CheckboxInputElement(getContext(), element);
            }
            if (inputType.equals("radio")) {
                return new RadioInputElement(getContext(), element);
            }
            if (inputType.equals("password")) {
                return new PasswordInputElement(getContext(), element);
            }
        }
        if (element.tagName().equals("textarea")) {
            return new TextAreaElement(getContext(), element);
        }
        if (element.tagName().equals("select")) {
            return new SelectElement(getContext(), element);
        }
        if (element.tagName().equals("table")) {
            return new TableElement(getContext(), element);
        }
        if (element.tagName().equals("button")) {
            return new ButtonElement(getContext(), element);
        }
        if (element.tagName().equals("img")) {
            return new ImageElement(getContext(), element);
        }
        if (element.tagName().equals("a")) {
            return new LinkElement(getContext(), element);
        }
        if (StringUtils.isNotBlank(element.attr(AbstractHtmlLayoutStrategy.ATTR_WICKET_ID))) {
            return new UnknownDomainElement(getContext(), element);
        } else {
            return null;
        }
    }
}
