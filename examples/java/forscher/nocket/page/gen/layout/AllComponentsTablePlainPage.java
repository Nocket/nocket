package forscher.nocket.page.gen.layout;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;

import forscher.nocket.page.ForscherPage;

public class AllComponentsTablePlainPage extends ForscherPage {

    public AllComponentsTablePlainPage() {
        this(Model.of(new AllComponentsTablePlain()));
    }

    public AllComponentsTablePlainPage(final IModel<AllComponentsTablePlain> model) {
        super(model);
        new GeneratedBinding(this).bind();
    }

}
