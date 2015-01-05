package forscher.nocket;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.nocket.NocketWebApplication;

import forscher.nocket.page.HomePage;

public class ForscherWebApplication extends NocketWebApplication {

	@Override
	public void init() {
		super.init();
		// Let's try a gengui-based translation :-)
//		I18n i18n = new I18nPropertyBasedImpl();
//		System.out.println("UEBERSETZUNGSTEXT: " + i18n.translate("context.menu.copy"));
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return null;
	}

}
