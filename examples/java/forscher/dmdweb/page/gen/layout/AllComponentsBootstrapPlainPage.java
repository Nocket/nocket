package forscher.dmdweb.page.gen.layout;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import dmdweb.gen.page.GeneratedBinding;
import forscher.dmdweb.page.ForscherPage;

public class AllComponentsBootstrapPlainPage extends ForscherPage {

    public AllComponentsBootstrapPlainPage() {
        this(Model.of(new AllComponentsBootstrapPlain()));
    }

    public AllComponentsBootstrapPlainPage(final IModel<AllComponentsBootstrapPlain> model) {
        super(model);
        new GeneratedBinding(this).bind();
    }

}
