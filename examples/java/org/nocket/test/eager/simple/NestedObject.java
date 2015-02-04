package org.nocket.test.eager.simple;

import gengui.annotations.Eager;

import java.io.Serializable;

public class NestedObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String textEager;

	private EagerSimpleTest eagerSimpleTest;

	public NestedObject(EagerSimpleTest eagerSimpleTest) {
		this.eagerSimpleTest = eagerSimpleTest;
	}

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
