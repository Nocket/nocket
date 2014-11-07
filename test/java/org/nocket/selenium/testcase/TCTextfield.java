package org.nocket.selenium.testcase;

import org.junit.Before;
import org.junit.Test;
import org.nocket.selenium.BootstrapPlainTestData;
import org.nocket.selenium.SeleniumTestCase;

/** TestCase TC0001 - Textfield */
public class TCTextfield extends SeleniumTestCase {

	private String textFieldID = BootstrapPlainTestData.TXTF_ID;
	private String textFieldErrorID = BootstrapPlainTestData.TXTF_ERROR_ID;
	private String submitXpath = BootstrapPlainTestData.BUTTON_SUBMIT_XPATH;

	private final static String ERROR_NULL = "Das Feld 'Textfield' darf nicht leer sein.";
	private final static String ERROR_PATTERN = "Der Wert im Feld 'Textfield' entspricht nicht dem erforderlichen Muster '[A-Za-z]*'.";
	private final static String ERROR_LENGTH = "Der Wert im Feld 'Textfield' muss zwischen 2 und 20 liegen.";

	@Before
	public void setUp() throws Exception {
		getSite(BootstrapPlainTestData.SITE_URL);
	}

	@Test
	public void testNotNullError() {
		setFieldValue(textFieldID, BootstrapPlainTestData.NULL_VALUE);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(textFieldErrorID);
		assertErrorMessage(textFieldErrorID, ERROR_NULL);
	}

	@Test
	public void testPatternNumber() {
		setFieldValue(textFieldID, BootstrapPlainTestData.TXTF_PATTERN_NUMBER);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(textFieldErrorID);
		assertErrorMessage(textFieldErrorID, ERROR_PATTERN);
	}

	@Test
	public void testPatternMixed() {
		setFieldValue(textFieldID, BootstrapPlainTestData.TXTF_PATTERN_MIXED);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(textFieldErrorID);
		assertErrorMessage(textFieldErrorID, ERROR_PATTERN);
	}

	@Test
	public void testPatternBlank() {
		setFieldValue(textFieldID, BootstrapPlainTestData.TXTF_PATTERN_BLANK);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(textFieldErrorID);
		assertErrorMessage(textFieldErrorID, ERROR_PATTERN);
	}

	@Test
	public void testPatternBlanks() {
		setFieldValue(textFieldID, BootstrapPlainTestData.TXTF_PATTERN_BLANKS);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(textFieldErrorID);
		assertErrorMessage(textFieldErrorID, ERROR_NULL);
	}

	@Test
	public void testPatternSymbols() {
		setFieldValue(textFieldID, BootstrapPlainTestData.TXTF_PATTERN_SYMBOLS);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(textFieldErrorID);
		assertErrorMessage(textFieldErrorID, ERROR_PATTERN);
	}

	@Test
	public void testPatternUmlaute() {
		setFieldValue(textFieldID, BootstrapPlainTestData.TXTF_PATTERN_UMLAUT);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(textFieldErrorID);
		assertErrorMessage(textFieldErrorID, ERROR_PATTERN);
	}

	@Test
	public void testSizeMinError() {
		setFieldValue(textFieldID, BootstrapPlainTestData.TXTF_SIZE_MIN_FALSE);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(textFieldErrorID);
		assertErrorMessage(textFieldErrorID, ERROR_LENGTH);
	}

	@Test
	public void testSizeMin() {
		setFieldValue(textFieldID, BootstrapPlainTestData.TXTF_SIZE_MIN_TRUE);
		clickButtonByXpath(submitXpath);
		assertNoError(textFieldErrorID);
	}

	@Test
	public void testSizeMaxError() {
		setFieldValue(textFieldID, BootstrapPlainTestData.TXTF_SIZE_MAX_FALSE);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(textFieldErrorID);
		assertErrorMessage(textFieldErrorID, ERROR_LENGTH);
	}

	@Test
	public void testSizeMax() {
		setFieldValue(textFieldID, BootstrapPlainTestData.TXTF_SIZE_MAX_TRUE);
		clickButtonByXpath(submitXpath);
		assertNoError(textFieldErrorID);
	}
}
