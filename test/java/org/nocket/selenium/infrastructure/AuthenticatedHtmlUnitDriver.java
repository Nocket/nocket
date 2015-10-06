/*
 * AuthenticatedHtmlUnitDriver.java
 */

package org.nocket.selenium.infrastructure;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/*
 * Class extending HtmlUnitDriver to support BASIC authentication
 * @author Sebastiano Armeli-Battana
 */
/**
 * The Class AuthenticatedHtmlUnitDriver.
 */
public class AuthenticatedHtmlUnitDriver extends HtmlUnitDriver {

	/** The username. */
	private static String USERNAME;

	/** The password. */
	private static String PASSWORD;

	/**
	 * Instantiates a new authenticated html unit driver.
	 */
	public AuthenticatedHtmlUnitDriver() {
		super();
		setJavascriptEnabled(true);
	}

	/**
	 * Instantiates a new authenticated html unit driver.
	 *
	 * @param version
	 *            the version
	 */
	public AuthenticatedHtmlUnitDriver(final BrowserVersion version) {
		super(version);
		setJavascriptEnabled(true);
	}

	/**
	 * Instantiates a new authenticated html unit driver.
	 *
	 * @param capabilities
	 *            the capabilities
	 */
	public AuthenticatedHtmlUnitDriver(final Capabilities capabilities) {
		super(capabilities);
		setJavascriptEnabled(true);
	}

	/**
	 * Creates the.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return the web driver
	 */
	public static WebDriver create(final String username, final String password) {
		USERNAME = username;
		PASSWORD = password;
		return new AuthenticatedHtmlUnitDriver();
	}

	/**
	 * Creates the.
	 *
	 * @param version
	 *            the version
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return the web driver
	 */
	public static WebDriver create(final BrowserVersion version, final String username, final String password) {
		USERNAME = username;
		PASSWORD = password;
		return new AuthenticatedHtmlUnitDriver(version);
	}

	/**
	 * Creates the.
	 *
	 * @param capabilities
	 *            the capabilities
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return the web driver
	 */
	public static WebDriver create(final Capabilities capabilities, final String username, final String password) {
		USERNAME = username;
		PASSWORD = password;
		return new AuthenticatedHtmlUnitDriver(capabilities);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.openqa.selenium.htmlunit.HtmlUnitDriver#newWebClient(com.gargoylesoftware
	 * .htmlunit.BrowserVersion)
	 */
	@Override
	protected WebClient newWebClient(final BrowserVersion browserVersion) {
		WebClient client = super.newWebClient(browserVersion);
		DefaultCredentialsProvider provider = new DefaultCredentialsProvider();
		provider.addCredentials(USERNAME, PASSWORD);
		client.setCredentialsProvider(provider);
		return client;
	}
}
