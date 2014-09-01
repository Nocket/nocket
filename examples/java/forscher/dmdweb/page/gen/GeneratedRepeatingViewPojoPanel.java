package forscher.dmdweb.page.gen;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import dmdweb.gen.page.GeneratedBinding;

public class GeneratedRepeatingViewPojoPanel extends Panel {

    public GeneratedRepeatingViewPojoPanel(String id, IModel<GeneratedRepeatingViewPojo> model) {
        super(id, model);
        GeneratedBinding generatedBinding = new GeneratedBinding(this);
        generatedBinding.bind();
    }

}
