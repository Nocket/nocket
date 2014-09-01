package dmdweb.gen.page.element;

import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;

import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.visitor.PageElementVisitorI;

public class FeedbackElement extends AbstractNoDomainPageElement<Void> {

    public static final String DEFAULT_WICKET_ID = "feedback";

    public FeedbackElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Deprecated
    @Override
    public IModel<Void> getModel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitFeedback(this);
    }

}
