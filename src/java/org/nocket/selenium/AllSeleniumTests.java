package org.nocket.selenium;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.nocket.selenium.testcase.TC0001;
import org.nocket.selenium.testcase.TC0002;
import org.nocket.selenium.testcase.TC0003;
import org.nocket.selenium.testcase.TC0004;
import org.nocket.selenium.testcase.TC0005;
import org.nocket.selenium.testcase.TC0006;

@RunWith(Suite.class)
@SuiteClasses({ TC0001.class, TC0002.class, TC0003.class, TC0004.class, TC0005.class, TC0006.class })
public class AllSeleniumTests {
	@AfterClass
	public static void afterClass() {
		SeleniumTestCase.tearDown();
	}
}
