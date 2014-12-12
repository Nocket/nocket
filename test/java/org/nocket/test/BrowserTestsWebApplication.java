package org.nocket.test;

import org.apache.wicket.Page;
import org.nocket.NocketWebApplication;
import org.nocket.test.page.BrowserTestsPage;

public class BrowserTestsWebApplication extends NocketWebApplication {

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends Page> getHomePage() {
        return BrowserTestsPage.class;
    }

    /**
     * @see dmdweb.DMDWebApplication#getLoginPage()
     */
    @Override
    public Class<? extends Page> getLoginPage() {
        return BrowserTestsPage.class;
    }

}
