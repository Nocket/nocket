package forscher.nocket.page.css;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;

import forscher.nocket.ForscherWebApplication;

public class ExamplesWebApplication extends ForscherWebApplication {

    @Override
	protected Class<? extends WebPage> getSignInPageClass() {
        return ExamplesPage.class;
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends Page> getHomePage() {
        return ExamplesPage.class;
    }

}
