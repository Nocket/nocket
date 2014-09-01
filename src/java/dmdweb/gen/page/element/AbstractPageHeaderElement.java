package dmdweb.gen.page.element;

import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;

import dmdweb.gen.page.DMDWebGenPageContext;

public abstract class AbstractPageHeaderElement extends AbstractNoDomainPageElement {

    public AbstractPageHeaderElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public IModel getModel() {
        return null;
    }

    abstract public String getResource();

    abstract public boolean isScript();

    protected boolean irrelevant() {
        //TODO JL: This is too pragmatic and unsafe
        // The element is irrelevant if it is not part of the links
        // which are generated into the header according to gengui.properties html.header.links
        return getResource().startsWith("../");
    }

}
