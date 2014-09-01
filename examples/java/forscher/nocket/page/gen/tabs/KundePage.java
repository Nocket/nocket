package forscher.nocket.page.gen.tabs;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;
import org.nocket.gen.page.element.GroupTabbedPanelElement;
import org.nocket.gen.page.guiservice.WebGuiServiceAdapter;
import org.nocket.gen.page.inject.PageComponent;
import org.nocket.gen.page.visitor.bind.builder.BindingInterceptor;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedGroupTabbedPanel;

import forscher.nocket.page.ForscherPage;

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
