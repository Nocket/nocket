package dmdweb.gen.page.guiservice;

import dmdweb.NocketSession;
import dmdweb.component.modal.ButtonFlag;
import dmdweb.component.modal.ModalSettings.ButtonDef;
import dmdweb.gen.test.WebGuiServiceMockImpl;

public class WebGuiServiceAdapter implements WebGuiServiceI {

    private static volatile boolean useMock;

    public static void setUseMock(boolean useMock) {
        WebGuiServiceAdapter.useMock = useMock;
    }

    @Override
    public void errorMessage(String message) {
        NocketSession session = getSession();
        if (session == null) {
            return;
        }
        getImpl(session).errorMessage(message);
    }

    private NocketSession getSession() {
        try {
            return NocketSession.get();
        } catch (Throwable t) {
            return null;
        }
    }

    @Override
    public void errorMessage(String title, String message) {
        NocketSession session = getSession();
        if (session == null) {
            return;
        }
        getImpl(session).errorMessage(title, message);
    }

    private WebGuiServiceI getImpl(NocketSession session) {
        if (useMock) {
            return new WebGuiServiceMockImpl();
        } else {
            return session.getDMDWebGenGuiServiceProvider();
        }
    }

    @Override
    public void infoMessage(String message) {
        NocketSession session = getSession();
        if (session == null) {
            return;
        }
        getImpl(session).infoMessage(message);
    }

    @Override
    public void infoMessage(String title, String message) {
        NocketSession session = getSession();
        if (session == null) {
            return;
        }
        getImpl(session).infoMessage(title, message);
    }

    @Override
    public void warningMessage(String message) {
        NocketSession session = getSession();
        if (session == null) {
            return;
        }
        getImpl(session).warningMessage(message);
    }

    @Override
    public void warningMessage(String title, String message) {
        NocketSession session = getSession();
        if (session == null) {
            return;
        }
        getImpl(session).warningMessage(title, message);
    }

    @Override
    public void confirmMessage(String message, ModalResultCallback<ButtonFlag> callback, ButtonDef... buttonDefs) {
        NocketSession session = getSession();
        if (session == null) {
            return;
        }
        getImpl(session).confirmMessage(message, callback, buttonDefs);
    }

    @Override
    public void confirmMessage(String title, String message, ModalResultCallback<ButtonFlag> callback,
            ButtonDef... buttonDefs) {
        NocketSession session = getSession();
        if (session == null) {
            return;
        }
        getImpl(session).confirmMessage(title, message, callback, buttonDefs);
    }

    @Override
    public void status(String message) {
        NocketSession session = getSession();
        if (session == null) {
            return;
        }
        getImpl(session).status(message);
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

    @Override
    public void resetModalPanelConfig() {
        NocketSession session = getSession();
        if (session == null) {
            return;
        }
        getImpl(session).resetModalPanelConfig();
    }
}
