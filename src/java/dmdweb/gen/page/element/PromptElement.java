package dmdweb.gen.page.element;

import gengui.domain.DomainObjectReference;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.IModel;
import org.jsoup.nodes.Element;

import dmdweb.gen.domain.element.DomainElementI;
import dmdweb.gen.i18n.I18NLabelModelFactory;
import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.visitor.PageElementVisitorI;

public class PromptElement extends AbstractNoDomainPageElement<String> {

    private static final String LABEL = ".label";

    public PromptElement(DMDWebGenPageContext context, Element element) {
        super(context, element);
    }

    @Override
    public void accept(PageElementVisitorI visitor) {
        visitor.visitPrompt(this);
    }

    @Override
    public String getPropertyExpression() {
        String expression = "";
        String[] pathElements = removeLabelSuffix(getWicketId()).split("\\.");
        for (String pathElement : pathElements) {
            expression += StringUtils.uncapitalize(pathElement) + ".";
        }
        expression = StringUtils.removeEnd(expression, ".");
        return expression;
    }

    @Override
    public DomainElementI<DomainObjectReference> getDomainElement() {
        String wicketId = removeLabelSuffix(getWicketId());
        if (wicketId != null) {
            return getContext().getDomainRegistry().getElement(wicketId);
        } else {
            return null;
        }
    }

    @Override
    public IModel<String> getModel() {
        return I18NLabelModelFactory.createLabelModel(this);
    }

    protected String removeLabelSuffix(String wicketId) {
        return StringUtils.removeEnd(wicketId, LABEL);
    }

}
