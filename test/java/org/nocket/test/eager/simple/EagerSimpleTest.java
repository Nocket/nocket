package org.nocket.test.eager.simple;

import gengui.annotations.Eager;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;

public class EagerSimpleTest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String textEager;
	private NestedObject nestedObject;
	private String textEagerEcho;
	private String text;  
    
    public EagerSimpleTest() {
    	nestedObject = new NestedObject(this);
    }

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

    public void setTextEagerEcho(String textEagerEcho) {
    	this.textEagerEcho = textEagerEcho;
    }
    
    public String disableTextEagerEcho() {
    	return "\"Text Eager Echo\" is always disabled.";
    }
    
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
		return "EagerSimpleTest [textEager=" + textEager + ", nestedObject="
				+ nestedObject + ", textEagerEcho=" + textEagerEcho + ", text="
				+ text + "]";
	}

	

}
