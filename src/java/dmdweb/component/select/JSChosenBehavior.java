package dmdweb.component.select;

import java.text.MessageFormat;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.PackageResourceReference;

import dmdweb.component.header.jquery.JQueryDependingReference;
import dmdweb.component.header.jquery.JQueryHelper;

/**
 * Behaivor adding c apabilities of Java Script Library Chosen to standard
 * Wicket components.
 * 
 * @author blaz02
 * 
 */
public class JSChosenBehavior extends Behavior {

    private static final long serialVersionUID = 1L;

    public final static String DATA_PLACEHOLDER_ATTR = "data-placeholder";
    public final static String DATA_NORESULT_ATTR = "data-no_results_text";

    public final static String DATA_PLACEHOLDER = "data.placeholder";
    public final static String DATA_NORESULT = "data.noresult";

    public final static String INIT_SCRIPT = "$(\"#{0}_chosen\").remove(); $(\"#{1}\").chosen({2})";

    /** configuration of the chosen widget */
    private String chosenConfig = "";

    /**
     * @param chosenConfig
     *            configuration of the chosen widget
     */
    public JSChosenBehavior(String chosenConfig) {
        super();
        this.chosenConfig = chosenConfig;
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        tag.append("class", "chosen-select", " ");
        tag.append(DATA_PLACEHOLDER_ATTR, new ResourceModel(JSChosenBehavior.DATA_PLACEHOLDER).wrapOnAssignment(
                component).getObject(), " ");
        tag.append(DATA_NORESULT_ATTR, new ResourceModel(JSChosenBehavior.DATA_NORESULT).wrapOnAssignment(component)
                .getObject(), " ");
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        JQueryHelper.initJQuery(response);
        response.render(JavaScriptHeaderItem.forReference(new JQueryDependingReference(JSChosenBehavior.class,
                "chosen.jquery.js")));
        response.render(CssReferenceHeaderItem.forReference(new PackageResourceReference(component.getClass(),
                "chosen.css")));
        response.render(OnDomReadyHeaderItem.forScript(getInitScript(component.getMarkupId())));
    }

    private String getInitScript(String id) {
        // If html id contains dots in the name, we have to escape them 
        // in two different ways to build proper JQuery selector.
        String chosenId = id.replace(".", "_");
        String originalId = id.replace(".", "\\\\.");

        return MessageFormat.format(INIT_SCRIPT, chosenId, originalId, chosenConfig);
    }
}
