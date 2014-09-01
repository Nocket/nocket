package org.nocket.gen;

import gengui.WindowManagerSPI;
import gengui.guiadapter.AbstractMethodActivator;
import gengui.guiadapter.ConnectionPrototype;
import gengui.guiadapter.DisplaySyncManager.DomainObjectKey;
import gengui.guiadapter.PendingSyncOperation;
import gengui.guiadapter.SynchronizerI;
import gengui.swing.GUISessionExI;
import gengui.util.I18nPropertyBasedImpl;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.protocol.http.WebSession;

/**
 * Half-alive gengui session object which allows to use gengui's
 * internationalization functionality in a Wicket-based web application. All the
 * functions being concerned with managing strongly Swing-related internal data
 * structures are not implemented and through appropriate RuntimeExceptions.
 * 
 * @author less02
 */
public class WebGUISession implements GUISessionExI {
    public static final String NOT_SUPPORTED_MESSAGE =
            "You are working with the class WebGUISession which is not suitable for " +
                    "managing Swing-related internal data structures of gengui. When you " +
                    "are running a Wicket-based web application, make shure not to use any " +
                    "gengui functionality related to AbstractRootFrame. Otherwise use class" +
                    "LocalGUISession for Swing applications or AjaxSwingGUISessionI for " +
                    "AjaxSwing-based web applications instead!";

    @Override
    @Deprecated
    /**
     * When you are working with a generic web user interface based on Wicket,
     * you should rather use the strongly typed session object concept of Wicket
     * instead of putting name value pairs in the session with this function.
     * However, if you like so, it still works :-)
     */
    public void put(String key, Object value) {
        WebSession.get().setAttribute(key, (Serializable) value);
    }

    @Override
    @Deprecated
    /**
     * When you are working with a generic web user interface based on Wicket,
     * you should rather use the strongly typed session object concept of Wicket
     * instead of fetching name value pairs from the session with this function.
     * However, if you like so, it still works :-)
     */
    public Object get(String key) {
        return WebSession.get().getAttribute(key);
    }

    @Override
    public void putLocale(Locale locale) {
        WebSession.get().setLocale(locale);
    }

    @Override
    public Locale getLocale() {
        return (WebSession.exists()) ?
                WebSession.get().getLocale() :
                I18nPropertyBasedImpl.getStandardLocale();
    }

    // The folloing 
    @Override
    public void putGUICache(Map<Class, ConnectionPrototype> cache) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override
    public Map<Class, ConnectionPrototype> getGUICache() {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override
    public void putSyncmap(Map<DomainObjectKey, Set<SynchronizerI>> syncmap) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override
    public Map<DomainObjectKey, Set<SynchronizerI>> getSyncmap() {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override
    public void putActivatormap(Map<DomainObjectKey, Set<AbstractMethodActivator>> activatormap) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override
    public Map<DomainObjectKey, Set<AbstractMethodActivator>> getActivatormap() {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override
    public void putWindowManager(WindowManagerSPI windowManager) {
        if (windowManager != null)
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override
    public WindowManagerSPI getWindowManager() {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override
    public void putPendingSyncOperations(List<PendingSyncOperation> operations) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override
    public List<PendingSyncOperation> getPendingSyncOperations() {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override
    public void putPendingSyncOperationWorking(boolean working) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override
    public boolean getPendingSyncOperationWorking() {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

}
