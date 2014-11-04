package org.nocket.selenium;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SeleniumWebDriver {
	private static final int TIMEOUT_SEC = 3;
	private static final String PHANTOMJS_BASEPATH = System.getProperty("user.dir") + "/phantomjs/";
	private static WebDriver driver;
	private static SeleniumWebDriver seleniumDriver = null;

	private SeleniumWebDriver(String browser) {
		if (browser.equals("FIREFOX")) {
			setupFirefoxDriver();
		} else {
			setupPhantomJSDriver();
		}
	}

	public static WebDriver getInstanceOfWebDriver(String browser) {
		if (seleniumDriver == null) {
			seleniumDriver = new SeleniumWebDriver(browser);
		}
		return driver;
	}

	private void setupFirefoxDriver() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(TIMEOUT_SEC, TimeUnit.SECONDS);
	}

	private void setupPhantomJSDriver() {
		String executePath = getPhantomjsExecutablePath();
		System.setProperty("phantomjs.binary.path", PHANTOMJS_BASEPATH + executePath);
		DesiredCapabilities dCaps = new DesiredCapabilities();
		dCaps.setJavascriptEnabled(true);
		dCaps.setCapability("takesScreenshot", false);

		driver = new PhantomJSDriver(dCaps);
		driver.manage().timeouts().implicitlyWait(TIMEOUT_SEC, TimeUnit.SECONDS);
	}

	private String getPhantomjsExecutablePath() {
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		String path = "";

		if (os.equals("Mac OS X") || os.equals("Mac OS")) {
			path = "phantomjs-1.9.8-macosx/bin/phantomjs";
		} else {
			path = "phantomjs-1.9.8-windows/phantomjs.exe";
		}
		return path;
	}
}
