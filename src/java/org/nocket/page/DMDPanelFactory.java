package org.nocket.page;

import org.apache.wicket.markup.html.panel.Panel;

public class DMDPanelFactory {

    public static Panel getViewPanelInstance(Object view, String id) {
        return new FactoryHelper(view).getPanelInstance(id);
    }

    public static Class<Panel> getViewPanelClass(Object view) {
        return new FactoryHelper(view).getPanelClass();
    }
}
