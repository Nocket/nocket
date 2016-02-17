/**
 * 
 */
package forscher.nocket.page.modal;

import java.io.Serializable;

import gengui.annotations.Closer;
import gengui.annotations.Closer.Type;
import gengui.annotations.Forced;

/**
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class PersonModalView implements Serializable {

	private Person person;
	
	public PersonModalView(Person person) {
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	
	@Closer(Type.DEFAULT)
	@Forced
	public void schliessen() {
		
	}
}
