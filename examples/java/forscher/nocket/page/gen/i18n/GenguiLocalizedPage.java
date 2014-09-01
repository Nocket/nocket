package forscher.nocket.page.gen.i18n;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;

import forscher.nocket.page.ForscherPage;

public class GenguiLocalizedPage extends ForscherPage {

    public GenguiLocalizedPage() {
        this(Model.of(new GenguiLocalized()));
    }

    public GenguiLocalizedPage(final IModel<GenguiLocalized> model) {
        super(model);
        new GeneratedBinding(this).bind();
    }

}
