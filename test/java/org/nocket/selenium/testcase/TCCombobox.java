package org.nocket.selenium.testcase;

import org.junit.Before;
import org.junit.Test;
import org.nocket.selenium.BootstrapPlainTestData;
import org.nocket.selenium.SeleniumTestCase;

/** TestCase TC0003 - Combobox */
public class TCCombobox extends SeleniumTestCase {

	private String comboboxID = BootstrapPlainTestData.COB_ID;
	private String comboboxErrorID = BootstrapPlainTestData.COB_ERROR_ID;
	private String submitXpath = BootstrapPlainTestData.BUTTON_SUBMIT_XPATH;

	@Before
	public void setUp() throws Exception {
		getWindow(BootstrapPlainTestData.SITE_URL);
	}

	@Test
	public void testNotSelected() {
		selectComboboxValue(comboboxID, BootstrapPlainTestData.NULL_VALUE);
		clickButtonByXpath(submitXpath);
		assertErrorMessageNotNull(comboboxErrorID);
		assertErrorMessage(comboboxErrorID, "Bitte tragen Sie einen Wert im Feld 'Combobox' ein.");
	}

	@Test
	public void testSelected() {
		selectComboboxValue(comboboxID, BootstrapPlainTestData.COB_MILLISEC);
		clickButtonByXpath(submitXpath);
		assertNoError(comboboxErrorID);
	}
}
