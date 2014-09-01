package forscher.nocket.page.gen;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.nocket.gen.page.GeneratedBinding;

public class GeneratedRepeatingViewPojoPanel extends Panel {

    public GeneratedRepeatingViewPojoPanel(String id, IModel<GeneratedRepeatingViewPojo> model) {
        super(id, model);
        GeneratedBinding generatedBinding = new GeneratedBinding(this);
        generatedBinding.bind();
    }

}
