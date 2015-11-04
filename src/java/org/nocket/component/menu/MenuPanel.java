package org.nocket.component.menu;

import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;
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
        menuBuilder.initMenuBuilder("realMainMenu", list);
        add(menuBuilder.getMenu());
    }
    

}
