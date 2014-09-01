package forscher.nocket.page.gen.layout;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;

import forscher.nocket.page.ForscherPage;

public class AllComponentsTableTooltipPage extends ForscherPage {

    public AllComponentsTableTooltipPage() {
        this(Model.of(new AllComponentsTableTooltip()));
    }

    public AllComponentsTableTooltipPage(final IModel<AllComponentsTableTooltip> model) {
        super(model);
        new GeneratedBinding(this).bind();
    }

}
