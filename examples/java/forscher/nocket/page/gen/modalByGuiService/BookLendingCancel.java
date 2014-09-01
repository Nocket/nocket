package forscher.nocket.page.gen.modalByGuiService;

import java.io.Serializable;

public class BookLendingCancel implements Serializable {

    private String message;

    public BookLendingCancel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public BookLending goBack() {
        return new BookLending();
    }
}
