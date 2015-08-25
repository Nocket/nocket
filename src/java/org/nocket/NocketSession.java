package org.nocket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.wicket.PageReference;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.nocket.component.menu.MenuItem;
import org.nocket.gen.page.guiservice.DMDWebGenGuiServiceProvider;

public class NocketSession extends AbstractAuthenticatedWebSession {

	private static final long serialVersionUID = 1L;

	private static final File META_SESSION_DIR = new File(System.getProperty("java.io.tmpdir"), "org.nocket");

	private volatile boolean signedIn;
	
	static {
		FileUtils.deleteQuietly(META_SESSION_DIR);
	}

	public static NocketSession get() {
		return (NocketSession) Session.get();
	}

	private File sessionDir;
	private HashMap<Integer, List<PageReference>> pageHistory = new HashMap<Integer, List<PageReference>>();
	private DMDWebGenGuiServiceProvider dmdWebGenGuiServiceProvider;
	private transient MenuItem lastSelectedMenuItem;

	public NocketSession(Request request) {
		super(request);
	}

	private File getSessionDir() {
		if (sessionDir == null) {
			sessionDir = new File(META_SESSION_DIR, getId());
			try {
				FileUtils.deleteQuietly(sessionDir);
				FileUtils.forceMkdir(sessionDir);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return sessionDir;
	}

	public HashMap<Integer, List<PageReference>> getPageHistory() {
		return pageHistory;
	}

	public List<PageReference> getPageReferences(int viewHashCode) {
		Integer viewHashCodeAsInteger = Integer.valueOf(viewHashCode);
		List<PageReference> pageReferences = getPageHistory().get(viewHashCodeAsInteger);
		if (pageReferences == null) {
			pageReferences = new ArrayList<PageReference>();
			getPageHistory().put(viewHashCodeAsInteger, pageReferences);
		}
		return pageReferences;
	}

	public synchronized DMDWebGenGuiServiceProvider getDMDWebGenGuiServiceProvider() {
		if (dmdWebGenGuiServiceProvider == null) {
			dmdWebGenGuiServiceProvider = new DMDWebGenGuiServiceProvider();
		}
		return dmdWebGenGuiServiceProvider;
	}

	public File putFileInSessionDir(String filename, InputStream data) {
		try {
			File file = getFileInSessionDir(filename);
			FileUtils.deleteQuietly(file);
			IOUtils.copy(data, new FileOutputStream(file));
			return file;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public File getFileInSessionDir(String filename) {
		return new File(getSessionDir(), filename);
	}

	@Override
	public void onInvalidate() {
		super.onInvalidate();
		if (sessionDir != null && sessionDir.exists()) {
			FileUtils.deleteQuietly(sessionDir);
			sessionDir = null;
		}
	}

	public void setLastSelectedMenuItem(MenuItem menuItem) {
		lastSelectedMenuItem = menuItem;
	}

	public MenuItem getLastSelectedMenuItem() {
		return lastSelectedMenuItem;
	}

	
	public boolean authenticate(String username, String password) {
		return false;
	}

	@Override
	public Roles getRoles() {
		return null;
	}

	@Override
	public boolean isSignedIn() {
		return signedIn;
	}
	
	public boolean signIn(final String username, final String password)	{
		signedIn = authenticate(username, password);
		if (signedIn)
		{
			bind();
		}
		return signedIn;
	}

	/**
	 * Cookie based logins (remember me) may not rely on putting username and password into the
	 * cookie but something else that safely identifies the user. This method is meant to support
	 * these use cases.
	 * 
	 * It is protected (and not public) to enforce that cookie based authentication gets implemented
	 * in a subclass (like you need to implement {@link #authenticate(String, String)} for 'normal'
	 * authentication).
	 * 
	 * @see #authenticate(String, String)
	 * 
	 * @param value
	 */
	protected void signIn(boolean value)
	{
		signedIn = value;
	}

	public void signOut()
	{
		signedIn = false;
	}

	/**
	 * Call signOut() and remove the logon data from where ever they have been persisted (e.g.
	 * Cookies)
	 */
	@Override
	public void invalidate()
	{
		signOut();
		super.invalidate();
	}
}
