package org.nocket.test.eager.validation;

import gengui.annotations.Eager;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;

public class EagerValidationTest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer number;
	private String textEager;
	private NestedObject nestedObject;
	private String textEagerEcho;
	private String text;  
    
    public EagerValidationTest() {
    	nestedObject = new NestedObject(this);
    }

    @NotNull
    public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@NotNull
    @Size(max=5)
    public String getTextEager() {
    	return textEager;
    }
    
    @Eager
    public void setTextEager(String textEager) {
    	this.textEager = textEager;
    	this.textEagerEcho = "Text Eager: " + StringUtils.trimToEmpty(textEager);
    }
    
    public String getTextEagerEcho() {
    	return textEagerEcho;
    }

    public String validateTextEager(String s) {
    	if("qwe".equals(s)) {
    		return "The value \"qwe\" is wrong!";
    	}
    	return null;
    }
    
    public void setTextEagerEcho(String textEagerEcho) {
    	this.textEagerEcho = textEagerEcho;
    }
    
    public String disableTextEagerEcho() {
    	return "Always disabled";
    }
    
    @NotNull
    @Size(max=5)
    public String getText() {
    	return text;
    }

    public void setText(String text) {
    	this.text = text;
    }

	
	public void save() {
		System.out.println("save(): " + this);
	}

	public NestedObject getNestedObject() {
		return nestedObject;
	}

	public void setNestedObject(NestedObject nestedObject) {
		this.nestedObject = nestedObject;
	}

	@Override
	public String toString() {
		return "EagerValidationTest [textEager=" + textEager + ", nestedObject="
				+ nestedObject + ", textEagerEcho=" + textEagerEcho + ", text="
				+ text + "]";
	}

}
