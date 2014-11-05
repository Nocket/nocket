package org.nocket.selenium.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import de.bertelsmann.coins.general.error.AssertionException;

/**
 * Abstrakte Oberklasse alle Util-Klassen um Property-Dateien zu lesen und zu
 * cachen.
 * <P>
 * Eine Instanz soll mit einer Property-Datei verkn�pft sein.
 * 
 * @author <a href="mailto:holze01@bertelsmann.de">holze01 </a>
 * @version $Revision: 3 $ - $Modtime: 10.08.05 12:13 $ ($Author: Ggdcc01 $)
 */
public abstract class AbstractProperties {

	/** Cache für die Properties (d.h. für den Inhalt einer Property-Datei). */
	protected Properties fileProps = null;

	/**
	 * Contructor ist private, damit nur über static-Methoden zugegriffen wird.
	 */
	protected AbstractProperties() {
		initFileProps();
	}

	protected void initFileProps() {
		if (getPropertyFileName() != null) {
			readFileProps();
		}
	}

	/** Liefert den Namen der zugeh�rigen Property-Datei. */
	protected abstract String getPropertyFileName();

	/** L�scht die gesetzten Properties. */
	protected synchronized void deleteProps() {
		fileProps = new Properties();
	}

	/** Liest die (ggf. neu) Properties ein. */
	protected synchronized void readFileProps() {
		Properties props = new Properties();
		ClassLoader cl = getClass().getClassLoader();
		if (cl == null) {
			cl = ClassLoader.getSystemClassLoader();
		}
		InputStream in = cl.getResourceAsStream(getPropertyFileName());
		notNull(in, "InputStream is null for file: " + getPropertyFileName());
		try {
			props.load(in);
		} catch (IOException e) {
			// Err.handler().processSevere(e, e + " caught. File was " +
			// getPropertyFileName());
		} finally {
			IOUtils.closeQuietly(in);
		}
		fileProps = props;
	}

	/**
	 * Liefert Wert aus den Properties.
	 * 
	 * @see Properties#getProperty(java.lang.String)
	 */
	protected String _getProperty(String key) {
		return fileProps.getProperty(key);
	}

	/**
	 * Liefert Wert aus den Properties.
	 * 
	 * @see Properties#getProperty(java.lang.String,java.lang.String)
	 */
	protected String _getProperty(String key, String defaultValue) {
		return fileProps.getProperty(key, defaultValue);
	}

	/**
	 * Ver�ndert oder erg�nzt einen Property-Wert.
	 * <p>
	 * Dieser Ver�nderung geschieht nur im Cache und wird nicht persistiert.
	 */
	protected void _setProperty(String key, String value) {
		fileProps.setProperty(key, value);
	}

	/**
	 * Ersetzt alle Platzhalter in einer Property mit Hilfe von MessageFormat
	 * durch die übergebenen Objekte.
	 * <p>
	 * Bsp: Der Property-Text "Hallo {0}!" wird durch übergabe des Arrays
	 * {"Welt"} als "Hallo Welt!" zur�ckgegeben. Die Zahl in den geschweiften
	 * Klammern entspricht dem Array-Index. Detaillierte Informationen zur
	 * Nutzung können im JavaDoc zu MessageFormat nachgelesen werden.
	 * 
	 * @param key
	 *            Schl�ssel zum gesuchten Property-Text
	 * @param args
	 *            Array der Objekte, die für die Platzhalter eingesetzt werden
	 *            sollen.
	 */
	protected String _getProperty(String key, Object[] args) {
		String template = _getProperty(key);
		MessageFormat format = new MessageFormat(template);
		return format.format(args);
	}

	public static void notNull(Object obj, String message) {
		test(obj != null, message);
	}

	public static void test(boolean condition, String message) throws AssertionException {
		if (!condition)
			throw new AssertionException(message);
	}
}
