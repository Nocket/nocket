package org.nocket.gen.domain.visitor.html.styling.common;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.nocket.gen.page.element.GroupTabbedPanelElement;

/**
 * Builder Interface für ein Panel mit mehreren Tabs
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface TabbedPanelBuilderI<T extends ITab> {
	
	/**
	 * Initialisierung des Panels mit den übergebenen Tabs
	 * 
	 * @param e Das GroupTabbedElement, das dargestellt werden soll
	 */
	public void initTabbedPanelBuilder(GroupTabbedPanelElement e);
	
	/**
	 * Methode gibt das fertig generierte Panel zurück
	 */
	public AjaxTabbedPanel<T> getTabbedPanel();

}
