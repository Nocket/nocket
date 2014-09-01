package dmdweb.run;

import org.apache.wicket.RuntimeConfigurationType;

/**
 * Anwendung im Development Modus in einem Embedded Jetty laufen lassen.
 */
public class StartInDevMode {
	public static void main(String[] args) throws Exception {
		System.setProperty("wicket.configuration", RuntimeConfigurationType.DEVELOPMENT.toString());
		AbstractStartEmbeddedJetty jetty = new StartEmbeddedJetty();
		jetty.start();
	}
}
