/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling;

import org.nocket.gen.domain.DMDWebGenContext;
import org.nocket.gen.domain.WebDomainProperties;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.DefaultStylingStrategy;
import org.nocket.gen.domain.visitor.html.styling.common.StylingStrategyI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Diese Factory verwaltet die zu nutzende Styling-Strategie. 
 * Führend ist hier die Bootstrap2StylingStrategy. Diese wird genutzt, wenn keine
 * Stylingstrategie konfiguriert wurde. Wenn jedoch eine Styling-Strategie 
 * kongiguriert wurde, dann wird diese Strategie per Reflection initialisiert
 * und von dieser Factory zurück geliefert.
 * 
 *  Konfiguration erfolgt in der <i>gengui.properties</i> und dort unter 
 *  dem Key <i>nocket.styling.strategy</i>
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class StylingFactory {
	
	private static StylingStrategyI strategyInstance;
	final private static Logger log = LoggerFactory.getLogger(StylingFactory.class);
	
	
	/**
	 * Accessor an den Singleton der Styling-Strategie
	 */
	public static StylingStrategyI getStylingStrategy() {
		return getStrategyInstance(null);
	}
	
	public static StylingStrategyI getStylingStrategy(DMDWebGenContext<?> context) {
		return getStrategyInstance(context);
	}
	
	/**
	 * Neue Instanz der aktuellen Styling Strategie erstellen
	 * @param context Generator-Kontext, kann im Normalbetrieb auch Null sein
	 * @return Neu erstellte Instanz der Styling-Strategie
	 */
	public static StylingStrategyI newStylingStrategyInstance(DMDWebGenContext<?> context) {
		
		StylingStrategyI newInstance;
		
		try {
			// Konfiguration aus der gengui.properties lesen
			String strategyClass = new WebDomainProperties().getStylingStrategyClass();
			Class<? extends StylingStrategyI> clazz = (Class<? extends StylingStrategyI>) Class.forName(strategyClass);
			
			newInstance = clazz.newInstance();
			log.debug("Choosed styling strategy: " + strategyClass);
		} catch (Exception e) {
			// wenn ein Fehler passiert, dann die Default Strategie nehmen
			newInstance = new DefaultStylingStrategy();
			log.warn("Choosed styling strategy: Could not choose from properties, take default Bootstrap2StylingStrategy instead");
		}
		
		if(context != null) {
			newInstance.setContext(context);
		}
		
		return newInstance;
	}
	
	/**
	 * Singleton-Accessor für die Instanz der Styling-Strategie
	 */
	@SuppressWarnings("unchecked")
	private static StylingStrategyI getStrategyInstance(DMDWebGenContext<?> context) {
		
		if(strategyInstance == null) {
			// neuen Singleton initialisieren			
			strategyInstance = newStylingStrategyInstance(context);
		}
		
		return strategyInstance;
	}

}
