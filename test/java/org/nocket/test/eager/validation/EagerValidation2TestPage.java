package org.nocket.test.eager.validation;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;
import org.nocket.test.page.BrowserTestsPage;

public class EagerValidation2TestPage extends BrowserTestsPage {

	private static final long serialVersionUID = -6709142315208024733L;

	public EagerValidation2TestPage() {
        this(Model.of(new EagerValidation2Test()));
    }

    public EagerValidation2TestPage(final IModel<EagerValidation2Test> model) {
        super(model);
        new GeneratedBinding(this).bind();
    }
    
}
