package forscher.dmdweb.page.gen;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import dmdweb.gen.page.GeneratedBinding;

public class GeneratedSeperatedFormPanel extends Panel {

    public GeneratedSeperatedFormPanel(String id, IModel<GeneratedSeperatedForm> model) {
        super(id, model);
        new GeneratedBinding(this).bind();
    }

}
