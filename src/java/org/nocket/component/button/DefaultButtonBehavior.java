package org.nocket.component.button;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.nocket.component.header.jquery.JQueryHelper;

public class DefaultButtonBehavior extends Behavior {

    private static final long serialVersionUID = 1L;

    private static final String JS_SCRIPT = "defaultButton.js";

    private static final String INIT_SCRIPT = "registerDefaultButtonHandler();";

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        JQueryHelper.initJQuery(response);
        response.render(JavaScriptHeaderItem.forReference(
                new JavaScriptResourceReference(DefaultButtonBehavior.class, JS_SCRIPT)));
        response.render(OnLoadHeaderItem.forScript(INIT_SCRIPT));
        super.renderHead(component, response);
    }
}
