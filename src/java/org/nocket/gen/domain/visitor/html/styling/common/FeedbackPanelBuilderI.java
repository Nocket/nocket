package org.nocket.gen.domain.visitor.html.styling.common;

import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * Builder für die Feedback-Panels in einer Form um bestimmte Meldungen auszugeben.
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public interface FeedbackPanelBuilderI {
	
	/**
	 * Builder initialisieren unter angabe der Wicket-ID
	 * 
	 * @param wicketId Wicket-ID in der HTML Seite
	 */
	public void initFeedbackPanelBuilder(String wicketId);
	
	/**
	 * Fertig erstelltes Feedback-Panel erhalten
	 */
	public FeedbackPanel getFeedbackPanel();

}
