package org.nocket.gen.page.element;

import gengui.domain.DomainObjectReference;

import java.io.File;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.jsoup.nodes.Element;
import org.nocket.gen.domain.element.SimplePropertyElement;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.visitor.PageElementVisitorI;

public class FileDownloadElement extends AbstractDomainPageElement<File> {

    public FileDownloadElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public IModel<File> innerGetModel() {
        IModel<Object> coreModel =
                new PropertyModel<Object>(getPropertyModelObject(), getPropertyExpression());
        IModel model = new RelativeFileModel(coreModel);
        return (IModel<File>) model;
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitFileDownload(this);
    }

    @Override
    public SimplePropertyElement<DomainObjectReference> getDomainElement() {
        return (SimplePropertyElement<DomainObjectReference>) super.getDomainElement();
    }

    @Override
    public boolean enableThoughUnmodifiable() {
        return true;
    }

    public static final class RelativeFileModel implements IModel<File> {
        private final IModel<Object> core;

        public RelativeFileModel(IModel<Object> core) {
            this.core = core;
        }

        @Override
        public void detach() {
            core.detach();
        }

        @Override
        public File getObject() {
            File coreValue = (File) core.getObject();
            return coreValue.isAbsolute() ? coreValue :
                    new File(WebApplication.get().getServletContext().getRealPath(".") + "/" + coreValue.getPath());
        }

        @Override
        public void setObject(File value) {
            core.setObject(value);
        }

    }

}
