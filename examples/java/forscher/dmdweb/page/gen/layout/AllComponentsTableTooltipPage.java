package forscher.dmdweb.page.gen.layout;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import dmdweb.gen.page.GeneratedBinding;
import forscher.dmdweb.page.ForscherPage;

public class AllComponentsTableTooltipPage extends ForscherPage {

    public AllComponentsTableTooltipPage() {
        this(Model.of(new AllComponentsTableTooltip()));
    }

    public AllComponentsTableTooltipPage(final IModel<AllComponentsTableTooltip> model) {
        super(model);
        new GeneratedBinding(this).bind();
    }

}
