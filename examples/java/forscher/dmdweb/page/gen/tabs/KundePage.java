package forscher.dmdweb.page.gen.tabs;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import dmdweb.gen.page.GeneratedBinding;
import dmdweb.gen.page.element.GroupTabbedPanelElement;
import dmdweb.gen.page.guiservice.WebGuiServiceAdapter;
import dmdweb.gen.page.inject.PageComponent;
import dmdweb.gen.page.visitor.bind.builder.BindingInterceptor;
import dmdweb.gen.page.visitor.bind.builder.components.GeneratedGroupTabbedPanel;
import forscher.dmdweb.page.ForscherPage;

public class KundePage extends ForscherPage {
    private static final long serialVersionUID = 1L;

    @PageComponent("groupTabbedPanel")
    private GeneratedGroupTabbedPanel groupTabbedPanel;

    public KundePage() {
        this(Model.of(new Kunde()));
    }

    public KundePage(final IModel<Kunde> model) {
        super(model);
        final GeneratedBinding generatedBinding = new GeneratedBinding(this);
        generatedBinding.withInterceptors(new BindingInterceptor() {

            @Override
            public Component createGroupTabbedPanel(GroupTabbedPanelElement e) {
                return new GeneratedGroupTabbedPanel(e) {

                    @Override
                    protected boolean allowTabChange(String oldTab, String newTab) {
                        boolean allowTabChange = model.getObject().getAllowTabChange();
                        if (!allowTabChange) {
                            new WebGuiServiceAdapter().status("Wechsel nicht erlaubt!");
                        }
                        return allowTabChange;
                    }

                };
            }

        });
        generatedBinding.bind();

        assert groupTabbedPanel != null;
    }
}
