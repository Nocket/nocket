package forscher.nocket.page.css;

import org.apache.wicket.Page;

import forscher.nocket.ForscherWebApplication;

public class ExamplesWebApplication extends ForscherWebApplication {

    @Override
    public Class<? extends Page> getLoginPage() {
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