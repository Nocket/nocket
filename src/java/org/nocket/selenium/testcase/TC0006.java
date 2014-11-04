package org.nocket.selenium.testcase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.nocket.selenium.BootstrapPlainTestData;
import org.nocket.selenium.SeleniumTestCase;

/** TestCase TC0005 - Datefield */
public class TC0006 extends SeleniumTestCase {

	private String datefieldID = BootstrapPlainTestData.DF_ID;
	private String datefieldErrorID = BootstrapPlainTestData.DF_ERROR_ID;
	private String submitXpath = BootstrapPlainTestData.BUTTON_SUBMIT_XPATH;

	@BeforeClass
	public static void setUpClass() {
		driver = getWebDriverInstance(BootstrapPlainTestData.TESTBROWSER.toString());
	}

	@Before
	public void setUp() throws Exception {
		getWindow(BootstrapPlainTestData.SITE_URL);
	}

	@Test
	public void testNotNull() {
		setFieldValueByID(datefieldID, BootstrapPlainTestData.NULL_VALUE);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(datefieldErrorID);
		assertErrorMessage(datefieldErrorID, "Bitte tragen Sie einen Wert im Feld 'Datefield' ein.");
	}

	@Test
	public void testFullDate() {
		setFieldValueByID(datefieldID, BootstrapPlainTestData.DF_FULLDATE);
		clickButtonByXpath(submitXpath);
		assertNoError(datefieldErrorID);
	}

	@Test
	public void testPast() {
		setFieldValueByID(datefieldID, BootstrapPlainTestData.DF_PAST);
		clickButtonByXpath(submitXpath);
		assertNoError(datefieldErrorID);
	}

	@Test
	public void testShortDate() {
		setFieldValueByID(datefieldID, BootstrapPlainTestData.DF_SHORTDATE);
		clickButtonByXpath(submitXpath);
		assertNoError(datefieldErrorID);
	}

	@Test
	public void testFormat() {
		setFieldValueByID(datefieldID, BootstrapPlainTestData.DF_FORMAT);
		clickButtonByXpath(submitXpath);
		assertNoError(datefieldErrorID);
	}

	@Test
	public void testOutOfRange() {
		setFieldValueByID(datefieldID, BootstrapPlainTestData.DF_RANGE);
		clickButtonByXpath(submitXpath);
		assertNoError(datefieldErrorID);
	}

	@Test
	public void testNumber() {
		setFieldValueByID(datefieldID, BootstrapPlainTestData.DF_NUMBER);
		clickButtonByXpath(submitXpath);

		assertErrorMessageNotNull(datefieldErrorID);
		/*
		 * <p class="error"> visible, but no error message implemented. If
		 * fixed: uncomment the following line and add the expected error
		 * message -> assertErrorMessage(datefieldErrorID, "ErrorMessage");
		 */
	}

	@Test
	public void testAlphanumeric() {
		setFieldValueByID(datefieldID, BootstrapPlainTestData.DF_ALPHANUMERIC);
		clickButtonByXpath(submitXpath);

		assertErrorMessageNotNull(datefieldErrorID);
		/*
		 * <p class="error"> visible, but no error message implemented. If
		 * fixed: uncomment the following line and add the expected error
		 * message -> assertErrorMessage(datefieldErrorID, "ErrorMessage");
		 */
	}
}
