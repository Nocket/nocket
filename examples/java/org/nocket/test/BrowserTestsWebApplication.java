package org.nocket.test;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
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

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return BrowserTestsPage.class;
	}

}
