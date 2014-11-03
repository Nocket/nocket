package org.nocket.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public abstract class SeleniumTestCase {

	private static final int DEFAULT_PORT = 8070;
	private static final int TIMEOUT_SEC = 3;
	private static final String BASE_URL = "http://localhost:";
	private static StringBuffer verificationErrors = new StringBuffer();
	private static DesiredCapabilities dCaps;
	protected static WebDriver driver;

	public static WebDriver getWebDriverInstance(String browser) {
		if (driver == null) {
			if (browser.equals("FIREFOX")) {
				setupFirefoxDriver();
			} else {
				setupPhantomJSDriver();
			}
		}
		return driver;
	}

	private static void setupPhantomJSDriver() {
		dCaps = new DesiredCapabilities();
		dCaps.setJavascriptEnabled(true);
		dCaps.setCapability("takesScreenshot", false);

		driver = new PhantomJSDriver(dCaps);
		driver.manage().timeouts().implicitlyWait(TIMEOUT_SEC, TimeUnit.SECONDS);
	}

	private static void setupFirefoxDriver() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(TIMEOUT_SEC, TimeUnit.SECONDS);
	}

	protected static void getWindow(String siteUrl) {
		driver.get(BASE_URL + DEFAULT_PORT + siteUrl);
	}

	protected void setFieldValueByID(String fieldID, String value) {
		driver.findElement(By.id(fieldID)).clear();
		driver.findElement(By.id(fieldID)).sendKeys(value);
	}

	protected void selectComboboxValue(String comboboxID, String value) {
		Select combobox = new Select(driver.findElement(By.id(comboboxID)));
		combobox.selectByValue(value);
	}

	protected void selectMultipleChoiceByValue(String multiplechoiceID, String... value) {
		Select multiplechoice = new Select(driver.findElement(By.id(multiplechoiceID)));
		for (int i = 0; i < value.length; i++) {
			multiplechoice.selectByValue(value[i]);
		}
	}

	protected void selectMultipleChoiceByVisibleText(String multiplechoiceID, String... visibleText) {
		Select multiplechoice = new Select(driver.findElement(By.id(multiplechoiceID)));
		for (int i = 0; i < visibleText.length; i++) {
			multiplechoice.selectByVisibleText(visibleText[i]);
		}
	}

	protected String[] getAllMultiplechoiceOptions(String multiplechoiceID) {
		Select multiplechoice = new Select(driver.findElement(By.id(multiplechoiceID)));
		List<WebElement> optionElements = multiplechoice.getOptions();
		String[] optionText = new String[optionElements.size()];

		for (int i = 0; i < optionText.length; i++) {
			optionText[i] = optionElements.get(i).getText();
		}
		return optionText;
	}

	protected void selectCheckbox(String checkboxID) {
		if (!driver.findElement(By.id(checkboxID)).isSelected()) {
			driver.findElement(By.id(checkboxID)).click();
		}
	}

	protected void deselectCheckbox(String checkboxID) {
		if (driver.findElement(By.id(checkboxID)).isSelected()) {
			driver.findElement(By.id(checkboxID)).click();
		}
	}

	protected void clickButtonByXpath(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}

	protected void assertErrorMessage(String errorID, String errorMessage) {
		assertEquals(errorMessage, driver.findElement(By.id(errorID)).getText());
	}

	protected void assertNoError(String errorID) {
		assertTrue(!isElementPresent(By.id(errorID)));
	}

	protected void assertErrorMessageNotNull(String errorID) {
		if (isElementPresent(By.id(errorID))) {
			assertTrue("Error visible, but no error message implemented!", isErrorNotNull(errorID));
		}
	}

	private boolean isErrorNotNull(String errorID) {
		if (driver.findElement(By.id(errorID)).getText().equals("")) {
			return false;
		}
		return true;
	}

	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	protected static void tearDown() {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
