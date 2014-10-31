package org.nocket.test.eager.validation;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;
import org.nocket.test.page.BrowserTestsPage;

public class EagerValidationTestPage extends BrowserTestsPage {

	private static final long serialVersionUID = -6709142315208024733L;

	public EagerValidationTestPage() {
        this(Model.of(new EagerValidationTest()));
    }

    public EagerValidationTestPage(final IModel<EagerValidationTest> model) {
        super(model);
        new GeneratedBinding(this).bind();
    }
    
}
