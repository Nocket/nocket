package forscher.nocket.page.gen.layout;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;

import forscher.nocket.page.ForscherPage;

public class AllComponentsBootstrapPlainPage extends ForscherPage {

    public AllComponentsBootstrapPlainPage() {
        this(Model.of(new AllComponentsBootstrapPlain()));
    }

    public AllComponentsBootstrapPlainPage(final IModel<AllComponentsBootstrapPlain> model) {
        super(model);
        new GeneratedBinding(this).bind();
    }

}
