package forscher.nocket.page.gen.ajax;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;

import forscher.nocket.page.ForscherPage;

public class AjaxTargetUpdateTestPage extends ForscherPage {
    private static final long serialVersionUID = 1L;

    public AjaxTargetUpdateTestPage() {
        this(Model.of(new AjaxTargetUpdateTest()));
    }

    public AjaxTargetUpdateTestPage(final IModel<AjaxTargetUpdateTest> model) {
        super(model);
        final GeneratedBinding generatedBinding = new GeneratedBinding(this);
        generatedBinding.bind();
    }
}
