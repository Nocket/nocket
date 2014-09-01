package org.nocket.gen.domain.visitor.html;

import gengui.domain.AbstractDomainReference;

import java.io.File;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.GroupNameFileAndClassNameStrategy;
import org.nocket.gen.domain.visitor.AbstractDomainElementVisitor;

public abstract class AbstractHtmlVisitor<E extends AbstractDomainReference> extends AbstractDomainElementVisitor<E> {

    public static final int INDENT_AMOUNT = 4;

    public AbstractHtmlVisitor(DMDWebGenContext<E> context) {
        super(context);
    }

    public File getHtmlFile() {

        String filenamePart = getContext().getFileAndClassNameStrategy().getFilenamePartAsPath();

        File htmlPanelFile = new File(getContext().getSrcDir() + File.separator + filenamePart + "Panel.html");
        if (htmlPanelFile.exists() || isPanel()) {
            return htmlPanelFile;
        } else {
            String pagePath = getContext().getSrcDir() + File.separator + filenamePart + "Page.html";
            return new File(pagePath);
        }
    }

    protected boolean isPanel() {
        return getContext().getFileAndClassNameStrategy().isPanel();
    }

    protected String toHtml(Document doc) {
        doc.outputSettings().prettyPrint(true);
        doc.outputSettings().indentAmount(INDENT_AMOUNT);
        String gen = doc.html();
        return gen;
    }

    protected Class getDomainClass() {
        return getContext().getRefFactory().getRootReference().getRef().getDomainClass();
    }

    protected void checkAndAddGroupTabbedPanel(Document document) {
        if (!(getContext().getFileAndClassNameStrategy() instanceof GroupNameFileAndClassNameStrategy)) {
            return;
        }
        GroupNameFileAndClassNameStrategy strategy = (GroupNameFileAndClassNameStrategy) getContext()
                .getFileAndClassNameStrategy();
        if (!strategy.isDomainObjectWithGroupAnnotations() || !strategy.isStrategyForMainObject()) {
            return;
        }

        if (document.getElementsByAttributeValue("wicket:id", "groupTabbedPanel").isEmpty()) {
            Elements elementsByTag = document.getElementsByTag("form");
            if (!elementsByTag.isEmpty()) {
                org.jsoup.nodes.Element element = elementsByTag.first();
                org.jsoup.nodes.Element ulElement = element.appendElement("ul");
                ulElement.attr("wicket:id", "groupTabbedPanel");
            }
        }
    }
}
