package dmdweb.gen.test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;

import dmdweb.component.modal.ButtonFlag;
import dmdweb.component.modal.ModalSettings.ButtonDef;
import dmdweb.gen.page.guiservice.ModalResultCallback;
import dmdweb.gen.page.guiservice.WebGuiServiceI;

public class WebGuiServiceMockImpl implements WebGuiServiceI {

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
        CLOSE_MODAL_PANEL,
        RESET_MODEL_PANEL_CONFIG;
    }

    public static class GUIServiceOperation {
        public Operation operation;
        public Object[] params;

        public GUIServiceOperation(Operation operation, Object... params) {
            super();
            this.operation = operation;
            this.params = params;
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
        WebGuiServiceMockImpl.confirmResults.offer(confirmResult);
    }

    /** Setzt das gewollte Result für modal Operation */
    public static void addModalResult(Object modalResult) {
        WebGuiServiceMockImpl.modalResults.offer(modalResult);
    }

    @Override
    public void errorMessage(String message) {
        addCall(Operation.ERROR_MESSAGE, message);
    }

    @Override
    public void errorMessage(String title, String message) {
        addCall(Operation.ERROR_MESSAGE, title, message);
    }

    @Override
    public void infoMessage(String message) {
        addCall(Operation.INFO_MESSAGE, message);
    }

    @Override
    public void infoMessage(String title, String message) {
        addCall(Operation.INFO_MESSAGE, title, message);
    }

    @Override
    public void warningMessage(String message) {
        addCall(Operation.WARNING_MESSAGE, message);
    }

    @Override
    public void warningMessage(String title, String message) {
        addCall(Operation.WARNING_MESSAGE, title, message);
    }

    @Override
    public void confirmMessage(String message, ModalResultCallback<ButtonFlag> callback, ButtonDef... buttonDefs) {
        addCall(Operation.CONFIRM, message, callback, buttonDefs);
        callback.onResult(confirmResults.poll());
    }

    @Override
    public void confirmMessage(String title, String message, ModalResultCallback<ButtonFlag> callback,
            ButtonDef... buttonDefs) {
        addCall(Operation.CONFIRM, title, message, callback, buttonDefs);
        callback.onResult(confirmResults.poll());
    }

    @Override
    public void status(String message) {
        addCall(Operation.STATUS, message);
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
    public void resetModalPanelConfig() {
        addCall(Operation.RESET_MODEL_PANEL_CONFIG);

    }
}
