package forscher.nocket.page.css;

import org.apache.wicket.Page;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.nocket.component.panel.login.LoginStatusBar;
import org.nocket.page.DMDWebPage;

import forscher.nocket.page.css.layout.ComplexFormsPage;
import forscher.nocket.page.css.layout.FormsPage;
import forscher.nocket.page.css.layout.GridSystemPage;

@SuppressWarnings("serial")
public class ExamplesPage extends DMDWebPage {
    public ExamplesPage() {
        this((IModel<?>) null);
    }

    public ExamplesPage(IModel<?> model) {
        super(model);

        add(new DebugBar("debug"));

        add(new LoginStatusBar("loginstatus") {
            private static final long serialVersionUID = 1L;

            @Override
            protected String getLoggedInUserName() {
                return "dummy";
            }

            @Override
            protected Class<? extends WebPage> getLoginPage() {
                return ExamplesPage.this.getClass();
            }

        });

        // Navigation pages
        add(new BookmarkablePageLink<Page>("layouts", GridSystemPage.class));
        add(new BookmarkablePageLink<Page>("forms", FormsPage.class));
        add(new BookmarkablePageLink<Page>("complexForms", ComplexFormsPage.class));

    }
}
