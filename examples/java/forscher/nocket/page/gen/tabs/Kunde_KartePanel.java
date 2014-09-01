package forscher.nocket.page.gen.tabs;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.nocket.gen.page.GeneratedBinding;
import org.nocket.gen.page.element.ButtonElement;
import org.nocket.gen.page.visitor.bind.builder.BindingInterceptor;
import org.nocket.gen.page.visitor.bind.builder.components.GeneratedButton;

public class Kunde_KartePanel extends Panel {

    public Kunde_KartePanel(String id, IModel<?> model) {
        super(id, model);
        GeneratedBinding generatedBinding = new GeneratedBinding(this);
        generatedBinding.withInterceptors(new BindingInterceptor() {
            @Override
            public Component createButton(ButtonElement e) {
                if (e.getWicketId().equals(Kunde_KarteConstants.pruefen)) {
                    Button button = new GeneratedButton(e) {
                        @Override
                        protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                            super.onSubmit(target, form);
                            System.err.println("Button call intercepted");
                        }
                    };
                    return button;

                }
                return super.createButton(e);
            }
        });
        generatedBinding.bind();
    }
}
