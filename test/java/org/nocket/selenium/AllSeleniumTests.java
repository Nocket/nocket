package org.nocket.selenium;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.nocket.selenium.testcase.TCTextfield;
import org.nocket.selenium.testcase.TCNumberfield;
import org.nocket.selenium.testcase.TCCombobox;
import org.nocket.selenium.testcase.TCListbox;
import org.nocket.selenium.testcase.TCCheckbox;
import org.nocket.selenium.testcase.TCDatefield;

@RunWith(Suite.class)
@SuiteClasses({ TCTextfield.class, TCNumberfield.class, TCCombobox.class, TCListbox.class, TCCheckbox.class, TCDatefield.class })
public class AllSeleniumTests {

}
