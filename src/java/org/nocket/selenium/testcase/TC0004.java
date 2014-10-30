package org.nocket.selenium.testcase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.nocket.selenium.BootstrapPlainTestData;
import org.nocket.selenium.SeleniumTestCase;

/** TestCase TC0004 - Listbox */
public class TC0004 extends SeleniumTestCase {

	private String listboxID = BootstrapPlainTestData.LB_ID;
	private String listboxErrorID = BootstrapPlainTestData.LB_ERROR_ID;
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
	public void testNotNull() {
		clickButtonByXpath(submitXpath);
		assertErrorMessage(listboxErrorID, "At least one entry must be selected!");
	}

	@Test
	public void testSelectOneElement() {
		String htmlValue = BootstrapPlainTestData.LB_VALUE_0;
		selectMultipleChoiceByValue(listboxID, htmlValue);
		clickButtonByXpath(submitXpath);
		assertNoError(listboxErrorID);
	}

	@Test
	public void testSelectTwoElements() {
		String[] htmlValue = { BootstrapPlainTestData.LB_VALUE_0, BootstrapPlainTestData.LB_VALUE_1 };
		selectMultipleChoiceByValue(listboxID, htmlValue);
		clickButtonByXpath(submitXpath);
		assertNoError(listboxErrorID);
	}

	@Test
	public void testSelectAllElements() {
		String[] htmlVisibleText = getAllMultiplechoiceOptions(listboxID);
		selectMultipleChoiceByVisibleText(listboxID, htmlVisibleText);
		clickButtonByXpath(submitXpath);
		assertNoError(listboxErrorID);
	}
}
