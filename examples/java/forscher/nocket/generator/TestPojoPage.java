package forscher.nocket.generator;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;
import org.nocket.page.DMDWebPage;

public class TestPojoPage extends DMDWebPage {

	private static final long serialVersionUID = 1L;

	public TestPojoPage() {
		this(Model.of(new TestPojo()));
	}

	public TestPojoPage(final IModel<TestPojo> model) {
		super(model);
		final GeneratedBinding generatedBinding = new GeneratedBinding(this);
		generatedBinding.bind();
	}
}
