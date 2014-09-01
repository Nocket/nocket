package dmdweb.gen.page.element.synchronizer;

import gengui.annotations.Modal;
import gengui.guiadapter.AbstractMethodActivator;

import java.io.Serializable;

import org.apache.commons.lang.BooleanUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;

import de.bertelsmann.coins.general.error.Assert;
import de.bertelsmann.coins.general.error.AssertionException;
import dmdweb.NocketSession;
import dmdweb.gen.page.element.ButtonElement;
import dmdweb.gen.page.element.synchronizer.error.MethodExceptionHandlerI;
import dmdweb.gen.page.guiservice.DMDWebGenGuiServiceProvider;

@SuppressWarnings("serial")
public class ButtonCallback implements Serializable {

    private final SynchronizerHelper helper;
    private boolean isCloser;
    private ButtonCallbackInterceptor interceptor;

    public ButtonCallback(ButtonElement element, ButtonCallbackInterceptor interceptor) {
        this.interceptor = interceptor != null ? interceptor : new ButtonCallbackInterceptor();

        this.helper = new SynchronizerHelper(element);
        //Performance improvement: The calls of AbstractMethodActivator.isCloserMethod take
        // a mentionable amount of time on mask construction (about 16% in production mode,
        // measured on 27.03.2013). As most masks are pages which can't be closed in dmdweb,
        // we omit the call in that case.
        //ATTENTION: Same improvement applies in TableButtonCallback
        Boolean isCloserMethod = (element.getContext().isPage()) ? Boolean.FALSE :
                AbstractMethodActivator.isCloserMethod(helper.getRef(), helper.getButtonMethod());

        //TODO: according to gengui a method which is not clearly detected as being a closer
        // derives its closing behavior from the start of the modal operation.
        // This is not yet supported by web gengui. However, as closers are currently only
        // supported for modal dialogs at all, the behavior simply defaults to true
        isCloser = !BooleanUtils.isFalse(isCloserMethod);
    }

    public ButtonCallback(ButtonElement element) {
        this(element, null);
    }

    public void updateAllForms(AjaxRequestTarget target) {
        SynchronizerHelper.updateAllFormsFromPage(helper.getContext(), target);
    }

    public void onSubmit(AjaxRequestTarget target) {
        interceptor.setButtonCallback(this);
        interceptor.onSubmit(target);
    }

    public boolean isForced() {
        return helper.isForced();
    }

    public class MethodExceptionHandlerImpl implements MethodExceptionHandlerI, Serializable {
        private boolean exceptionOccured;

        private MethodExceptionHandlerImpl() {
        }

        @Override
        public void displayError(Object domainObject, Throwable exception, String title, String message) {
            exceptionOccured = true;
            helper.getContext().getMethodExceptionHandler().displayError(domainObject, exception, title, message);
        }

        @Override
        public void exceptionSwallowed(Object domainObject, Throwable exception) {
            exceptionOccured = true;
            helper.getContext().getMethodExceptionHandler().exceptionSwallowed(domainObject, exception);
        }

        public boolean isExceptionOccured() {
            return exceptionOccured;
        }
    }

    /**
     * Interceptor für ButtonCallbacks um in den onSubmit-Process eingreifen zu
     * können. Dabei gibt es mehrere Punkte an denen man eingreifen kann. Wird
     * ein Button gedrückt, wird im Interceptor die Method
     * 
     * <pre>
     * <code>        protected void onSubmit(AjaxRequestTarget target) {
     *             try {
     *                 MethodExceptionHandlerImpl methodExceptionHandler = onSubmitPre(target);
     *                 Object result = onSubmitProcess(methodExceptionHandler);
     *                 onSubmitPost(methodExceptionHandler, result);
     *             } finally {
     *                 onSubmitFinally(target);
     *             }
     *         }
     * </code>
     * </pre>
     * 
     * aufgerufen. Darin wird der Prozess zum Registrieren des AjaxTargets
     * <code>MethodExceptionHandlerImpl onSubmitPre(AjaxRequestTarget target)</code>
     * , das Aufrufen der Button-Methode im Pojo
     * <code>Object onSubmitProcess(MethodExceptionHandlerImpl methodExceptionHandler)</code>
     * , das Verarbeiten von Exceptions bzw das Schliessen von Dialogen
     * <code>onSubmitPost(MethodExceptionHandlerImpl methodExceptionHandler, Object result)</code>
     * und schließlich das Deregistrieren des AjaxTargets
     * <code>onSubmitFinally(AjaxRequestTarget target)</code> durchgeführt.<br>
     * Alle diese Methode können überschrieben werden. Wird eine Methode nicht
     * überschrieben, wird die entsprechende Methode am ButtonCallback
     * ausgeführt. In den meisten Fällen wird die Methode
     * <code>Object onSubmitProcess(MethodExceptionHandlerImpl methodExceptionHandler)</code>
     * überschrieben werden. Gründe dafür könnten sein, es soll vor oder nach
     * dem Aufruf der Pojo-Button-Methode noch eine Funktion durchgeführt
     * werden.<br>
     * Ein Beispiel ist in @see GeneratedPage
     * 
     * 
     * @author meis026
     * 
     */
    public static class ButtonCallbackInterceptor implements Serializable {

        private ButtonCallback buttonCallback;

        void setButtonCallback(ButtonCallback buttonCallback) {
            this.buttonCallback = buttonCallback;
        }

        protected void onSubmit(AjaxRequestTarget target) {
            try {
                MethodExceptionHandlerImpl methodExceptionHandler = onSubmitPre(target);
                Object result = onSubmitProcess(methodExceptionHandler);
                onSubmitPost(methodExceptionHandler, result);
            } finally {
                onSubmitFinally(target);
            }
        }

        protected void onSubmitFinally(AjaxRequestTarget target) {
            DMDWebGenGuiServiceProvider webGuiServiceProvider = NocketSession.get().getDMDWebGenGuiServiceProvider();
            try {
                webGuiServiceProvider.unregisterAjaxRequestTarget(buttonCallback.helper.getContext(), target);
            } catch (AssertionException e) {
                String text = "WicketId = " + buttonCallback.helper.getWicketId() + " - ButtonMethod = "
                        + buttonCallback.helper.getButtonMethod().getName();
                throw new RuntimeException(text + " --- " + e.getMessage(), e);
            }
        }

        protected Object onSubmitProcess(MethodExceptionHandlerImpl methodExceptionHandler) {
            Object result = buttonCallback.helper.invokeButtonMethod(methodExceptionHandler);
            return result;
        }

        protected void onSubmitPost(MethodExceptionHandlerImpl methodExceptionHandler, Object result) {
            DMDWebGenGuiServiceProvider webGuiServiceProvider = NocketSession.get().getDMDWebGenGuiServiceProvider();

            if (methodExceptionHandler.isExceptionOccured()) {
                return;
            }

            boolean hasModalAnnotation = buttonCallback.helper.getAnnotationOnButtonMethode(Modal.class) != null;
            Assert.test(!hasModalAnnotation || !webGuiServiceProvider.isModalPanelActive(),
                    "One generic modal dialog over another is still not implemented. Button name = "
                            + this.buttonCallback.helper.getButtonMethod().getName());

            boolean modalPanelActive = webGuiServiceProvider.isModalPanelActive();

            if (!modalPanelActive && !hasModalAnnotation) {
                webGuiServiceProvider.showPage(result);

            } else if (buttonCallback.isCloser && modalPanelActive) {
                webGuiServiceProvider.closeModalPanel();
                webGuiServiceProvider.showPage(result);
            } else {
                // if it is not a closer but a result of null, do nothing
                if (result != null) {
                    /*
                     * While showing a modal dialog wicket will do no
                     * serialization of the models. So, it is possible to put a
                     * reference of data from the page to the dialog and let the
                     * dialog change it.
                     */
                    webGuiServiceProvider.showModalPanel(result);
                    if (modalPanelActive) {
                        webGuiServiceProvider.closeModalPanel();
                    }
                }
            }
        }

        protected MethodExceptionHandlerImpl onSubmitPre(AjaxRequestTarget target) {
            DMDWebGenGuiServiceProvider webGuiServiceProvider = NocketSession.get().getDMDWebGenGuiServiceProvider();
            webGuiServiceProvider.registerAjaxRequestTarget(buttonCallback.helper.getContext(), target);
            return buttonCallback.new MethodExceptionHandlerImpl();
        }

    }
}
