package org.nocket.gen.page.guiservice;

import org.nocket.component.modal.ButtonFlag;
import org.nocket.component.modal.ModalSettings.ButtonDef;

public interface WebGuiServiceI {

    void errorMessage(String message);

    void errorMessage(String title, String message);

    void infoMessage(String message);

    void infoMessage(String title, String message);

    void warningMessage(String message);

    void warningMessage(String title, String message);

    void confirmMessage(String message, ModalResultCallback<ButtonFlag> callback, ButtonDef... buttonDefs);

    void confirmMessage(String title, String message, ModalResultCallback<ButtonFlag> callback, ButtonDef... buttonDefs);

    void status(String message);

    void showPage(Object domainObject);

    void showModalPanel(Object domainObject);

    <T> void showModalPanel(Object domainObject, boolean hideCloseButton);

    boolean isModalPanelActive();

    void closeModalPanel();

    boolean touched(String... wicketIdPrefixes);

    void touch(String... wicketIdPrefixes);

    void untouch(String... wicketIdPrefixes);

    String workdir();

    void resetModalPanelConfig();
}
