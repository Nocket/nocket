package org.nocket.test.prompt;

import gengui.annotations.Prompt;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class PromptTest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String someVeryComplicatedVariableName;
	private String simpleText;

	@NotNull
	@Prompt("Complicated Text")
	public String getSomeVeryComplicatedVariableName() {
		return someVeryComplicatedVariableName;
	}

	public void setSomeVeryComplicatedVariableName(
			String someVeryComplicatedVariableName) {
		this.someVeryComplicatedVariableName = someVeryComplicatedVariableName;
	}

	@NotNull
	public String getSimpleText() {
		return simpleText;
	}

	public void setSimpleText(String simpleText) {
		this.simpleText = simpleText;
	}
	
	public void save() {
		System.err.println("ok");
	}

}
