package forscher.nocket.page.modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.nocket.component.modal.ButtonFlag;
import org.nocket.gen.page.guiservice.ModalResultCallback;
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
	
	public void confirmation() {
		new WebGuiServiceAdapter().confirmMessage("Titel", "Meine Nachricht", new ModalResultCallback<ButtonFlag>() {
			@Override
			public void onResult(ButtonFlag result) {
				System.out.println("Ergebnis: " + ReflectionToStringBuilder.toString(result));
			}
		});
	}
	
	public void error() {
		new WebGuiServiceAdapter().errorMessage("Fehlernachricht");
	}
	
	public void warning() {
		new WebGuiServiceAdapter().warningMessage("Warnnachricht");
	}
	
	public void info() {
		new WebGuiServiceAdapter().infoMessage("Infonachricht");
	}
	
	
}
