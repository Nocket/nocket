package dmdweb.gen.page.guiservice;

import dmdweb.component.modal.ButtonFlag;
import dmdweb.component.modal.ModalSettings.ButtonDef;

public interface WebGuiI18NServiceI {

    WebGuiI18NServiceI title(String title, Object... args);

    WebGuiI18NServiceI buttons(ButtonDef... buttonDef);

    void errorMessage(String message, Object... args);

    void infoMessage(String message, Object... args);

    void warningMessage(String message, Object... args);

    void confirmMessage(String message, ModalResultCallback<ButtonFlag> callback, Object... args);

    void status(String message, Object... args);

    void showPage(Object domainObject);

    void showModalPanel(Object domainObject);

    <T> void showModalPanel(Object domainObject, boolean hideCloseButton);

    boolean isModalPanelActive();

    void closeModalPanel();

    boolean touched(String... wicketIdPrefixes);

    void touch(String... wicketIdPrefixes);

    void untouch(String... wicketIdPrefixes);

    String workdir();
}
