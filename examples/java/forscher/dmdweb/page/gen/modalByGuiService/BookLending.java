package forscher.dmdweb.page.gen.modalByGuiService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dmdweb.gen.page.guiservice.WebGuiServiceAdapter;

public class BookLending implements Serializable {

    public static class BookRow implements Serializable {

        public BookRow(String name, boolean payed) {
            this.name = name;
            this.payed = payed;
        }

        private String name;
        private boolean payed;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPayed() {
            return payed;
        }

        public void setPayed(boolean payed) {
            this.payed = payed;
        }
    }

    List<BookRow> bookRow = new ArrayList<BookLending.BookRow>();

    public BookLendingCancel leihen() {
        if (bookRow.size() < 2) {
            new WebGuiServiceAdapter().showModalPanel(new BookLendingSelection(this));
            return null;
        } else {
            return new BookLendingCancel("Too many book. You cannot lend any further books.");
        }
    }

    public List<BookRow> getBookRow() {
        return bookRow;
    }

    public void setBookRow(List<BookRow> bookRow) {
        this.bookRow = bookRow;
    }
}
