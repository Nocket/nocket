package forscher.nocket.page.gen;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;

import forscher.nocket.page.ForscherPage;

public class GeneratedRedirectPage extends ForscherPage {

    public GeneratedRedirectPage() {
        this(Model.of(new GeneratedRedirect(null,
                "no redirect, but direct entry")));
    }

    public GeneratedRedirectPage(IModel<GeneratedRedirect> model) {
        super(model);
        new GeneratedBinding(this).bind();
    }

}
