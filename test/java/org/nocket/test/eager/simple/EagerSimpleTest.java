package org.nocket.test.eager.simple;

import gengui.annotations.Eager;

import java.io.Serializable;

public class EagerSimpleTest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String textEager;
	private String textEagerEcho;
	
	private String text;  
    
    public EagerSimpleTest() {
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
    	return "Disabled";
    }
    
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
		return "EagerSimpleTest [textEager=" + textEager + ", textEagerEcho=" + textEagerEcho + ", text=" + text + "]";
	}
	

}
