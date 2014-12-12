package forscher.nocket.page.css.layout;

import org.apache.wicket.behavior.AttributeAppender;

import forscher.nocket.page.css.ExamplesPage;

public class ComplexFormsPage extends ExamplesPage {
    public ComplexFormsPage() {
        // add the active CSS class for the current link
        this.get("complexForms").add(new AttributeAppender("class", "active"));
    }
}
