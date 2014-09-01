package dmdweb.gen.i18n;

import gengui.guibuilder.FormBuilder;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import dmdweb.gen.domain.WebDomainProperties;
import dmdweb.gen.page.DMDWebGenPageContext;
import dmdweb.gen.page.element.AbstractPageElement;

/**
 * Factory for the creation of String models for labels. The models are
 * constructed either by gengui's or Wicket's I18N concept depending on the
 * framework's localization configuration.
 * <p>
 * TODO: Add dynamic property resolvement in gengui mode in conjunction with
 * Wicket's development mode.
 * 
 * @author less02
 */
public class I18NLabelModelFactory {

    private I18NLabelModelFactory() {
    }

    public static IModel<String> createLabelModel(AbstractPageElement<?> e) {
        WebDomainProperties configuration = e.getContext().getConfiguration();
        String defaultLabel = FormBuilder.buildLabel(e.getPropertyExpression());
        if (configuration.isLocalizationWicket()) {
            return new ResourceModel(e.getDomainElement().getWicketId(), defaultLabel);
        } else {
            Class<?> domainClass = e.getDomainElement().getAccessor().getClassRef().getDomainClass();
            String key = FormBuilder.buildPromptIdentifier(e.getDomainElement().getAccessor().getClassRef(),
                        e.getDomainElement().getMethod(), defaultLabel);
            return new I18NLabelModel(domainClass, key, defaultLabel);
        }
    }

    public static IModel<String> createLabelModel(DMDWebGenPageContext ctx, Class<?> domainClass,
            String key) {
        if (ctx.getConfiguration().isLocalizationWicket()) {
            MarkupContainer page = ctx.getPage();
            return new ResourceModel(key, key).wrapOnAssignment(page);
        } else {
            return new I18NLabelModel(domainClass, key);
        }
    }

}
