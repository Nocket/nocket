package org.nocket.test.eager.disable;

import gengui.annotations.Eager;
import gengui.annotations.Forced;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Forced
	public void setEagerCheckbox(Boolean eagerCheckbox) {
		this.eagerCheckbox = eagerCheckbox;
		if(this.eagerCheckbox) {
			this.text = null;
		}
			
	}

	@NotNull
	@Size(max=5)
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
