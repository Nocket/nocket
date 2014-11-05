package org.nocket.test.eager.disable;

import gengui.annotations.Eager;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class EagerDisableTest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean eagerCheckbox = Boolean.FALSE;
	private String text;
    
    public EagerDisableTest() {
    }
    
    public Boolean getEagerCheckbox() {
		return eagerCheckbox;
	}

    @Eager
	public void setEagerCheckbox(Boolean eagerCheckbox) {
		this.eagerCheckbox = eagerCheckbox;
	}

	@NotNull
    public String getText() {
    	return text;
    }

    public void setText(String text) {
    	this.text = text;
    }

    public String disableText() {
    	return this.eagerCheckbox ? "Disabled due checkbox" : null;
    }
	
	public void save() {
		System.out.println("save(): " + this);
	}

	@Override
	public String toString() {
		return "EagerDisableTest [eagerCheckbox=" + eagerCheckbox + ", text=" + text + "]";
	}

}
