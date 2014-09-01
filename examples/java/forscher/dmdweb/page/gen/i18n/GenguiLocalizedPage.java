package forscher.dmdweb.page.gen.i18n;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import dmdweb.gen.page.GeneratedBinding;
import forscher.dmdweb.page.ForscherPage;

public class GenguiLocalizedPage extends ForscherPage {

    public GenguiLocalizedPage() {
        this(Model.of(new GenguiLocalized()));
    }

    public GenguiLocalizedPage(final IModel<GenguiLocalized> model) {
        super(model);
        new GeneratedBinding(this).bind();
    }

}
