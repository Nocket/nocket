package org.nocket.gen.domain.visitor.html.styling.common;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.nocket.gen.page.element.GroupTabbedPanelElement;

/**
 * Builder Interface f�r ein Panel mit mehreren Tabs
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface TabbedPanelBuilderI<T extends ITab> {
	
	/**
	 * Initialisierung des Panels mit den �bergebenen Tabs
	 * 
	 * @param e Das GroupTabbedElement, das dargestellt werden soll
	 */
	public void initTabbedPanelBuilder(GroupTabbedPanelElement e);
	
	/**
	 * Methode gibt das fertig generierte Panel zur�ck
	 */
	public AjaxTabbedPanel<T> getTabbedPanel();

}
