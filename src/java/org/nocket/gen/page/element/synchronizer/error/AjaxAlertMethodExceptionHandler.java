package org.nocket.gen.page.element.synchronizer.error;

import org.nocket.gen.page.guiservice.WebGuiServiceAdapter;

public class AjaxAlertMethodExceptionHandler implements MethodExceptionHandlerI {

    @Override
    public void displayError(Object domainObject, Throwable exception, String title, String message) {
        new WebGuiServiceAdapter().errorMessage(title, message);
    }

    @Override
    public void exceptionSwallowed(Object domainObject, Throwable exception) {
        //do nothing
    }
}
