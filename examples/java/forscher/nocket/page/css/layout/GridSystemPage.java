package forscher.nocket.page.css.layout;

import org.apache.wicket.behavior.AttributeAppender;

import forscher.nocket.page.css.ExamplesPage;

public class GridSystemPage extends ExamplesPage {
    public GridSystemPage() {
        // add the active CSS class for the current link
        this.get("layouts").add(new AttributeAppender("class", "active"));
    }
}
