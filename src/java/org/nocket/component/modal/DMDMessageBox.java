package org.nocket.component.modal;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.model.ResourceModel;

/**
 * This class simplifies usage of MessageBox functionality based on Wicket
 * ModalWindow. You can show MessageBox as a response to an AJAX event only.
 * 
 * <pre>
 * &lt;div wicket:id="box"&gt;&lt;/div&gt;
 * ...
 * DMDMessageBox box = new DMDMessageBox("box");
 * add(box);
 * ...
 * public void onClick(AjaxRequestTarget target) {
 *   messagebox.message(target, "Example of MessageBox.", new ModalCallback() {
 *     public void doAction(AjaxRequestTarget target, ButtonFlag flag) {
 *       System.out.println("Button " + flag + " has been clicked.");     
 *     }
 *   });
 * }
 * </pre>
 * 
 * @author Albert Blazek
 * 
 */
@SuppressWarnings("serial")
public class DMDMessageBox extends ModalWindow {

    ButtonFlag clickedButton;

    private ModalSettings modalSettings;

    private ModalCallback callback;

    private Page modalPage;

    private WindowClosedCallbackInterceptor windowClosedCallbackInterceptor;

    /**
     * Constructor.
     * 
     * @param id
     */
    public DMDMessageBox(String id) {
        this(id, false);
    }

    public DMDMessageBox(String id, final boolean allowCloseButton) {
        super(id);

        setPageCreator(new ModalWindow.PageCreator() {
            public Page createPage() {
                if (modalPage != null) {
                    Page modalPageCopy = modalPage;
                    modalPage = null;
                    return modalPageCopy;
                } else {
                    return newModalContentPage(DMDMessageBox.this);
                }
            }

        });

        setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            public void onClose(AjaxRequestTarget target) {
                boolean hookUsed = false;
                if (windowClosedCallbackInterceptor != null) {
                    hookUsed = windowClosedCallbackInterceptor.onClose(target);
                }
                if (callback != null && !hookUsed) {
                    callback.doAction(target, clickedButton);
                }
            }
        });

        setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
            public boolean onCloseButtonClicked(AjaxRequestTarget target) {
                return allowCloseButton;
            }
        });

    }

    public void setWindowClosedCallbackInterceptor(WindowClosedCallbackInterceptor windowClosedCallbackInterceptor) {
        this.windowClosedCallbackInterceptor = windowClosedCallbackInterceptor;
    }

    protected ModalContentPage newModalContentPage(DMDMessageBox dmdMessageBox) {
        return new ModalContentPage(dmdMessageBox);
    }

    ModalCallback getCallback() {
        return callback;
    }

    void setCallback(ModalCallback callback) {
        if (callback == null)
            throw new IllegalArgumentException("ModalCallback cannot be null!");
        this.callback = callback;
    }

    void setModalSettings(ModalSettings ms) {
        this.modalSettings = ms;
        setModalPageSettings(ms);
    }

    void setModalPageSettings(ModalPageSettings ms) {
        if (ms == null) {
            throw new IllegalStateException("ModalSettings of DMDMessageBox cannot be null!");
        }
        this.setTitle(ms.getTitle());
        setResizable(ms.isResizable());
        if (ms.getInitialWidth() != null) {
            setInitialWidth(ms.getInitialWidth());
        }
        if (ms.getMinimalWidth() != null) {
            setMinimalWidth(ms.getMinimalWidth());
        }
        if (ms.getInitialHeight() != null) {
            setInitialHeight(ms.getInitialHeight());
        }
        if (ms.getMinimalHeight() != null) {
            setMinimalHeight(ms.getMinimalHeight());
        }
    }

    public ModalSettings getModalSettings() {
        return modalSettings;
    }

    /**
     * @return the flag
     */
    public ButtonFlag getClickedButton() {
        return clickedButton;
    }

    /**
     * Opens MessageBox with specified {@link ModalSettings}.
     * 
     * @param target
     *            target from calling ajax event
     * @param modalSettings
     *            modal settings for the window
     * @param callback
     * 
     */
    public void showModal(final AjaxRequestTarget target, ModalSettings modalSettings, ModalCallback callback) {
        setModalSettings(modalSettings);
        setCallback(callback);
        super.show(target);
    }

    public void showModalPage(AjaxRequestTarget target, Page modalPage, String title) {
        showModalPage(target, modalPage, newModalSettings(title, null, new ButtonFlag[] {}));
    }

    public void showModalPage(AjaxRequestTarget target, Page modalPage, ModalPageSettings modalSettings) {
        this.modalPage = modalPage;
        setModalPageSettings(modalSettings);
        super.show(target);
    }

    /**
     * Opens message box with title "Info" and "OK" button.
     * 
     * @param target
     *            target from calling ajax event
     * @param text
     *            text to be shown
     */
    public void showInfo(final AjaxRequestTarget target, String text) {
        showInfo(target, text, newDefaultModalCallback());
    }

    public void showInfo(final AjaxRequestTarget target, String title, String text) {
        showInfo(target, title, text, newDefaultModalCallback());
    }

    /**
     * Opens message box with a "OK" button.
     * 
     * @param target
     *            target from calling ajax event
     * @param text
     *            text to be shown
     * @param callback
     *            instance of {@link ModalCallback} to perform action after
     *            MessageBox close
     */
    public void showInfo(final AjaxRequestTarget target, String text, ModalCallback callback) {
        showInfo(target, null, text, callback);
    }

    public void showInfo(final AjaxRequestTarget target, String title, String text, ModalCallback callback) {
        showModal(target, newModalSettings(createTitleStr("info.title", title), text, ButtonFlag.OK), callback);
    }

    /**
     * Opens message box with title "Warning" and "OK" button.
     * 
     * @param target
     *            target from calling ajax event
     * @param text
     *            text to be shown
     */
    public void showWarning(final AjaxRequestTarget target, String text) {
        showWarning(target, text, newDefaultModalCallback());
    }

    public void showWarning(final AjaxRequestTarget target, String title, String text) {
        showWarning(target, title, text, newDefaultModalCallback());
    }

    /**
     * Opens message box with title "Warning" and "OK" button.
     * 
     * @param target
     *            target from calling ajax event
     * @param text
     *            text to be shown
     * @param callback
     *            instance of {@link ModalCallback} to perform action after
     *            MessageBox close
     */
    public void showWarning(final AjaxRequestTarget target, String text, ModalCallback callback) {
        showWarning(target, null, text, callback);
    }

    public void showWarning(final AjaxRequestTarget target, String title, String text, ModalCallback callback) {
        showModal(target, newModalSettings(createTitleStr("warning.title", title), text, ButtonFlag.OK), callback);
    }

    /**
     * Opens message box with title "Error" and "OK" button.
     * 
     * @param target
     *            target from calling ajax event
     * @param text
     *            text to be shown
     */
    public void showError(final AjaxRequestTarget target, String text) {
        showError(target, text, newDefaultModalCallback());
    }

    public void showError(final AjaxRequestTarget target, String title, String text) {
        showError(target, title, text, newDefaultModalCallback());
    }

    /**
     * Opens message box with title "Error" and "OK" button.
     * 
     * @param target
     *            target from calling ajax event
     * @param text
     *            text to be shown
     * @param callback
     *            instance of {@link ModalCallback} to perform action after
     *            MessageBox close
     */
    public void showError(final AjaxRequestTarget target, String text, ModalCallback callback) {
        showError(target, null, text, callback);
    }

    public void showError(final AjaxRequestTarget target, String title, String text, ModalCallback callback) {
        showModal(target, newModalSettings(createTitleStr("error.title", title), text, ButtonFlag.OK), callback);
    }

    /**
     * Opens confirmation box with "Yes" and "No" buttons.
     * 
     * @param target
     *            target from calling ajax event
     * @param msg
     *            text to be shown
     * @param callback
     *            instance of {@link ModalCallback}
     */
    public void showConfirm(final AjaxRequestTarget target, String msg, ModalCallback callback) {
        showConfirm(target, null, msg, callback);
    }

    public void showConfirm(AjaxRequestTarget target, String title, String msg, ModalCallback callback) {
        showModal(target, newModalSettings(createTitleStr("confirm.title", title), msg, ButtonFlag.YES, ButtonFlag.NO),
                callback);
    }

    private String createTitleStr(String defaultTitleKey, String title) {
        String titleStr = new ResourceModel(defaultTitleKey).wrapOnAssignment(this).getObject();
        if (StringUtils.isNotBlank(title)) {
            titleStr += ": " + title;
        }
        return titleStr;
    }

    /**
     * Factory method to create {@link ModalSettings}. You can override it to
     * have i.e. other window dimensions.
     * 
     * @param title
     *            window title
     * @param msg
     *            message to be shown
     * @param buttons
     *            buttons to be shown
     * 
     * @return Instance of {@link ModalSettings}
     */
    protected ModalSettings newModalSettings(String title, String msg, ButtonFlag... buttons) {
        ModalSettings res = new ModalSettings(title, msg, buttons);
        return res;
    }

    /**
     * @return Default ModalCallback handler the does nothing
     */
    private ModalCallback newDefaultModalCallback() {
        return new ModalCallback() {
            @Override
            public boolean doAction(AjaxRequestTarget target, ButtonFlag flag) {
                return true;
            }
        };
    }

}
