package org.nocket.gen.test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.nocket.component.modal.ButtonFlag;
import org.nocket.component.modal.ModalSettings.ButtonDef;
import org.nocket.gen.page.guiservice.ModalResultCallback;
import org.nocket.gen.page.guiservice.WebGuiI18NServiceI;
import org.nocket.gen.page.guiservice.WebGuiServiceI;

import de.bertelsmann.coins.general.error.AssertionException;

public class WebGuiI18NServiceMockImpl implements WebGuiI18NServiceI, WebGuiServiceI {

    private static Stack<GUIServiceOperation> mockresults = new Stack<GUIServiceOperation>();

    private static Queue<ButtonFlag> confirmResults = new LinkedList<ButtonFlag>();

    private static Queue<Object> modalResults = new LinkedList<Object>();

    private static Object modalObject = null;

    public static boolean TOUCHED_OVERRIDE = false;

    public static enum Operation {
        CONFIRM,
        MODAL,
        STATUS,
        TOUCHED,
        UNTOUCH,
        TOUCH,
        ERROR_MESSAGE,
        INFO_MESSAGE,
        WARNING_MESSAGE,
        SHOW_PAGE,
        IS_MODAL_PANEL_ACTIVE,
        CLOSE_MODAL_PANEL;
    }

    public static class GUIServiceOperation {
        public Operation operation;
        public Object[] params;
        public Object[] titleParams;
        public Object[] buttonParams;

        public GUIServiceOperation(Operation operation, Object... params) {
            super();
            this.operation = operation;
            this.params = params;
            this.titleParams = null;
            this.buttonParams = null;
        }

        public GUIServiceOperation(Operation operation, Object[] titleParams, Object[] buttonParams, Object... params) {
            super();
            this.operation = operation;
            this.params = params;
            this.titleParams = titleParams;
            this.buttonParams = buttonParams;
        }

        @Override
        public String toString() {
            return String.format("%s(%s)", operation, StringUtils.join(params, ", "));
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((operation == null) ? 0 : operation.hashCode());
            result = prime * result + Arrays.hashCode(params);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            GUIServiceOperation other = (GUIServiceOperation) obj;
            if (operation == null) {
                if (other.operation != null) {
                    return false;
                }
            } else if (operation == other.operation) {
                return false;
            }
            if (!Arrays.equals(params, other.params)) {
                return false;
            }
            return true;
        }
    }

    protected static void addCall(Operation op, Object... params) {
        mockresults.push(new GUIServiceOperation(op, params));
    }

    protected static void addCallWithTitle(Operation op, Object... params) {
        mockresults.push(new GUIServiceOperation(op, titleParams, buttonParams, params));
    }

    public static void clear() {
        mockresults = new Stack<GUIServiceOperation>();
        confirmResults.clear();
        modalResults.clear();
        modalObject = null;
    }

    /** Setzt das gewollte Ergebnis für eine modale Operation */
    public static Object getModalObject() {
        return modalObject;
    }

    /** Liefert alle Operationen, die auf diesem Mock aufgerufen wurden, */
    public static Stack<GUIServiceOperation> getMockResults() {
        return mockresults;
    }

    public static GUIServiceOperation getLastCalledOperation() {
        return getMockResults().peek();
    }

    public static GUIServiceOperation popLastCalledOperation() {
        return getMockResults().pop();
    }

    /** Setzt das gewollte ConfirmResult für confirm Operation */
    public static void addConfirmResult(ButtonFlag confirmResult) {
        WebGuiI18NServiceMockImpl.confirmResults.offer(confirmResult);
    }

    /** Setzt das gewollte Result für modal Operation */
    public static void addModalResult(Object modalResult) {
        WebGuiI18NServiceMockImpl.modalResults.offer(modalResult);
    }

    private static Object[] titleParams;

    private static Object[] buttonParams;

    @Override
    public void errorMessage(String message, Object... args) {
        addCallWithTitle(Operation.ERROR_MESSAGE, addArgs(message, args));
    }

    @Override
    public void infoMessage(String message, Object... args) {
        addCallWithTitle(Operation.INFO_MESSAGE, addArgs(message, args));
    }

    @Override
    public void warningMessage(String message, Object... args) {
        addCallWithTitle(Operation.WARNING_MESSAGE, addArgs(message, args));
    }

    @Override
    public void confirmMessage(String message, ModalResultCallback<ButtonFlag> callback, Object... args) {
        addCallWithTitle(Operation.CONFIRM, addArgs(addArgs(message, callback), args));
        callback.onResult(confirmResults.poll());
    }

    @Override
    public void status(String message, Object... args) {
        addCall(Operation.STATUS, addArgs(message, args));
    }

    @Override
    public void showPage(Object domainObject) {
        addCall(Operation.SHOW_PAGE, domainObject);
    }

    @Override
    public void showModalPanel(Object domainObject) {
        addCall(Operation.MODAL, domainObject);
    }

    @Override
    public <T> void showModalPanel(Object domainObject, boolean hideCloseButton) {
        addCall(Operation.MODAL, domainObject, hideCloseButton);
    }

    @Override
    public boolean isModalPanelActive() {
        addCall(Operation.IS_MODAL_PANEL_ACTIVE);
        return false;
    }

    @Override
    public void closeModalPanel() {
        addCall(Operation.CLOSE_MODAL_PANEL);
    }

    @Override
    public boolean touched(String... wicketIdPrefixes) {
        addCall(Operation.TOUCHED, wicketIdPrefixes);
        return TOUCHED_OVERRIDE;
    }

    @Override
    public void touch(String... wicketIdPrefixes) {
        addCall(Operation.TOUCH, wicketIdPrefixes);
    }

    @Override
    public void untouch(String... wicketIdPrefixes) {
        addCall(Operation.UNTOUCH, wicketIdPrefixes);
    }

    @Override
    public String workdir() {
        return ".";
    }

    @Override
    public WebGuiI18NServiceI title(String title, Object... args) {
        WebGuiI18NServiceMockImpl.titleParams = addArgs(title, args);
        return this;
    }

    @Override
    public WebGuiI18NServiceI buttons(ButtonDef... buttonDef) {
        WebGuiI18NServiceMockImpl.buttonParams = addArgs(buttonDef);
        return this;
    }

    private Object[] addArgs(Object object, Object... args) {
        return ArrayUtils.add(args, 0, object);
    }

    private Object[] addArgs(Object[] args1, Object... args2) {
        return ArrayUtils.addAll(args1, args2);
    }

    @Override
    public void errorMessage(String message) {
        // This method exists because of the necessary interface WebGuiServiceI, which has to be used by this mock and only by this mock. 
        // The WebGuiI18NServiceImpl doesn't have this interface. So this method mustn't be called.
        throw new AssertionException("This method shouldn't be called in an I18N enviroment.");
    }

    @Override
    public void errorMessage(String title, String message) {
        // This method exists because of the necessary interface WebGuiServiceI, which has to be used by this mock and only by this mock. 
        // The WebGuiI18NServiceImpl doesn't have this interface. So this method mustn't be called.
        throw new AssertionException("This method shouldn't be called in an I18N enviroment.");
    }

    @Override
    public void infoMessage(String message) {
        // This method exists because of the necessary interface WebGuiServiceI, which has to be used by this mock and only by this mock. 
        // The WebGuiI18NServiceImpl doesn't have this interface. So this method mustn't be called.
        throw new AssertionException("This method shouldn't be called in an I18N enviroment.");
    }

    @Override
    public void infoMessage(String title, String message) {
        // This method exists because of the necessary interface WebGuiServiceI, which has to be used by this mock and only by this mock. 
        // The WebGuiI18NServiceImpl doesn't have this interface. So this method mustn't be called.
        throw new AssertionException("This method shouldn't be called in an I18N enviroment.");
    }

    @Override
    public void warningMessage(String message) {
        // This method exists because of the necessary interface WebGuiServiceI, which has to be used by this mock and only by this mock. 
        // The WebGuiI18NServiceImpl doesn't have this interface. So this method mustn't be called.
        throw new AssertionException("This method shouldn't be called in an I18N enviroment.");
    }

    @Override
    public void warningMessage(String title, String message) {
        // This method exists because of the necessary interface WebGuiServiceI, which has to be used by this mock and only by this mock. 
        // The WebGuiI18NServiceImpl doesn't have this interface. So this method mustn't be called.
        throw new AssertionException("This method shouldn't be called in an I18N enviroment.");
    }

    @Override
    public void confirmMessage(String message, ModalResultCallback<ButtonFlag> callback, ButtonDef... buttonDefs) {
        // This method exists because of the necessary interface WebGuiServiceI, which has to be used by this mock and only by this mock. 
        // The WebGuiI18NServiceImpl doesn't have this interface. So this method mustn't be called.
        throw new AssertionException("This method shouldn't be called in an I18N enviroment.");
    }

    @Override
    public void confirmMessage(String title, String message, ModalResultCallback<ButtonFlag> callback,
            ButtonDef... buttonDefs) {
        // This method exists because of the necessary interface WebGuiServiceI, which has to be used by this mock and only by this mock. 
        // The WebGuiI18NServiceImpl doesn't have this interface. So this method mustn't be called.
        throw new AssertionException("This method shouldn't be called in an I18N enviroment.");
    }

    @Override
    public void status(String message) {
        // This method exists because of the necessary interface WebGuiServiceI, which has to be used by this mock and only by this mock. 
        // The WebGuiI18NServiceImpl doesn't have this interface. So this method mustn't be called.
        throw new AssertionException("This method shouldn't be called in an I18N enviroment.");
    }

    @Override
    public void resetModalPanelConfig() {
        throw new AssertionException("This method shouldn't be called in an I18N enviroment.");
    }
}
