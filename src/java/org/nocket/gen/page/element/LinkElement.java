package org.nocket.gen.page.element;

import gengui.domain.DomainObjectReference;

import java.io.File;
import java.io.InputStream;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.jsoup.nodes.Element;
import org.nocket.gen.domain.element.ResourceElement;
import org.nocket.gen.domain.element.SimplePropertyElement;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.synchronizer.FileToStringModelWrapper;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class LinkElement extends AbstractDomainPageElement<Object> {

    public LinkElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public IModel getModel() {
        if (this.isResourceLink() && model == null) {
            model = innerGetModel();
            return model;
        } else {
            return super.getModel();
        }
    }

    @Override
    public IModel innerGetModel() {
        if (isResourceLink()) {
            return new PropertyModel<InputStream>(getPropertyModelObject(), getPropertyExpression());
        } else if (isFileType()) {
            IModel<File> coreModel = new PropertyModel<File>(getPropertyModelObject(), getPropertyExpression());
            return new FileToStringModelWrapper(coreModel);
        } else {
            return new PropertyModel<String>(getPropertyModelObject(), getPropertyExpression());
        }
    }

    protected boolean isFileType() {
        if (this.getDomainElement() instanceof SimplePropertyElement) {
            SimplePropertyElement<DomainObjectReference> simplePropertyElement = (SimplePropertyElement<DomainObjectReference>) this
                    .getDomainElement();
            return simplePropertyElement.isFileType();
        }
        return false;
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitLink(this);
    }

    @Override
    public boolean enableThoughUnmodifiable() {
        return true;
    }

    public boolean isResourceLink() {
        return super.getDomainElement() instanceof ResourceElement;
    }
}
