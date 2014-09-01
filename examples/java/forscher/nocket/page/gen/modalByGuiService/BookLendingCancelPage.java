package forscher.nocket.page.gen.modalByGuiService;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.nocket.gen.page.GeneratedBinding;

import forscher.nocket.page.ForscherPage;

public class BookLendingCancelPage extends ForscherPage {
    private static final long serialVersionUID = 1L;

    public BookLendingCancelPage() {
        this(Model.of(new BookLending()));
    }

    public BookLendingCancelPage(final IModel<BookLending> model) {
        super(model);
        final GeneratedBinding generatedBinding = new GeneratedBinding(this);
        generatedBinding.bind();
    }
}
