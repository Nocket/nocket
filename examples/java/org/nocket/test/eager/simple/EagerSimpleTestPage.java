package org.nocket.test.eager.simple;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;
import org.nocket.test.page.BrowserTestsPage;

public class EagerSimpleTestPage extends BrowserTestsPage {
    private static final long serialVersionUID = 6949413504297782550L;

    public EagerSimpleTestPage() {
        this(Model.of(new EagerSimpleTest()));
    }

    public EagerSimpleTestPage(final IModel<EagerSimpleTest> model) {
        super(model);
        new GeneratedBinding(this).bind();
    }
    
}
