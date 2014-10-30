package org.nocket.selenium.testcase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.nocket.selenium.BootstrapPlainTestData;
import org.nocket.selenium.SeleniumTestCase;

/** TestCase TC0001 - Textfield */
public class TC0001 extends SeleniumTestCase {

	private String textFieldID = BootstrapPlainTestData.TXTF_ID;
	private String textFieldErrorID = BootstrapPlainTestData.TXTF_ERROR_ID;
	private String submitXpath = BootstrapPlainTestData.BUTTON_SUBMIT_XPATH;

	@BeforeClass
	public static void setUpClass() {
		driver = getFirefoxWebDriverInstance();
	}

	@Before
	public void setUp() throws Exception {
		getFirefoxWindow(BootstrapPlainTestData.SITE_URL);
	}

	@Test
	public void testNotNullError() {
		setFieldValueByID(textFieldID, BootstrapPlainTestData.NULL);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(textFieldErrorID, "Bitte tragen Sie einen Wert im Feld 'Textfield' ein.");
	}

	@Test
	public void testPatternNumber() {
		setFieldValueByID(textFieldID, BootstrapPlainTestData.TXTF_PATTERN_NUMBER);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(textFieldErrorID, "Der Wert '" + BootstrapPlainTestData.TXTF_PATTERN_NUMBER
				+ "' im Feld 'Textfield' entspricht nicht dem erforderlichen Muster '[A-Za-z]*'.");
	}

	@Test
	public void testPatternMixed() {
		setFieldValueByID(textFieldID, BootstrapPlainTestData.TXTF_PATTERN_MIXED);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(textFieldErrorID, "Der Wert '" + BootstrapPlainTestData.TXTF_PATTERN_MIXED
				+ "' im Feld 'Textfield' entspricht nicht dem erforderlichen Muster '[A-Za-z]*'.");
	}

	@Test
	public void testPatternBlank() {
		setFieldValueByID(textFieldID, BootstrapPlainTestData.TXTF_PATTERN_BLANK);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(textFieldErrorID, "Der Wert '" + BootstrapPlainTestData.TXTF_PATTERN_BLANK
				+ "' im Feld 'Textfield' entspricht nicht dem erforderlichen Muster '[A-Za-z]*'.");
	}

	@Test
	public void testPatternBlanks() {
		setFieldValueByID(textFieldID, BootstrapPlainTestData.TXTF_PATTERN_BLANKS);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(textFieldErrorID, "Bitte tragen Sie einen Wert im Feld 'Textfield' ein.");
	}

	@Test
	public void testPatternSymbols() {
		setFieldValueByID(textFieldID, BootstrapPlainTestData.TXTF_PATTERN_SYMBOLS);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(textFieldErrorID, "Der Wert '" + BootstrapPlainTestData.TXTF_PATTERN_SYMBOLS
				+ "' im Feld 'Textfield' entspricht nicht dem erforderlichen Muster '[A-Za-z]*'.");
	}

	@Test
	public void testPatternUmlaute() {
		setFieldValueByID(textFieldID, BootstrapPlainTestData.TXTF_PATTERN_UMLAUT);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(textFieldErrorID, "Der Wert '" + BootstrapPlainTestData.TXTF_PATTERN_UMLAUT
				+ "' im Feld 'Textfield' entspricht nicht dem erforderlichen Muster '[A-Za-z]*'.");
	}

	@Test
	public void testSizeMinError() {
		setFieldValueByID(textFieldID, BootstrapPlainTestData.TXTF_SIZE_MIN_FALSE);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(textFieldErrorID, "Der Wert " + BootstrapPlainTestData.TXTF_SIZE_MIN_FALSE
				+ " im Feld 'Textfield' muss zwischen 2 und 20 liegen.");
	}

	@Test
	public void testSizeMin() {
		setFieldValueByID(textFieldID, BootstrapPlainTestData.TXTF_SIZE_MIN_TRUE);
		clickButtonByXpath(submitXpath);
		assertNoError(textFieldErrorID);
	}

	@Test
	public void testSizeMaxError() {
		setFieldValueByID(textFieldID, BootstrapPlainTestData.TXTF_SIZE_MAX_FALSE);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(textFieldErrorID, "Der Wert " + BootstrapPlainTestData.TXTF_SIZE_MAX_FALSE
				+ " im Feld 'Textfield' muss zwischen 2 und 20 liegen.");
	}

	@Test
	public void testSizeMax() {
		setFieldValueByID(textFieldID, BootstrapPlainTestData.TXTF_SIZE_MAX_TRUE);
		clickButtonByXpath(submitXpath);
		assertNoError(textFieldErrorID);
	}
}
