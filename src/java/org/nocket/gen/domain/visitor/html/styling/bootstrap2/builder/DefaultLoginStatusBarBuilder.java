/**
 * 
 */
package org.nocket.gen.domain.visitor.html.styling.bootstrap2.builder;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.nocket.gen.domain.visitor.html.styling.bootstrap2.components.DefaultLoginStatusBar;
import org.nocket.gen.domain.visitor.html.styling.common.LoginStatusBarBuilderI;

/**
 * Builder für die Statusbar des eingeloggten Users
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public class DefaultLoginStatusBarBuilder implements LoginStatusBarBuilderI {
	
	private DefaultLoginStatusBar statusBar = null;


	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.LoginStatusBarBuilderI#getLoginStatusBar()
	 */
	@Override
	public Panel getLoginStatusBar() {
		return statusBar;
	}

	/* (non-Javadoc)
	 * @see org.nocket.gen.domain.visitor.html.styling.common.LoginStatusBarBuilderI#init(java.lang.String)
	 */
	@Override
	public void init(String wicketId, final String username,
			final Class<? extends WebPage> loginPage) {
		statusBar = new DefaultLoginStatusBar(wicketId) {
			
			@Override
			protected Class<? extends WebPage> getLoginPage() {
				return loginPage;
			}
			
			@Override
			protected String getLoggedInUserName() {
				// TODO Auto-generated method stub
				return username;
			}
		};
	}

}
