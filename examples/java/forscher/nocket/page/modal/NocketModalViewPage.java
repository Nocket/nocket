package forscher.nocket.page.modal;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;

import forscher.nocket.page.ForscherPage;

public class NocketModalViewPage extends ForscherPage {

	private static final long serialVersionUID = 1L;
	
	public NocketModalViewPage() {
		this(Model.of(new NocketModalView()));
	}

	public NocketModalViewPage(final IModel<NocketModalView> model) {
		super(model);
		final GeneratedBinding generatedBinding = new GeneratedBinding(this);
		generatedBinding.bind();
	}
}
