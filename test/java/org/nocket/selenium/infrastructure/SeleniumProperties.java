package org.nocket.selenium.infrastructure;

import java.util.Properties;

public class SeleniumProperties extends AbstractProperties {

	private static SeleniumProperties instance = null;

	public static final String ENVIROMENT_USEPROXY = "enviroment.useproxy";
	public static final String NETWORK_PROXY_TYPE = "network.proxy.type";
	public static final String NETWORK_PROXY_HTTP = "network.proxy.http";
	public static final String NETWORK_PROXY_HTTP_PORT = "network.proxy.http_port";
	public static final String NETWORK_PROXY_SSL = "network.proxy.ssl";
	public static final String NETWORK_PROXY_SSL_PORT = "network.proxy.ssl_port";
	public static final String WEBDRIVER = "webdriver";
	public static final String WEBDRIVER_PORT = "webdriver.port";
	public static final String WEBDRIVER_URL = "webdriver.url";

	protected SeleniumProperties() {
		super();
	}

	protected synchronized static SeleniumProperties getInstance() {
		if (SeleniumProperties.instance == null) {
			SeleniumProperties.instance = new SeleniumProperties();
		}
		return SeleniumProperties.instance;
	}

	@Override
	protected String getPropertyFileName() {
		return "selenium.properties";
	}

	/**
	 * Liefert Wert aus den Properties.
	 * 
	 * @see Properties#getProperty(java.lang.String)
	 */
	public static String getProperty(String key) {
		return getInstance()._getProperty(key);
	}

	/**
	 * @see AbstractDMDProperties#_setProperty(String, String)
	 */
	public static String getProperty(String key, String defaultValue) {
		return getInstance()._getProperty(key, defaultValue);
	}

	public static void setProperty(String key, String value) {
		getInstance()._setProperty(key, value);
	}

}
