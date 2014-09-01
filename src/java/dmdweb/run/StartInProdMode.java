package dmdweb.run;

import org.apache.wicket.RuntimeConfigurationType;


/**
 * Anwendung im Produktions oder auch Deployment Modus in einem Embedded Jetty laufen lassen.
 */
public class StartInProdMode {
	public static void main(String[] args) throws Exception {
		System.setProperty("wicket.configuration", RuntimeConfigurationType.DEPLOYMENT.toString());
		AbstractStartEmbeddedJetty jetty = new StartEmbeddedJetty();
		jetty.start();
	}
}
