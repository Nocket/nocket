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
    	return textEager;
    }
    
    @Eager
    public void setTextEager(String textEager) {
    	this.textEager = textEager;
    	this.textEagerEcho = textEager;
    }

    @NotNull
    public String getTextEagerEcho() {
    	return textEagerEcho;
    }
    
    public void setTextEagerEcho(String textEagerEcho) {
    	this.textEagerEcho = textEagerEcho;
    }
    
    public String disableTextEagerEcho() {
    	return "Always disabled";
    }
    
    @NotNull
    public String getText() {
    	return text;
    }

    public void setText(String text) {
    	this.text = text;
    }

	
	public void save() {
		System.out.println("save(): " + this);
	}

	@Override
	public String toString() {
		return "EagerDisableTest [textEager=" + textEager + ", textEagerEcho=" + textEagerEcho + ", text=" + text + "]";
	}
	

}
