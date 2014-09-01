package forscher.nocket.page.gen;

import gengui.annotations.Assisted;
import gengui.annotations.Choicetype;
import gengui.annotations.Choicetype.Type;
import gengui.annotations.Decorate;
import gengui.annotations.Eager;
import gengui.annotations.Forced;
import gengui.annotations.Format;
import gengui.annotations.Hidden;
import gengui.annotations.Modal;
import gengui.annotations.Prompt;
import gengui.annotations.Validate;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.BooleanUtils;
import org.apache.wicket.util.io.IClusterable;
import org.nocket.gen.page.guiservice.WebGuiServiceAdapter;

/**
 * This class is used to demonstrate HTML generation and generic binding of
 * POJOs and to test various different types of supported bindings
 *
 */
@SuppressWarnings("serial")
public class Generated implements IClusterable, GeneratedConstraints {

	private TimeUnit enumChoicer = TimeUnit.DAYS;
	private TimeUnit notNullableEnumChoicer = TimeUnit.HOURS;
	private String simpleChoicer = "simpleChoiceValue";
	private String[] listChoicer = new String[] { "1", "2" };
	private String[] blazekChoicer = new String[] { "1", "3" }; // Multiple
	// choice,
	// displayed as
	// Blazek
	// control by
	// HTML
	// manipulation
	private String[] emptyChoicer; // Special case: option set is null, value is
	// null, should not cause exceptions
	private Collection<GeneratedChoice> structuredChoicer;
	private GeneratedChoice[] structuredArrayChoicer;
	private GeneratedChoice structuredSingleChoicer;
	private String tableChoicer;
	private Date dateAssisted = new Date();
	private String name = "someName";
	private Postfach postfach = new Postfach(1234);
	private Merge merge = new Merge();
	private boolean checkbox = true;
	private Boolean checkboxNullable;
	private boolean radioChoice = true;
	private Boolean radioChoiceNullable;
	private TimeUnit radioEnumChoicer = TimeUnit.DAYS;
	private double formattedDouble = 12.1D;
	private BigDecimal bigDecimal = new BigDecimal("1111111111111111.11111111111");
	private String longText = "This is a long text which is represented in a text area after having modified the generated HTML manually";
	private int blueValue;
	private File file;
	private File download = new File("WEB-INF/web.xml");
	private File thumbnail = new File("img/wicket.png");
	private String fullImage = "/img/wicket_logo.png";
	private String credential;
	private String separatedConstraint = "Must not be empty ";

	private List<GeneratedRepeatingViewPojo> generatedRepeatingViewPojos = new ArrayList<GeneratedRepeatingViewPojo>() {

		{
			add(new GeneratedRepeatingViewPojo("Test1"));
			add(new GeneratedRepeatingViewPojo("Test2"));
			add(new GeneratedRepeatingViewPojo());
		}
	};

	private static GeneratedChoice allStructuredChoices[] = new GeneratedChoice[] { new GeneratedChoice("Edwin", "Stang"),
		new GeneratedChoice("Albert", "Blazek"), new GeneratedChoice("Jan", "Lessner"), new GeneratedChoice("Joerg", "Meister") };

	public Generated() {
		new WebGuiServiceAdapter().infoMessage("Welcome!",
				"<html>Hello World onPageLoad!<p><i>This is also an example for HTML-formatted message text!</i></html>");
	}

	@Max(255)
	@Prompt("Field changes color according to value")
	public int getBlueValue() {
		return blueValue;
	}

	@Eager
	public void setBlueValue(int blueValue) {
		System.out.println("Blue value: " + blueValue);
		this.blueValue = blueValue;
	}

	public BigDecimal getBigDecimal() {
		return bigDecimal;
	}

	public void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

	@Format("###,##0.00")
	public int getFormattedDouble() {
		return (int) formattedDouble;
	}

	public void setFormattedDouble(int formattedDouble) {
		this.formattedDouble = formattedDouble;
	}

	@Format("yyyy.MM.dd 'custom' HH:mm:ss")
	@NotNull
	public Date getDateAssisted() {
		return dateAssisted;
	}

	@Assisted
	public void setDateAssisted(Date dateAssisted) {
		this.dateAssisted = dateAssisted;
	}

	public String disableDateAssisted() {
		if (BooleanUtils.isTrue(getCheckboxNullable())) {
			return "disabled by CheckboxNullable";
		}
		else {
			return null;
		}
	}

	public Boolean getRadioChoiceNullable() {
		return radioChoiceNullable;
	}

	public void setRadioChoiceNullable(Boolean radioChoiceNullable) {
		this.radioChoiceNullable = radioChoiceNullable;
	}

	public boolean getRadioChoice() {
		return radioChoice;
	}

	@Eager
	public void setRadioChoice(boolean radioChoice) {
		this.radioChoice = radioChoice;
	}

	public String validateRadioChoice(boolean newValue) {
		if (newValue == false) {
			return "false not allowed!";
		}
		else {
			return null;
		}
	}

	@Validate
	public TimeUnit getEnumChoicer() {
		return enumChoicer;
	}

	public String validateEnumChoicer(TimeUnit newValue) {
		if (newValue == TimeUnit.NANOSECONDS) {
			return "NANOSECONDS not allowed!";
		}
		else {
			return null;
		}
	}

	@Eager
	public void setEnumChoicer(TimeUnit timeUnit) {
		enumChoicer = timeUnit;
	}

	@Eager
	public void setNotNullableEnumChoicer(TimeUnit notNullableEnumChoicer) {
		this.notNullableEnumChoicer = notNullableEnumChoicer;
	}

	// Just to show, that null is not part of the list for the selection box,
	// because the getter is annoteted with notnull
	@NotNull
	public TimeUnit getNotNullableEnumChoicer() {
		return notNullableEnumChoicer;
	}

	public TimeUnit getRadioEnumChoicer() {
		return radioEnumChoicer;
	}

	public void setRadioEnumChoicer(TimeUnit radioEnumChoicer) {
		this.radioEnumChoicer = radioEnumChoicer;
	}

	// @Validate // uncomment to force exception
	public String getSimpleChoicer() {
		return simpleChoicer;
	}

	public void setSimpleChoicer(String simpleChoicer) {
		this.simpleChoicer = simpleChoicer;
	}

	public String[] choiceSimpleChoicer() {
		return new String[] { "1", "2", "3", "4" };
	}

	public String getTableChoicer() {
		return tableChoicer;
	}

	public void setTableChoicer(String tableChoicer) {
		this.tableChoicer = tableChoicer;
	}

	@Choicetype(Type.TABLE)
	public String[] choiceTableChoicer() {
		return new String[] { "1", "2", "3", "4" };
	}

	public String[] getListChoicer() {
		return listChoicer;
	}

	public void setListChoicer(String[] listChoicer) {
		System.out.println("listChoicer set to " + Arrays.asList(listChoicer));
		this.listChoicer = listChoicer;
	}

	public String[] choiceListChoicer() {
		return new String[] { "1", "2", "3" };
	}

	public String[] getBlazekChoicer() {
		return blazekChoicer;
	}

	public void setBlazekChoicer(String[] blazekChoicer) {
		this.blazekChoicer = blazekChoicer;
	}

	public String[] choiceBlazekChoicer() {
		return choiceListChoicer();
	}

	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Postfach getPostfach() {
		return postfach;
	}

	public void setPostfach(Postfach postfach) {
		this.postfach = postfach;
	}

	public Merge getMerge() {
		return merge;
	}

	public void setMerge(Merge merge) {
		this.merge = merge;
	}

	public void someButton() {
	}

	public String disableSomeButton() {
		return "weg";
	}

	public void someButtonToo() {
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Decorate(GeneratedConstants.Merge_Inner)
	@Hidden
	public MergeInner getInner() {
		return merge.getInner();
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	@Eager
	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public String validateCheckbox(boolean newValue) {
		if (newValue == false) {
			return "false not allowed!";
		}
		else {
			return null;
		}
	}

	public Boolean getCheckboxNullable() {
		return checkboxNullable;
	}

	@Eager
	public void setCheckboxNullable(Boolean checkboxNullable) {
		this.checkboxNullable = checkboxNullable;
	}

	public String getLongText() {
		return longText;
	}

	public void setLongText(String longText) {
		this.longText = longText;
	}

	public String[] getEmptyChoicer() {
		return emptyChoicer;
	}

	public String[] choiceEmptyChoicer() {
		return null;
	}

	public void setEmptyChoicer(String[] emptyChoicer) {
		this.emptyChoicer = emptyChoicer;
	}

	public Collection<GeneratedChoice> getStructuredChoicer() {
		return structuredChoicer;
	}

	public void setStructuredChoicer(Collection<GeneratedChoice> structuredChoicer) {
		System.out.println(structuredChoicer);
		this.structuredChoicer = structuredChoicer;
	}

	public GeneratedChoice[] choiceStructuredChoicer() {
		return allStructuredChoices;
	}

	public GeneratedChoice[] getStructuredArrayChoicer() {
		return structuredArrayChoicer;
	}

	public void setStructuredArrayChoicer(GeneratedChoice[] structuredArrayChoicer) {
		for (GeneratedChoice chosen : structuredArrayChoicer) {
			System.out.println(chosen);
		}
		this.structuredArrayChoicer = structuredArrayChoicer;
	}

	public GeneratedChoice[] choiceStructuredArrayChoicer() {
		return allStructuredChoices;
	}

	public GeneratedChoice getStructuredSingleChoicer() {
		return structuredSingleChoicer;
	}

	public void setStructuredSingleChoicer(GeneratedChoice structuredSingleChoicer) {
		this.structuredSingleChoicer = structuredSingleChoicer;
	}

	public GeneratedChoice[] choiceStructuredSingleChoicer() {
		return allStructuredChoices;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		System.out.println("setFile " + file);
		this.file = file;
	}

	public File getDownload() {
		return download;
	}

	public File getThumbnail() {
		return thumbnail;
	}

	public String getFullImage() {
		return fullImage;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		System.out.println("Credential set to " + credential);
		this.credential = credential;
	}

	@Override
	public String getSeparatedConstraint() {
		return separatedConstraint;
	}

	public void setSeparatedConstraint(String separatedConstraint) {
		this.separatedConstraint = separatedConstraint;
	}

	public void testRedirect() {
		// new WebGuiServiceAdapter().confirmMessage("Redirect?", new
		// ModalResultCallback<ButtonFlag>() {
		// @Override
		// public void onResult(ButtonFlag flag) {
		// if (flag == ButtonFlag.OK || flag == ButtonFlag.YES) {
		// new WebGuiServiceAdapter().showPage(new
		// GeneratedRedirect(Generated.this, Generated.this.toString()));
		// }
		// }
		// });
		new WebGuiServiceAdapter().showPage(new GeneratedRedirect(Generated.this, Generated.this.toString()));
	}

	@Forced
	public void forcedButton() {
		name = "may the force be with you!";
	}

	public Pageless pageless() {
		return new Pageless();
	}

	@Modal
	public Panelless panelless() {
		return new Panelless();
	}

	public void testRuntimeException() {
		throw new RuntimeException("runtime exception shows in wicket error page!");
	}

	public void testException() throws Exception {
		throw new Exception("normal.exception");
	}

	public void testIgnoredExceptionWithoutDialog() throws Exception {
		throw new Exception();
	}

	public boolean getTouched() {
		return new WebGuiServiceAdapter().touched("");
	}

	public void touch() {
		new WebGuiServiceAdapter().touch("");
	}

	public void untouch() {
		new WebGuiServiceAdapter().untouch("");
	}

	public void printStatus() {
		new WebGuiServiceAdapter().status("This is an information with 'quotes', produced by the \"status\" method");
	}

	public List<GeneratedRepeatingViewPojo> getGeneratedRepeatingViewPojo() {
		return generatedRepeatingViewPojos;
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		System.err.println("Generated.readObject() " + this);
	}

}
