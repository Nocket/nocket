package forscher.nocket.page.modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.nocket.gen.page.guiservice.WebGuiServiceAdapter;

public class NocketModalView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private List<Person> personen;

	public NocketModalView() {
		personen = new ArrayList<Person>();
		
		Person person1 = new Person();
		person1.setVorname("Jesse");
		person1.setNachname("Pinkman");
		person1.setAlter(26);
		
		Person person2 = new Person();
		person2.setAlter(50);
		person2.setVorname("Walter Hartwell");
		person2.setNachname("White");
		
		personen.add(person1);
		personen.add(person2);
	}	
	
	public List<Person> getPersonen() {
		return personen;
	}
	
	public void setPersonen(List<Person> personen) {
		this.personen = personen;
	}
	
	public void generatedSite() {
		new WebGuiServiceAdapter().showModalPanel(new PersonModalView(personen.get(0)));
	}
}
