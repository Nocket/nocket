package org.nocket.test.eager.validation;

import gengui.annotations.Eager;

import java.io.Serializable;

import javax.validation.constraints.Size;

public class EagerValidation2Test implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean toogler;
	private String text;
	private String textEager;

	private String kontonummer;
	private String blz;

	public boolean getToogler() {
		return toogler;
	}

	@Eager
	public void setToogler(boolean toogler) {
		this.toogler = toogler;
		doChangesForEager();
	}

	public String getKontonummer() {
		return kontonummer;
	}

	public void setKontonummer(String kontonummer) {
		this.kontonummer = kontonummer;
	}

	public String disableKontonummer() {
		return toogler ? "" : null;
	}

	public String getBlz() {
		return blz;
	}

	public void setBlz(String blz) {
		this.blz = blz;
	}

	public String disableBlz() {
		return toogler ? "" : null;
	}

	@Size(max = 5)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTooglerResult() {
		return toogler ? "gesetzt" : "nicht gesetzt";
	}

	public String getTextEager() {
		return textEager;
	}

	@Eager
	public void setTextEager(String textEager) {
		this.textEager = textEager;
		doChangesForEager();
	}

	private void doChangesForEager() {
		if(toogler) {
			kontonummer = null;
			blz = null;
		}
		
	}
	
	
}
