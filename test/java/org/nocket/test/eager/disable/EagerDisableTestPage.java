package org.nocket.test.eager.disable;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;
import org.nocket.test.page.BrowserTestsPage;

public class EagerDisableTestPage extends BrowserTestsPage {

	private static final long serialVersionUID = -13423802349360226L;

	public EagerDisableTestPage() {
        this(Model.of(new EagerDisableTest()));
    }

    public EagerDisableTestPage(final IModel<EagerDisableTest> model) {
        super(model);
        new GeneratedBinding(this).bind();
    }
    
}
