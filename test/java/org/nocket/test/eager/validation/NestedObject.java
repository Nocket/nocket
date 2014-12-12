package org.nocket.test.eager.validation;

import gengui.annotations.Eager;

import java.io.Serializable;

import javax.validation.constraints.Size;

public class NestedObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String textEager;

	private EagerValidationTest eagerSimpleTest;

	public NestedObject(EagerValidationTest eagerSimpleTest) {
		this.eagerSimpleTest = eagerSimpleTest;
	}

	@Size(max=5)
	public String getTextEager() {
		return textEager;
	}

	@Eager
	public void setTextEager(String textEager) {
		this.textEager = textEager;
		eagerSimpleTest.setTextEagerEcho("Text Eager Nested: " + textEager);
	}

	@Override
	public String toString() {
		return "NestedObject [textEager=" + textEager + "]";
	}
	
}
