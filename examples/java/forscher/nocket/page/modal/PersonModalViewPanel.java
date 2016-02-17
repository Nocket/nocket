package forscher.nocket.page.modal;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.nocket.gen.page.GeneratedBinding;

public class PersonModalViewPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public PersonModalViewPanel(String id, final IModel<PersonModalView> model) {
		super(id, model);
		final GeneratedBinding generatedBinding = new GeneratedBinding(this);
		generatedBinding.bind();
	}
}
