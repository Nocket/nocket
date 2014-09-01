package forscher.dmdweb.page.gen.modalByGuiService;

import forscher.dmdweb.page.gen.modalByGuiService.BookLending.BookRow;
import gengui.annotations.Closer;
import gengui.annotations.Closer.Type;
import gengui.annotations.Forced;

import java.io.Serializable;

public class BookLendingPayment implements Serializable {

    private final BookLending bookLending;
    private final String book;

    public BookLendingPayment(BookLending bookLending, String book) {
        this.bookLending = bookLending;
        this.book = book;
    }

    public String getBook() {
        return book;
    }

    @Closer()
    public void pay() {
        bookLending.bookRow.add(new BookRow(book, true));
    }

    @Closer(Type.DEFAULT)
    @Forced
    public BookLendingCancel cancel() {
        return new BookLendingCancel("Lending process canceled.");
    }

}
