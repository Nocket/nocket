package org.nocket.component.modal;

import java.io.Serializable;

public enum ButtonFlag implements Serializable {

	OK("btnok"),
	YES("btnyes"),
	NO("btnno");

	private String id;
	
	private ButtonFlag(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
		
}
