package org.nocket.component.panel.login;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

// TODO: Auto-generated Javadoc
/**
 * Panel showing if user is logged in or not. The panel conatins "Login" or 
 * "Logout" depending on login status.
 * 
 * @author blaz02
 */
@SuppressWarnings("serial")
abstract public class LoginStatusBar extends Panel {
		
	/**
	 * Instantiates a new login status bar.
	 *
	 * @param id the id
	 */
	public LoginStatusBar(String id) {
		super(id);
		final Label userStatus = new Label("loggedin", new AbstractReadOnlyModel<String>() {
			@Override
			public String getObject() {
				final LoginStatusBar bar = LoginStatusBar.this;
				return isLoggedIn() ? getLoggedInUserName() : bar.getString("notloggedin");
			}
		});
		add(userStatus);
		final Link<WebPage> actionLink = new Link<WebPage>("actionlink") {
			@Override
			public void onClick() {
				Class<? extends WebPage> resp =	(isLoggedIn() ? doLogout() : getLoginPage());
				if(resp != null)
					setResponsePage(resp);
			}
		};
		final Label loginactionlabel = new MyLabel("loginactionlabel", new ResourceModel("login"), false);
		final Label logoutactionlabel = new MyLabel("logoutactionlabel", new ResourceModel("logout"), true);
		actionLink.add(loginactionlabel);
		actionLink.add(logoutactionlabel);
		add(actionLink);
	}
	
	/**
	 * Method return true if the user is logged in. Normally you do not have 
	 * to overwrite it.
	 *  
	 *
	 * @return true, if is logged in
	 */
	protected boolean isLoggedIn() {
		return getLoggedInUserName() != null;
	}
	
	/**
	 * Method performs logout. It is called after click on the "Logout" link 
	 * in the panel. By default the method invalidates Wicket's session.
	 * You can overwrite this to feet is your needs.
	 *  
	 * @return Page where the user will be redirected after logout. Can be null. 
	 */
	protected Class<? extends WebPage> doLogout() {
		Session.get().invalidate();
		return null; 
	}	
	
	/**
	 * Method should return class name of a login page. User will be 
	 * redirected there after click on the "Login" link.
	 *  
	 * @return Class name of a login Page 
	 */
	abstract protected Class<? extends WebPage> getLoginPage();
	
	/**
	 * Method should return a user name which is logged in. The name will be 
	 * shown in the panel.
	 * 
	 * @return The name of the logged in user. Null otherwise. 
	 */
	abstract protected String getLoggedInUserName();

	/**
	 * The Class MyLabel.
	 */
	class MyLabel extends Label {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		/** The show if logged in. */
		private boolean showIfLoggedIn;
		
		/**
		 * Instantiates a new my label.
		 *
		 * @param id the id
		 * @param model the model
		 * @param showIfLoggedIn the show if logged in
		 */
		public MyLabel(String id, IModel<String> model, boolean showIfLoggedIn ) {
			super(id, model);
			this.showIfLoggedIn = showIfLoggedIn;
		}
		
		/* (non-Javadoc)
		 * @see org.apache.wicket.Component#isVisible()
		 */
		@Override
		public boolean isVisible() {
			return (LoginStatusBar.this.isLoggedIn() ? showIfLoggedIn : !showIfLoggedIn);
		}		
	}
	
}
