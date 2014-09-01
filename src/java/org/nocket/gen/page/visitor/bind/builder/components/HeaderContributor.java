package org.nocket.gen.page.visitor.bind.builder.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.nocket.gen.page.element.AbstractPageHeaderElement;

public class HeaderContributor extends Behavior {
    protected List<Contribution> contributions = new ArrayList<HeaderContributor.Contribution>();

    public HeaderContributor addContribution(AbstractPageHeaderElement headerElement) {
        if (StringUtils.isEmpty(headerElement.getResource())) {
            System.err.println("Link or script element in header without reference to a file is not yet supported!");
        } else {
            contributions.add(new Contribution(headerElement));
        }
        return this;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        for (Contribution contribution : contributions) {
            if (contribution.script)
                response.render(JavaScriptHeaderItem.forUrl(contribution.resource));
            else
                response.render(CssReferenceHeaderItem.forUrl(contribution.resource));
        }
    }

    private static class Contribution implements Serializable {
        final String resource;
        final boolean script;

        public Contribution(AbstractPageHeaderElement he) {
            this.resource = he.getResource();
            this.script = he.isScript();
        }
    }

}
