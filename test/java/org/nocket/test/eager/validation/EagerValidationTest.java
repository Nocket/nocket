package org.nocket.test.eager.validation;

import gengui.annotations.Eager;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class EagerValidationTest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String textEager;
	private String textEagerEcho;
	private String text;  
    
    public EagerValidationTest() {
    }

    public String getTextEager() {
    	System.out.println("getTextEager(): " + this);
    	return textEager;
    }
    
    @Eager
    public void setTextEager(String textEager) {
    	this.textEager = textEager;
    	this.textEagerEcho = textEager;
    	System.out.println("setTextEager(): " + this);
    }

    @NotNull
    public String getTextEagerEcho() {
    	System.out.println("getTextEagerEcho(): " + this);
    	return textEagerEcho;
    }
    
    public void setTextEagerEcho(String textEagerEcho) {
    	this.textEagerEcho = textEagerEcho;
    	System.out.println("setTextEagerEcho(): " + this);
    }
    
    public String disableTextEagerEcho() {
    	System.out.println("disableTextEagerEcho(): " + this);
    	return "Always disabled";
    }
    
    @NotNull
    public String getText() {
    	System.out.println("getText(): " + this);
    	return text;
    }

    public void setText(String text) {
    	this.text = text;
    	System.out.println("setText(): " + this);
    }

	
	public void save() {
		System.out.println("save(): " + this);
	}

	@Override
	public String toString() {
		return "EagerDisableTest [textEager=" + textEager + ", textEagerEcho=" + textEagerEcho + ", text=" + text + "]";
	}
	

}
