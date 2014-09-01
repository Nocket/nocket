package forscher.nocket.page.gen.modalByGuiService;

import gengui.annotations.Closer;
import gengui.annotations.Closer.Type;
import gengui.annotations.Forced;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class BookLendingSelection implements Serializable {

    private String[] arr = new String[] { "The bible", "Wicket in action", "Java in a nutshell" };

    private String book;

    private BookLending bookLending;

    public BookLendingSelection(BookLending bookLending) {
        this.bookLending = bookLending;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String[] choiceBook() {
        return arr;
    }

    public String validateBook(String pBook) {
        return StringUtils.isBlank(pBook) ? "You have to choose a book." : null;
    }

    public BookLendingPayment pay() {
        return new BookLendingPayment(bookLending, book);
    }

    @Closer(Type.DEFAULT)
    @Forced
    public BookLendingCancel cancel() {
        return new BookLendingCancel("Lending process canceled.");
    }

}
