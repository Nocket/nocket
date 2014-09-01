package forscher.nocket.page.gen.layout;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;

import forscher.nocket.page.ForscherPage;

public class AllComponentsBootstrapTooltipPage extends ForscherPage {

    public AllComponentsBootstrapTooltipPage() {
        this(Model.of(new AllComponentsBootstrapTooltip()));
    }

    public AllComponentsBootstrapTooltipPage(final IModel<AllComponentsBootstrapTooltip> model) {
        super(model);
        new GeneratedBinding(this).bind();
    }

}
