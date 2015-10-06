/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.nocket.component.panel.DMDFeedbackPanel;
import org.nocket.gen.domain.visitor.html.styling.common.FeedbackPanelBuilderI;

/**
 * Builder, der einfach die alte Implementierung von Nocket weiter nutzt wie bevor
 * Nocket umgestellt wurde auf Stylingstrategien
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class Bootstrap2FeedbackPanelBuilder implements FeedbackPanelBuilderI {

	private DMDFeedbackPanel panel = null;
	
	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.FeedbackPanelBuilderI#initFeedbackPanelBuilder(java.lang.String)
	 */
	@Override
	public void initFeedbackPanelBuilder(String wicketId) {
		panel = new DMDFeedbackPanel(wicketId);
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.FeedbackPanelBuilderI#getFeedbackPanel()
	 */
	@Override
	public FeedbackPanel getFeedbackPanel() {
		return panel;
	}

}
