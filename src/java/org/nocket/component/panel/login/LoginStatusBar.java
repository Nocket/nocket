package org.nocket.component.panel.login;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.nocket.gen.domain.visitor.html.styling.StylingFactory;
import org.nocket.gen.domain.visitor.html.styling.common.LoginStatusBarBuilderI;

// TODO: Auto-generated Javadoc
/**
 * Panel showing if user is logged in or not. The panel conatins "Login" or 
 * "Logout" depending on login status.
 * 
 * @author blaz02
 */
@SuppressWarnings("serial")
public class LoginStatusBar extends Panel {
		
	/**
	 * Instantiates a new login status bar.
	 *
	 * @param id the id
	 */
	public LoginStatusBar(String id, String username, Class<? extends WebPage> loginPage) {
		super(id);
		LoginStatusBarBuilderI builder = StylingFactory.getStylingStrategy().getLoginStatusBarBuilder();
		builder.init("sf_loginStatusBar", username, loginPage);
		add(builder.getLoginStatusBar());
	}
	
}
