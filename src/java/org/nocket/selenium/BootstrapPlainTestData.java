package org.nocket.selenium;

import java.util.concurrent.TimeUnit;

public abstract class BootstrapPlainTestData {

	public final static String NULL = "";

	/* BootstrapPlain URL */
	public final static String SITE_URL = "/nocket/wicket/bookmarkable/forscher.nocket.page.gen.layout.AllComponentsBootstrapPlainPage";

	/* Button Submit */
	public final static String BUTTON_SUBMIT_XPATH = "//button[@id='button']";

	/* Data for Textfield - TC0001 */
	public final static String TXTF_ID = "Textfield";
	public final static String TXTF_ERROR_ID = "TextfieldError";
	public final static String TXTF_PATTERN_NUMBER = "12";
	public final static String TXTF_PATTERN_MIXED = "Test12";
	public final static String TXTF_PATTERN_BLANK = "Test ABC";
	public final static String TXTF_PATTERN_BLANKS = "       ";
	public final static String TXTF_PATTERN_SYMBOLS = "Test!";
	public final static String TXTF_PATTERN_UMLAUT = "Ää";
	public final static String TXTF_SIZE_MIN_FALSE = "A";
	public final static String TXTF_SIZE_MIN_TRUE = "AB";
	public final static String TXTF_SIZE_MAX_FALSE = "ABCDEFGHIJKLMNOPQRSTU";
	public final static String TXTF_SIZE_MAX_TRUE = "ABCDEFGHIJKLMNOPQRST";

	/* Data for Numberfield - TC0002 */
	public final static String NF_ID = "Numberfield";
	public final static String NF_ERROR_ID = "NumberfieldError";
	public final static String NF_PATTERN_NEGATIVE = "-20";
	public final static String NF_PATTERN_PLUS = "+20";
	public final static String NF_PATTERN_ALPHANUMERIC = "ABC";
	public final static String NF_PATTERN_DOT = "20.22";
	public final static String NF_PATTERN_COMMA = "20,22";
	public final static String NF_VALUE_MIN_FALSE = "17";
	public final static String NF_VALUE_MIN_TRUE = "18";
	public final static String NF_VALUE_MAX_FALSE = "76";
	public final static String NF_VALUE_MAX_TRUE = "75";

	/* Data for Combobox - TC0003 */
	public final static String COB_ID = "Combobox";
	public final static String COB_ERROR_ID = "ComboboxError";
	public final static String COB_MILLISEC = TimeUnit.MILLISECONDS.name();
	public final static String COB_MICROSEC = TimeUnit.MICROSECONDS.name();
	public final static String COB_NANOSEC = TimeUnit.NANOSECONDS.name();
	public final static String COB_SECOND = TimeUnit.SECONDS.name();
	public final static String COB_MINUTES = TimeUnit.MINUTES.name();
	public final static String COB_HOURS = TimeUnit.HOURS.name();
	public final static String COB_DAYSC = TimeUnit.DAYS.name();

	/* Data for Listbox - TC0004 */
	public final static String LB_ID = "Listbox";
	public final static String LB_ERROR_ID = "ListboxError";
	public final static String LB_VALUE_0 = "0";
	public final static String LB_VALUE_1 = "1";

	/* Data for Checkbox - TC0005 */
	public final static String CKB_ID = "Checkbox";
	public final static String CKB_ERROR_ID = "CheckboxError";

	/* Data for Datefield - TC0006 */
	public final static String DF_ID = "Datefield";
	public final static String DF_ERROR_ID = "DatefieldError";
	public final static String DF_FULLDATE = "31.10.2014";
	public final static String DF_SHORTDATE = "1.1.20";
	public final static String DF_FORMAT = "09.30.2014";
	public final static String DF_RANGE = "09.80.2014";
	public final static String DF_PAST = "01.01.2000";
	public final static String DF_ALPHANUMERIC = "01.a1.2020";
	public final static String DF_NUMBER = "2020";
}
