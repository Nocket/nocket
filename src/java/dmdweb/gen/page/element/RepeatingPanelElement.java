package dmdweb.gen.page.element;

import gengui.domain.DomainObjectReference;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;

import dmdweb.gen.domain.element.MultivalueButtonElement;
import dmdweb.gen.domain.element.MultivaluePropertyElement;
import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.element.synchronizer.GeneratedRepeatingPanelModel;
import dmdweb.gen.page.element.synchronizer.TableButtonCallback;
import dmdweb.gen.page.visitor.PageElementVisitorI;

public class RepeatingPanelElement extends AbstractDomainPageElement<List<?>> {

    private ArrayList<TableButtonCallback> buttonCallbacks;

    public RepeatingPanelElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
        this.buttonCallbacks = new ArrayList<TableButtonCallback>();
        for (MultivalueButtonElement<DomainObjectReference> buttonElement : getDomainElement().getButtonElements()) {
            buttonCallbacks.add(new TableButtonCallback(getContext(), buttonElement));
        }
    }

    @Override
    public MultivaluePropertyElement<DomainObjectReference> getDomainElement() {
        return (MultivaluePropertyElement<DomainObjectReference>) super.getDomainElement();
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitRepeatingPanel(this);
    }

    @Override
    public IModel<List<?>> innerGetModel() {
        return new GeneratedRepeatingPanelModel(this);
    }

}
