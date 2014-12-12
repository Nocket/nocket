package forscher.nocket.page.css.layout;

import org.apache.wicket.behavior.AttributeAppender;

import forscher.nocket.page.css.ExamplesPage;

public class FormsPage extends ExamplesPage {
    public FormsPage() {
        // add the active CSS class for the current link
        this.get("forms").add(new AttributeAppender("class", "active"));
    }
}
