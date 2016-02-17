package forscher.nocket.page.modal;

import java.io.Serializable;

import org.nocket.gen.page.guiservice.WebGuiServiceAdapter;

public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	
	private String vorname;
	private String nachname;
	private Integer alter;

	public Person() {
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public Integer getAlter() {
		return alter;
	}

	public void setAlter(Integer alter) {
		this.alter = alter;
	}
	
	public void zeigeModal() {
		new WebGuiServiceAdapter().showModalPanel(new PersonModalView(this));
	}
}
