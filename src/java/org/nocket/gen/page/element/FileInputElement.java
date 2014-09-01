package org.nocket.gen.page.element;

import gengui.domain.DomainObjectReference;

import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;
import org.nocket.gen.domain.element.SimplePropertyElement;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.synchronizer.FileUploadModel;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class FileInputElement extends AbstractDomainPageElement<Object> {

    public FileInputElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public IModel<Object> innerGetModel() {
        IModel model = new FileUploadModel(this);
        return model;
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitFileInput(this);
    }

    @Override
    public SimplePropertyElement<DomainObjectReference> getDomainElement() {
        return (SimplePropertyElement<DomainObjectReference>) super.getDomainElement();
    }

}
