/*
 * WebDriverFactory.java
 *
 * Created on 06.03.2013
 *
 * Copyright (C) 2013 Volkswagen AG, All rights reserved.
 */

package org.nocket.selenium.infrastructure;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gargoylesoftware.htmlunit.BrowserVersion;

// TODO: Auto-generated Javadoc
/**
 * Factory to instantiate a WebDriver object. It returns an instance of the
 * driver (local invocation) or an instance of RemoteWebDriver
 * 
 * @author Sebastiano Armeli-Battana
 */
public final class WebDriverFactory {

	public static final int DEFAULT_WAIT_TIME = 5;

	public static final String baseDriverPath = "seleniumDrivers";

	/** The Constant CHROME. */
	public static final String CHROME = "chrome";

	/** The Constant FIREFOX. */
	public static final String FIREFOX = "firefox";

	/** The Constant INTERNET_EXPLORER. */
	public static final String INTERNET_EXPLORER = "ie";

	/** The Constant PHANTOM_JS. */
	public static final String PHANTOM_JS = "phantomjs";

	/** The Constant HTML_UNIT. */
	public static final String HTML_UNIT = "htmlunit";

	/** The Constant WINDOWS. */
	public static final String WINDOWS = "windows";

	/** The Constant XP. */
	public static final String XP = "xp";

	/** The Constant VISTA. */
	public static final String VISTA = "vista";

	/** The Constant MAC. */
	public static final String MAC = "mac";

	/** The Constant LINUX. */
	public static final String LINUX = "linux";

	private static File tempDir = new File(System.getProperty("java.io.tmpdir"));;
	private static File downloadDir = new File(tempDir, "seleniumDownloads");

	/**
	 * Instantiates a new web driver factory.
	 */
	private WebDriverFactory() {
	}

	/**
	 * Factory method to return a WebDriver instance given the browser to hit.
	 * 
	 * @param browser
	 *            String representing the local browser to hit
	 * @param username
	 *            username for BASIC authentication on the page to test
	 * @param password
	 *            password for BASIC authentication on the page to test
	 * @param start64bitVersion
	 *            if available start a 64bit version of the browser
	 * 
	 * @return WebDriver instance
	 */
	public static WebDriver getInstance(final String browser, final String username, final String password,
			final boolean start64bitVersion) {

		WebDriver webDriver = null;
		downloadDir.mkdirs();

		if (CHROME.equals(browser)) {
			setChromeDriver();

			webDriver = new ChromeDriver();
		} else if (FIREFOX.equals(browser)) {
			FirefoxProfile ffProfile = getFirefoxProfile(username, password);

			webDriver = new FirefoxDriver(ffProfile);
		} else if (INTERNET_EXPLORER.equals(browser)) {
			setIEDriver(start64bitVersion);

			webDriver = new InternetExplorerDriver();

		} else if (PHANTOM_JS.equals(browser)) {
			setPhantomJSDriver();

			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("takesScreenshot", false);

			webDriver = new PhantomJSDriver(caps);
			webDriver.manage().window().setSize(new Dimension(800, 600));
		} else {
			if (username != null && password != null) {
				webDriver = AuthenticatedHtmlUnitDriver.create(BrowserVersion.INTERNET_EXPLORER_9, username, password);
			} else {
				HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_9);
				htmlUnitDriver.setJavascriptEnabled(true);
				webDriver = htmlUnitDriver;
			}
		}

		// Timeouts definieren, damit man nicht zu lange warten muss
		webDriver.manage().timeouts().pageLoadTimeout(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);
		webDriver.manage().timeouts().setScriptTimeout(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);
		webDriver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);

		return webDriver;
	}

	/**
	 * Helper method to set ChromeDriver location into the right ststem
	 * property.
	 */
	private static void setChromeDriver() {
		String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
		String ext;

		if (os.equals("win")) {
			ext = ".exe";
		} else if (os.equals("mac")) {
			ext = ".osx";
		} else {
			ext = ".lnx";
		}

		String chromeBinary = baseDriverPath + "/chromedriver" + ext;
		System.setProperty("webdriver.chrome.driver", chromeBinary);
	}

	/**
	 * Helper method to set IEDriver location
	 * 
	 * @param start64bit
	 */
	private static void setIEDriver(boolean start64bit) {
		if (start64bit) {
			System.setProperty("webdriver.ie.driver", baseDriverPath + "/IEDriverServer32.exe");
		} else {
			System.setProperty("webdriver.ie.driver", baseDriverPath + "/IEDriverServer64.exe");
		}
	}

	private static FirefoxProfile getFirefoxProfile(final String username, final String password) {
		FirefoxProfile ffProfile = new FirefoxProfile();

		// Authenication Hack for Firefox
		if (username != null && password != null) {
			ffProfile.setPreference("network.http.phishy-userpass-length", 255);
		}

		String useProxy = SeleniumProperties.getProperty(SeleniumProperties.ENVIROMENT_USEPROXY);
		if (useProxy != null && useProxy.equals("true")) {
			int proxyType = new Integer(SeleniumProperties.getProperty(SeleniumProperties.NETWORK_PROXY_TYPE))
					.intValue();
			String proxyHttp = SeleniumProperties.getProperty(SeleniumProperties.NETWORK_PROXY_HTTP);
			int proxyPort = new Integer(SeleniumProperties.getProperty(SeleniumProperties.NETWORK_PROXY_HTTP_PORT))
					.intValue();
			String ssl = SeleniumProperties.getProperty(SeleniumProperties.NETWORK_PROXY_SSL);
			int sslPort = new Integer(SeleniumProperties.getProperty(SeleniumProperties.NETWORK_PROXY_SSL_PORT))
					.intValue();

			ffProfile.setPreference("network.proxy.type", proxyType);
			ffProfile.setPreference("network.proxy.http", proxyHttp);
			ffProfile.setPreference("network.proxy.http_port", proxyPort);
			ffProfile.setPreference("network.proxy.ssl", ssl);
			ffProfile.setPreference("network.proxy.ssl_port", sslPort);

		}

		ffProfile.setPreference("browser.download.folderList", 2);
		ffProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain");
		ffProfile.setPreference("browser.download.dir", downloadDir.getAbsolutePath());

		try {
			ffProfile.addExtension(new File(baseDriverPath + "/firebug-2.0.4.xpi"));
		} catch (IOException e) {
			// Add-On geht nicht, das ist aber doof .. trotzdem nicht schlimm
			System.err.println("Firebug-Plugin konnte nicht geladen werden. Mache weiter ...");
		}

		return ffProfile;
	}

	private static void setPhantomJSDriver() {
		// DesiredCapabilities caps = new DesiredCapabilities();
		// TODO mannl01: local-storage-path soll angeblich das
		// download-verzeichnis sein, wird aber ignoriert
		// Datei landet irgendwo im Temp-Ordner in einem zufallserzeugtem
		// Unterordner, Prüfung deshalb im TC nicht möglich
		// caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new
		// String[] { "--ignore-ssl-errors=yes", "--local-storage-path=" +
		// downloadDir.getAbsolutePath() } );

		String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
		String ext;

		if (os.equals("win")) {
			ext = ".exe";
		} else if (os.equals("mac")) {
			ext = ".osx";
		} else {
			// liefert zwar nur die Architektur der Java-Version, nicht des BS,
			// aber hoffen wir mal, dass diese übereinstimmen
			String arch = System.getProperty("os.arch");

			// liefert angeblich die korrekte Architektur des OS
			// funktioniert nicht auf Jenkins, da alle Werte null sind
			// String arch = System.getenv("PROCESSOR_ARCHITECTURE");
			// String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");
			// boolean arch64 = arch.endsWith("64") || wow64Arch != null &&
			// wow64Arch.endsWith("64");

			if (arch.endsWith("64")) {
				ext = "64.lnx";
			} else {
				ext = "32.lnx";
			}
		}

		String phantomJSBinary = baseDriverPath + "/phantomjs" + ext;
		System.setProperty("phantomjs.binary.path", phantomJSBinary);
	}

	public static File getTempDir() {
		return tempDir;
	}

	public static File getDownloadDir() {
		return downloadDir;
	}

}
