package org.nocket.gen.page.element.synchronizer.error;

public interface MethodExceptionHandlerI {

    void displayError(Object domainObject, Throwable exception, String title, String message);

    void exceptionSwallowed(Object domainObject, Throwable exception);
}