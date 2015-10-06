package org.nocket.component.menu;

import java.util.List;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.nocket.gen.domain.visitor.html.styling.StylingFactory;
import org.nocket.gen.domain.visitor.html.styling.common.WebMenuBuilderI;

/**
 * A MenuPanel component which displays the provided List of MenuItems in a
 * navigation
 * 
 * @author vocke03
 * 
 */
@SuppressWarnings({ "serial" })
public class MenuPanel extends Panel {

    public MenuPanel(String id, List<MenuItem> list) {
        super(id);
        WebMenuBuilderI menuBuilder = StylingFactory.getStylingStrategy().getWebMenuBuilder();
        menuBuilder.initMenuBuilder("menuList", list);
        add(menuBuilder.getMenu());
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptHeaderItem
                .forReference(new PackageResourceReference(MenuPanel.class, "MenuPanel.js")));
        response.render(CssHeaderItem.forReference(new PackageResourceReference(MenuPanel.class, "MenuPanel.css")));
    }

    

}
