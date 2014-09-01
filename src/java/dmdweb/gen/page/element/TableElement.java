package dmdweb.gen.page.element;

import gengui.domain.DomainObjectReference;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;

import dmdweb.gen.domain.element.MultivalueButtonElement;
import dmdweb.gen.domain.element.MultivalueColumnElement;
import dmdweb.gen.domain.element.MultivaluePropertyElement;
import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.element.synchronizer.GeneratedTableModel;
import dmdweb.gen.page.element.synchronizer.TableButtonCallback;
import dmdweb.gen.page.element.synchronizer.TableDownloadCallback;
import dmdweb.gen.page.visitor.PageElementVisitorI;

public class TableElement extends AbstractDomainPageElement<List<?>> {

    private List<TableButtonCallback> buttonCallbacks;
    private List<TableDownloadCallback> downloadCallbacks;

    public TableElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
        this.buttonCallbacks = new ArrayList<TableButtonCallback>();
        this.downloadCallbacks = new ArrayList<TableDownloadCallback>();
        for (MultivalueButtonElement<DomainObjectReference> buttonElement : getDomainElement().getButtonElements()) {
            buttonCallbacks.add(new TableButtonCallback(getContext(), buttonElement));
        }
        for (MultivalueColumnElement<DomainObjectReference> downloadElement : getDomainElement().getDownloadColumnElements()) {
            downloadCallbacks.add(new TableDownloadCallback(getContext(), downloadElement));
        }
    }

    @Override
    public MultivaluePropertyElement<DomainObjectReference> getDomainElement() {
        return (MultivaluePropertyElement<DomainObjectReference>) super.getDomainElement();
    }

    public List<TableButtonCallback> getButtonCallbacks() {
        return buttonCallbacks;
    }

    public List<TableDownloadCallback> getDownloadCallbacks() {
        return downloadCallbacks;
    }

    @Override
    public IModel<List<?>> innerGetModel() {
        return new GeneratedTableModel(this);
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitTable(this);
    }

}
