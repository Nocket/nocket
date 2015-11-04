/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.nocket.gen.domain.visitor.html.styling.common.TabbedPanelBuilderI;
import org.nocket.gen.page.element.GroupTabbedPanelElement;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedGroupTabbedPanel;

/**
 * Bootstrap 2 Builder für Ajax Tabs. Implementierung von Nocket wird genutzt, die vor Umstellung auf 
 * Stylingstrategien genutzt wurde
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class DefaultTabbedPanelBuilder implements TabbedPanelBuilderI<ITab> {
	
	private GeneratedGroupTabbedPanel panel = null;

	@Override
	public void initTabbedPanelBuilder(GroupTabbedPanelElement e) {
		panel = new GeneratedGroupTabbedPanel(e);
	}

	@Override
	public GeneratedGroupTabbedPanel getTabbedPanel() {
		return panel;
	}

}
