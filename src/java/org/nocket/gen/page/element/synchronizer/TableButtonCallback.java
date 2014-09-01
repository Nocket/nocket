package org.nocket.gen.page.element.synchronizer;

import gengui.annotations.Modal;
import gengui.annotations.Prompt;
import gengui.domain.DomainObjectReference;
import gengui.guiadapter.AbstractMethodActivator;
import gengui.util.AnnotationHelper;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.MissingResourceException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.nocket.NocketSession;
import org.nocket.gen.domain.element.MultivalueButtonElement;
import org.nocket.gen.i18n.I18NLabelModel;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.FormElement;
import org.nocket.gen.page.element.synchronizer.error.MethodExceptionHandlerI;
import org.nocket.gen.page.guiservice.DMDWebGenGuiServiceProvider;

import de.bertelsmann.coins.general.error.Assert;

public class TableButtonCallback extends TableCallback implements Serializable {

    private static final long serialVersionUID = 1L;

    private final boolean isRemover;
    private final boolean isCloser;

    private static final String ICON_ENABLED = "{0}.icon.enabled";
    private static final String ICON_DISABLED = "{0}.icon.disabled";

    public TableButtonCallback(DMDWebGenPageContext context, MultivalueButtonElement<DomainObjectReference> element) {
        super(context, element);
        this.isRemover = element.isRemover();

        //Same performance improvement as in ButtonCallback's constructor. See details there!
        Boolean isCloserMethod = context.isPage() ? Boolean.FALSE :
                AbstractMethodActivator.isCloserMethod(helper.getRef(), helper.getButtonMethod());

        // TODO: according to gengui a method which is not clearly detected as being a closer
        // derives its closing behavior from the start of the modal operation.
        // This is not yet supported by web gengui. However, as closers are currently only
        // supported for modal dialogs at all, the behavior simply defaults to true
        isCloser = !BooleanUtils.isFalse(isCloserMethod);
    }

    public String getWicketId() {
        return propertiesWicketId;
    }

    public String getName() {
        if (isRemover) {
            return helper.getRemoverButtonMethod().getName();
        } else {
            return helper.getButtonMethod().getName();
        }
    }

    public String getPrompt() {
        return prompt;
    }

    public boolean isEnabled(Object targetObject) {
        if (isRemover) {
            return helper.isEnabled();
        } else {
            try {
                helper.setForcedMethodTargetObject(targetObject);
                return helper.isEnabled();
            } finally {
                helper.setForcedMethodTargetObject(null);
            }
        }
    }

    public boolean isForced(Object targetObject) {
        if (isRemover) {
            return helper.isForced();
        } else {
            try {
                helper.setForcedMethodTargetObject(targetObject);
                return helper.isForced();
            } finally {
                helper.setForcedMethodTargetObject(null);
            }
        }
    }

    public void onSubmit(AjaxRequestTarget target, final IModel model) {
        DMDWebGenGuiServiceProvider webGuiServiceProvider = NocketSession.get().getDMDWebGenGuiServiceProvider();
        try {
            webGuiServiceProvider.registerAjaxRequestTarget(helper.getContext(), target);
            if (isRemover) {
                helper.invokeRemoverButtonMethod(newMethodExceptionHanlder(), model.getObject());
            } else {
                try {
                    helper.setForcedMethodTargetObject(model.getObject());
                    Object result = helper.invokeButtonMethod(newMethodExceptionHanlder());
                    boolean hasModalAnnotation = helper.getAnnotationOnButtonMethode(Modal.class) != null;
                    Assert.test(!hasModalAnnotation || !webGuiServiceProvider.isModalPanelActive(),
                            "One generic modal dialog over another is still not implemented. Button name = "
                                    + this.helper.getButtonMethod().getName());

                    boolean modalPanelActive = webGuiServiceProvider.isModalPanelActive();

                    if (!modalPanelActive && !hasModalAnnotation) {
                        webGuiServiceProvider.showPage(result);

                    } else if (isCloser) {
                        Assert.test(isCloser && webGuiServiceProvider.isModalPanelActive(),
                                "Found a Closer, but there is no active modal panel. Button name = "
                                        + this.helper.getButtonMethod().getName());

                        webGuiServiceProvider.closeModalPanel();
                        webGuiServiceProvider.showPage(result);
                    } else {
                        // if it is not a closer but a result of null, do nothing
                        if (result != null) {
                            /*
                             * While showing a modal dialog wicket will do no
                             * serialization of the models. So, it is possible
                             * to put a reference of data from the page to the
                             * dialog and let the dialog change it.
                             */
                            webGuiServiceProvider.showModalPanel(result);
                            if (modalPanelActive) {
                                webGuiServiceProvider.closeModalPanel();
                            }
                        }
                    }
                } finally {
                    helper.setForcedMethodTargetObject(null);
                }
            }
        } finally {
            webGuiServiceProvider.unregisterAjaxRequestTarget(helper.getContext(), target);
        }
    }

    private MethodExceptionHandlerI newMethodExceptionHanlder() {
        final MutableBoolean exceptionOccured = new MutableBoolean(false);
        return new MethodExceptionHandlerI() {
            @Override
            public void displayError(Object domainObject, Throwable exception, String title, String message) {
                exceptionOccured.setValue(true);
                helper.getContext().getMethodExceptionHandler()
                        .displayError(domainObject, exception, title, message);
            }

            @Override
            public void exceptionSwallowed(Object domainObject, Throwable exception) {
                exceptionOccured.setValue(true);
                helper.getContext().getMethodExceptionHandler().exceptionSwallowed(domainObject, exception);
            }
        };
    }

    public String getTooltip(Object targetObject) {
        if (isRemover) {
            return helper.getButtonTooltip();
        } else {
            try {
                helper.setForcedMethodTargetObject(targetObject);
                return helper.getButtonTooltip();
            } finally {
                helper.setForcedMethodTargetObject(null);
            }
        }
    }

    public void updateAllForms(AjaxRequestTarget target) {
        helper.updateAllForms(target);
    }

    public Form<?> getForm() {
        return (Form<?>) helper.getContext().getComponentRegistry().getComponent(FormElement.DEFAULT_WICKET_ID);
    }

    public Method getMethod() {
        return helper.getButtonMethod();
    }

    public String getIconResourceName(Object targetObject) {
        if (helper.getContext().getConfiguration().isLocalizationWicket()) {
            String key = isEnabled(targetObject) ? getIconTableKey(ICON_ENABLED, this.getWicketId()) :
                    getIconTableKey(ICON_DISABLED, this.getWicketId());
            return localizeLikeWicket(key, getForm());
        }
        String key = null;
        Class<?> domainClass = helper.getRef().getDomainClass();
        Method buttonMethod = helper.getButtonMethod();
        Prompt prompt = new AnnotationHelper(buttonMethod).getAnnotation(Prompt.class);
        if (prompt != null) {
            key = isEnabled(targetObject) ? getIconTableKey(ICON_ENABLED, prompt.value()) :
                    getIconTableKey(ICON_DISABLED, prompt.value());
        } else {
            key = isEnabled(targetObject) ? getIconTableKey(ICON_ENABLED, buttonMethod.getName()) :
                    getIconTableKey(ICON_DISABLED, buttonMethod.getName());
        }
        return new I18NLabelModel(domainClass, key).getObject();
    }

    private String getIconTableKey(String pattern, String baseKey) {
        return MessageFormat.format(pattern, baseKey);
    }

    private String localizeLikeWicket(String key, Component c) {
        try {
            // Wicket should recursively search for the property in the component's properties-file and those of its parents down to the application 
            return Localizer.get().getString(key, c);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * @return true if the method return file. In such case, the
     */
    public boolean isDownloadMethod() {
        Class<?> returnType = getMethod().getReturnType();
        return java.io.File.class.isAssignableFrom(returnType);
    }

    /**
     * @return Returns file to download from the method of the domain object.
     */
    public File onDownloadMethod(IModel<?> model) {
        helper.setForcedMethodTargetObject(model.getObject());
        return (File) helper.invokeButtonMethod(newMethodExceptionHanlder());
    }

}
