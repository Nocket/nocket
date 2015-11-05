package forscher.nocket.generator;

import java.io.Serializable;

public class TestPojo implements Serializable {

	protected String firstName;
	
	protected String lastName;
	
	protected Boolean newsletter;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(Boolean newsletter) {
		this.newsletter = newsletter;
	}
	
}
