package org.nocket.selenium.testcase;

import org.junit.Before;
import org.junit.Test;
import org.nocket.selenium.BootstrapPlainTestData;
import org.nocket.selenium.SeleniumTestCase;

/** TestCase TC0002 - Numberfield */
public class TCNumberfield extends SeleniumTestCase {

	private String numberfieldID = BootstrapPlainTestData.NF_ID;
	private String numberfieldErrorID = BootstrapPlainTestData.NF_ERROR_ID;
	private String submitXpath = BootstrapPlainTestData.BUTTON_SUBMIT_XPATH;

	private final static String ERROR_NULL = "Das Feld 'Numberfield' darf nicht leer sein.";
	private final static String ERROR_VALUE_MAX = "Der Wert im Feld 'Numberfield' muss kleiner sein als '75'.";
	private final static String ERROR_VALUE_MIN = "Der Wert im Feld 'Numberfield' muss größer sein als '18'.";

	@Before
	public void setUp() throws Exception {
		getSite(BootstrapPlainTestData.SITE_URL);
	}

	@Test
	public void testNotNullError() {
		setFieldValue(numberfieldID, BootstrapPlainTestData.NULL_VALUE);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(numberfieldErrorID);
		assertErrorMessage(numberfieldErrorID, ERROR_NULL);
	}

	@Test
	public void testSizeMinError() {
		setFieldValue(numberfieldID, BootstrapPlainTestData.NF_VALUE_MIN_FALSE);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(numberfieldErrorID);
		assertErrorMessage(numberfieldErrorID, ERROR_VALUE_MIN);
	}

	@Test
	public void testSizeMin() {
		setFieldValue(numberfieldID, BootstrapPlainTestData.NF_VALUE_MIN_TRUE);
		clickButtonByXpath(submitXpath);
		assertNoError(numberfieldErrorID);
	}

	@Test
	public void testSizeMaxError() {
		setFieldValue(numberfieldID, BootstrapPlainTestData.NF_VALUE_MAX_FALSE);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(numberfieldErrorID);
		assertErrorMessage(numberfieldErrorID, ERROR_VALUE_MAX);
	}

	@Test
	public void testSizeMax() {
		setFieldValue(numberfieldID, BootstrapPlainTestData.NF_VALUE_MAX_TRUE);
		clickButtonByXpath(submitXpath);
		assertNoError(numberfieldErrorID);
	}

	@Test
	public void testNegatives() {
		setFieldValue(numberfieldID, BootstrapPlainTestData.NF_PATTERN_NEGATIVE);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(numberfieldErrorID);
		assertErrorMessage(numberfieldErrorID, ERROR_VALUE_MIN);
	}

	@Test
	public void testPlus() {
		setFieldValue(numberfieldID, BootstrapPlainTestData.NF_PATTERN_PLUS);
		clickButtonByXpath(submitXpath);

		assertErrorMessageNotNull(numberfieldErrorID);
		/*
		 * <p class="error"> visible, but no error message implemented. If
		 * fixed: uncomment the following line and add the expected error
		 * message -> assertErrorMessage(numberfieldErrorID, "ErrorMessage");
		 */
	}

	@Test
	public void testAlphanumeric() {
		setFieldValue(numberfieldID, BootstrapPlainTestData.NF_PATTERN_ALPHANUMERIC);
		clickButtonByXpath(submitXpath);

		assertErrorMessageNotNull(numberfieldErrorID);
		/*
		 * <p class="error"> visible, but no error message implemented. If
		 * fixed: uncomment the following line and add the expected error
		 * message -> assertErrorMessage(numberfieldErrorID, "ErrorMessage");
		 */
	}

	@Test
	public void testDecimalDot() {
		setFieldValue(numberfieldID, BootstrapPlainTestData.NF_PATTERN_DOT);
		clickButtonByXpath(submitXpath);

		assertErrorMessageNotNull(numberfieldErrorID);
		/*
		 * <p class="error"> visible, but no error message implemented. If
		 * fixed: uncomment the following line and add the expected error
		 * message -> assertErrorMessage(numberfieldErrorID, "ErrorMessage");
		 */
	}

	@Test
	public void testDecimalComma() {
		setFieldValue(numberfieldID, BootstrapPlainTestData.NF_PATTERN_COMMA);
		clickButtonByXpath(submitXpath);

		assertErrorMessageNotNull(numberfieldErrorID);
		/*
		 * <p class="error"> visible, but no error message implemented. If
		 * fixed: uncomment the following line and add the expected error
		 * message -> assertErrorMessage(numberfieldErrorID, "ErrorMessage");
		 */
	}
}
