package forscher.nocket.page.gen;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.nocket.gen.page.GeneratedBinding;

public class GeneratedSeperatedFormPanel extends Panel {

    public GeneratedSeperatedFormPanel(String id, IModel<GeneratedSeperatedForm> model) {
        super(id, model);
        new GeneratedBinding(this).bind();
    }

}
