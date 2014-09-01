package dmdweb.gen.page.guiservice;

import gengui.util.DomainProperties;
import gengui.util.I18NFactoryI;
import gengui.util.I18n;
import dmdweb.NocketSession;
import dmdweb.component.modal.ButtonFlag;
import dmdweb.component.modal.ModalSettings.ButtonDef;
import dmdweb.gen.test.WebGuiI18NServiceMockImpl;

public class WebGuiI18NServiceAdapter implements WebGuiI18NServiceI {

    private transient I18n i18n;
    private static volatile boolean useMock;

    // Stores the title of the upcoming message box. Usually the title-method will be called directly BEFORE the errorMessage method (or the other methods)
    private transient String translatedTitle;
    private transient ButtonDef[] buttonDefs;

    public WebGuiI18NServiceAdapter() {
    }

    public static void setUseMock(boolean useMock) {
	WebGuiI18NServiceAdapter.useMock = useMock;
    }

    @Override
    public WebGuiI18NServiceI title(String title, Object... args) {
	if (useMock) {
	    new WebGuiI18NServiceMockImpl().title(title, args);
	}
	translatedTitle = getI18n().format(title, args);
	return this;
    }

    @Override
    public WebGuiI18NServiceI buttons(ButtonDef... buttonDefs) {
	this.buttonDefs = buttonDefs;
	return this;
    }

    @Override
    public void errorMessage(String message, Object... args) {
	if (useMock) {
	    new WebGuiI18NServiceMockImpl().errorMessage(message, args);
	    return;
	}

	NocketSession session = getSession();
	if (session == null) {
	    return;
	}

	String translatedMessage = getI18n().format(message, args);
	session.getDMDWebGenGuiServiceProvider().errorMessage(translatedTitle, translatedMessage);
	translatedTitle = null;
    }

    private NocketSession getSession() {
	try {
	    return NocketSession.get();
	} catch (Throwable t) {
	    return null;
	}
    }

    private WebGuiServiceI getImpl(NocketSession session) {
	if (useMock) {
	    return new WebGuiI18NServiceMockImpl();
	} else {
	    return session.getDMDWebGenGuiServiceProvider();
	}
    }

    @Override
    public void infoMessage(String message, Object... args) {
	if (useMock) {
	    new WebGuiI18NServiceMockImpl().infoMessage(message, args);
	    return;
	}

	NocketSession session = getSession();
	if (session == null) {
	    return;
	}
	String translatedMessage = getI18n().format(message, args);
	session.getDMDWebGenGuiServiceProvider().infoMessage(translatedTitle, translatedMessage);
	translatedTitle = null;
    }

    @Override
    public void warningMessage(String message, Object... args) {
	if (useMock) {
	    new WebGuiI18NServiceMockImpl().warningMessage(message, args);
	    return;
	}

	NocketSession session = getSession();
	if (session == null) {
	    return;
	}
	String translatedMessage = getI18n().format(message, args);
	session.getDMDWebGenGuiServiceProvider().warningMessage(translatedTitle, translatedMessage);
	translatedTitle = null;
    }

    @Override
    public void confirmMessage(String message, ModalResultCallback<ButtonFlag> callback, Object... args) {
	if (useMock) {
	    new WebGuiI18NServiceMockImpl().confirmMessage(message, callback, args);
	    return;
	}

	NocketSession session = getSession();
	if (session == null) {
	    return;
	}
	String translatedMessage = getI18n().format(message, args);
	session.getDMDWebGenGuiServiceProvider().confirmMessage(translatedTitle, translatedMessage, callback,
		buttonDefs);
	translatedTitle = null;
	buttonDefs = null;
    }

    @Override
    public void status(String message, Object... args) {
	if (useMock) {
	    new WebGuiI18NServiceMockImpl().status(message, args);
	    return;
	}

	NocketSession session = getSession();
	if (session == null) {
	    return;
	}
	String translatedMessage = getI18n().format(message, args);
	session.getDMDWebGenGuiServiceProvider().status(translatedMessage);
    }

    @Override
    public void showPage(Object domainObject) {
	NocketSession session = getSession();
	if (session == null) {
	    return;
	}
	getImpl(session).showPage(domainObject);
    }

    @Override
    public void showModalPanel(Object domainObject) {
	NocketSession session = getSession();
	if (session == null) {
	    return;
	}
	getImpl(session).showModalPanel(domainObject);
    }

    @Override
    public <T> void showModalPanel(Object domainObject, boolean hideCloseButton) {
	NocketSession session = getSession();
	if (session == null) {
	    return;
	}
	getImpl(session).showModalPanel(domainObject, hideCloseButton);
    }

    @Override
    public boolean touched(String... wicketIdPrefixes) {
	NocketSession session = getSession();
	if (session == null) {
	    return false;
	}
	return getImpl(session).touched(wicketIdPrefixes);
    }

    @Override
    public void touch(String... wicketIdPrefixes) {
	NocketSession session = getSession();
	if (session == null) {
	    return;
	}
	getImpl(session).touch(wicketIdPrefixes);
    }

    @Override
    public void untouch(String... wicketIdPrefixes) {
	NocketSession session = getSession();
	if (session == null) {
	    return;
	}
	getImpl(session).untouch(wicketIdPrefixes);
    }

    @Override
    public boolean isModalPanelActive() {
	NocketSession session = getSession();
	if (session == null) {
	    return false;
	}
	return getImpl(session).isModalPanelActive();
    }

    @Override
    public void closeModalPanel() {
	NocketSession session = getSession();
	if (session == null) {
	    return;
	}
	getImpl(session).closeModalPanel();
    }

    @Override
    public String workdir() {
	NocketSession session = getSession();
	if (session == null) {
	    return null;
	}
	return getImpl(session).workdir();
    }

    public I18n getI18n() {
	if (i18n == null) {
	    i18n = new DomainProperties().getI18NFactory().getI18N(this.getClass());
	}
	return i18n;
    }
}
