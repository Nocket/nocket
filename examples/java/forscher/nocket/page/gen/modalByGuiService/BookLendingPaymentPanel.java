package forscher.nocket.page.gen.modalByGuiService;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.nocket.gen.page.GeneratedBinding;

public class BookLendingPaymentPanel extends Panel {

    public BookLendingPaymentPanel(String id, IModel<BookLendingPayment> model) {
        super(id, model);
        GeneratedBinding generatedBinding = new GeneratedBinding(this);
        generatedBinding.bind();
    }
}
