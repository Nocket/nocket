package org.nocket.test.eager.simple;

import gengui.annotations.Eager;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class EagerSimpleTest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String textEager;
	private String textEagerEcho;
	private String text;  
    
    public EagerSimpleTest() {
    }

    public String getTextEager() {
    	return textEager;
    }
    
    @Eager
    public void setTextEager(String textEager) {
    	this.textEager = textEager;
    	this.textEagerEcho = StringUtils.trimToEmpty(textEager) 
    				+ StringUtils.trimToEmpty(text);
    }
    
    public String getTextEagerEcho() {
    	return textEagerEcho;
    }
    
    public void setTextEagerEcho(String textEagerEcho) {
    	this.textEagerEcho = textEagerEcho;
    }
    
    public String disableTextEagerEcho() {
    	return "Always disabled";
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

	@Override
	public String toString() {
		return "EagerSimpleTest [textEager=" + textEager + ", textEagerEcho=" + textEagerEcho + ", text=" + text + "]";
	}
	

}
