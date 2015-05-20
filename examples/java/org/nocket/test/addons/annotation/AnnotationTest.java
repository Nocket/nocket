package org.nocket.test.addons.annotation;

import java.io.Serializable;

public class AnnotationTest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String field1;
	private String field2;
	
	public void repaintField1() {
		field1 = System.currentTimeMillis() + "";
	}
	
	public void repaintField2() {
		field2 = System.currentTimeMillis() + "";
	}
	
	public String getField1() {
		System.err.println("Getter field1 mit Wert " + field1 + " aufgerufen");
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		System.err.println("Getter field2 mit Wert " + field2 + " aufgerufen");
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
}
