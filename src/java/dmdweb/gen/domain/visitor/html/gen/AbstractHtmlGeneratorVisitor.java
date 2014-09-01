package dmdweb.gen.domain.visitor.html.gen;

import gengui.domain.AbstractDomainReference;
import gengui.util.SevereGUIException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.commons.io.FileUtils;
import org.apache.ecs.Element;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.html.Body;
import org.apache.ecs.html.Comment;
import org.apache.ecs.html.Div;
import org.apache.ecs.html.FieldSet;
import org.apache.ecs.html.Form;
import org.apache.ecs.html.Head;
import org.apache.ecs.html.Html;
import org.apache.ecs.html.Link;
import org.apache.ecs.xml.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import dmdweb.gen.domain.DMDWebGenContext;
import dmdweb.gen.domain.element.ButtonElement;
import dmdweb.gen.domain.element.CheckboxPropertyElement;
import dmdweb.gen.domain.element.ChoicerPropertyElement;
import dmdweb.gen.domain.element.HeadlineElement;
import dmdweb.gen.domain.element.HiddenPropertyElement;
import dmdweb.gen.domain.element.MultivaluePropertyElement;
import dmdweb.gen.domain.element.ResourceElement;
import dmdweb.gen.domain.element.SimplePropertyElement;
import dmdweb.gen.domain.visitor.html.AbstractHtmlComponentBuilder;
import dmdweb.gen.domain.visitor.html.AbstractHtmlVisitor;
import dmdweb.gen.page.element.ModalElement;

abstract class AbstractHtmlGeneratorVisitor<E extends AbstractDomainReference> extends AbstractHtmlVisitor<E> {

    protected final Html html;
    protected final Deque<MultiPartElement> panelStack = new ArrayDeque<MultiPartElement>();
    protected final AbstractHtmlComponentBuilder componentBuilder;

    public AbstractHtmlGeneratorVisitor(DMDWebGenContext<E> context, AbstractHtmlComponentBuilder componentBuilder) {
        super(context);
        this.componentBuilder = componentBuilder;
        this.html = newHtml();
    }

    protected Html newHtml() {
        Html html = new Html();
        html.addAttribute("xmlns:wicket", "http://wicket.apache.org/dtds.data/wicket-xhtml1.4-strict.dtd");
        Body body = new Body();
        addHeader(html);
        html.addElement(body);
        final XML wicketContainer;
        if (isPanel()) {
            wicketContainer = new XML("wicket:panel");
        } else {
            wicketContainer = new XML("wicket:extend");
        }
        body.addElement(wicketContainer);
        wicketContainer.addElement(new Comment(new Div().addAttribute(AbstractHtmlComponentBuilder.ATTR_WICKET_ID,
                "feedback")));
        wicketContainer.addElement(new Div().addAttribute(AbstractHtmlComponentBuilder.ATTR_WICKET_ID,
                ModalElement.DEFAULT_WICKET_ID));
        Form form = new Form();
        form.addAttribute(AbstractHtmlComponentBuilder.ATTR_CLASS, "form-horizontal");
        form.removeAttribute("accept-charset");
        form.removeAttribute("enctype");
        wicketContainer.addElement(form);
        form.addAttribute(AbstractHtmlComponentBuilder.ATTR_WICKET_ID, "form");
        panelStack.add(form);
        return html;
    }

    protected void addHeader(Html html) {
        //<head>
        //  <link rel="stylesheet" href="../../../../../resources/webapp/css/bootstrap.css" type="text/css" />
        // </head>
        String[] headerlinks = getContext().getHeaderlinks();
        if (headerlinks != null && headerlinks.length > 0) {
            String relativePath = constructRelativeLinkPath();
            Head head = new Head();
            for (String headerlink : headerlinks) {
                Link link = new Link();
                link.setRel("stylesheet");
                link.setType("text/css");
                link.setHref(relativePath + headerlink.trim());
                head.addElement(link);
            }
            html.addElement(head);
        }
    }

    protected String constructRelativeLinkPath() {
        int packageDepth = getDomainClass().getName().split("\\.").length - 1;
        int genPathDepth = getContext().getGenDir().getPath().split("/").length;
        String relativePath = "";
        for (int i = 0; i < packageDepth + genPathDepth; i++)
            relativePath += "../";
        return relativePath;
    }

    protected void maybeAdd(Element element) {
        if (element != null) {
            panelStack.getLast().addElementToRegistry(element);
        }
    }

    @Override
    public void visitSimpleProperty(SimplePropertyElement<E> e) {
        Element input = componentBuilder.createSimpleProperty(e);
        maybeAdd(input);
    }

    @Override
    public void visitChoicerProperty(ChoicerPropertyElement<E> e) {
        Element select = componentBuilder.createChoicerProperty(e);
        maybeAdd(select);
    }

    @Override
    public void visitCheckboxProperty(CheckboxPropertyElement<E> e) {
        Element checkbox = componentBuilder.createCheckboxProperty(e);
        maybeAdd(checkbox);
    }

    @Override
    public void visitButton(ButtonElement<E> e) {
        Element button = componentBuilder.createButton(e);
        maybeAdd(button);
    }

    @Override
    public void visitResource(ResourceElement<E> e) {
        Element resource = componentBuilder.createResource(e);
        maybeAdd(resource);
    }

    @Override
    public void visitFieldsetOpen(HeadlineElement<E> e) {
        FieldSet fieldset = componentBuilder.createFieldset(e);
        maybeAdd(fieldset);
        panelStack.add(fieldset);
    }

    @Override
    public void visitMultivalueProperty(MultivaluePropertyElement<E> e) {
        Element table = componentBuilder.createMultivalueProperty(e);
        maybeAdd(table);
    }

    @Override
    public void visitFieldsetClose() {
        if (panelStack.removeLast() == null) {
            throw new IllegalStateException("PanelStack improperly implemented, did not expect a panel to be null!");
        }
    }

    @Override
    public void visitHiddenProperty(HiddenPropertyElement<E> e) {
        // ignore
    }

    @Override
    public void finish() {
        String gen = "<!DOCTYPE HTML>" + html.toString();
        Document doc = Jsoup.parse(gen);
        checkAndAddGroupTabbedPanel(doc);
        gen = toHtml(doc);
        File file = getHtmlFile();
        try {
            FileUtils.forceMkdir(file.getParentFile());
            FileUtils.writeStringToFile(file, gen);
            writeStatics(file.getName(), true, false, null);
        } catch (IOException e) {
            writeStatics(file.getName(), false, false, e.getMessage());
            throw new SevereGUIException(e);
        }
    }
}
