package org.nocket.gen.page.guiservice;

import gengui.GUIServiceI.MessageType;
import gengui.util.ReflectionUtil;

import java.awt.Dimension;
import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.nocket.NocketSession;
import org.nocket.component.modal.ButtonFlag;
import org.nocket.component.modal.DMDModalWindow;
import org.nocket.component.modal.ModalCallback;
import org.nocket.component.modal.ModalPanel;
import org.nocket.component.modal.ModalSettings.ButtonDef;
import org.nocket.gen.notify.Notifier;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.BodyElement;
import org.nocket.gen.page.element.ModalElement;
import org.nocket.gen.page.element.synchronizer.SynchronizerHelper;
import org.nocket.page.DMDPageFactory;
import org.nocket.page.DMDPanelFactory;
import org.nocket.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class DMDWebGenGuiServiceProvider implements WebGuiServiceI, Serializable {

    final private static Logger log = LoggerFactory.getLogger(DMDWebGenGuiServiceProvider.class);

    private static final String WICKET_ID_PANEL_INNER_CONTENT = "innerContent";

    public class ShowModalPanelConfig implements Serializable {
	private DMDWebGenPageContext showModalPanelParentContext;
	private DMDWebGenPageContext overrideShowModalPanelParentContext;
	private Object showModalPanel;
	private boolean modalPanelActive;
	private boolean closeModalPanel;
	public DMDModalWindow dmdModalWindowFromANonGenericContext;
	public boolean hideCloseButton;

	void close() {
	    if (showModalPanelParentContext != null) {
		DMDModalWindow modal;
		if (dmdModalWindowFromANonGenericContext != null) {
		    modal = dmdModalWindowFromANonGenericContext;
		} else {
		    modal = (DMDModalWindow) showModalPanelParentContext.getComponentRegistry().getComponent(
			    ModalElement.DEFAULT_WICKET_ID);
		}
		if (modal != null) {
		    modal.close(target);
		    modelChanged(showModalPanelParentContext);
		    SynchronizerHelper.updateAllFormsFromPage(showModalPanelParentContext,
			    target);
		}
	    }
	}

	private void modelChanged(DMDWebGenPageContext ctx) {
	    MarkupContainer root = SynchronizerHelper.findRoot(ctx.getPage());
	    root.visitChildren(new IVisitor<Component, Object>() {
		@Override
		public void component(Component object, IVisit<Object> visit) {
		    if (object instanceof Form) {
			Form.class.cast(object).clearInput();
		    }
		}
	    });
	}

	private void closeModalPanel() {
	    this.closeModalPanel = true;
	    if (showModalPanelParentContext != null) {
		SynchronizerHelper.updateAllFormsFromPage(showModalPanelParentContext, target);
	    }
	    removeActiveConfig();
	    close();
	}

	public Panel newPanel(String id) {
	    return DMDPanelFactory.getViewPanelInstance(showModalPanel, id);
	}
    }

    private boolean onloadAdded;
    private transient DMDWebGenPageContext targetContext;
    private transient AjaxRequestTarget target;
    private ButtonDef[] confirmButtonDefs;
    private String title;
    private String message;
    private MessageType messageType;
    private String status;
    private ModalResultCallback confirm;
    private Object showPage;

    private TouchedRegistry lastTouchedRegistry;

    private ShowModalPanelConfig activeShowModalPanelConfig;
    private ShowModalPanelConfig nextActiveShowModalPanelConfig;
    private transient DMDWebGenPageContext newPanelContext;

    private ShowModalPanelConfig getActiveShowModalPanelConfig() {
	if (activeShowModalPanelConfig != null && activeShowModalPanelConfig.modalPanelActive
		&& !activeShowModalPanelConfig.closeModalPanel) {
	    return activeShowModalPanelConfig;
	} else {
	    return null;
	}
    }

    public synchronized void onGeneratedBinding(final DMDWebGenPageContext ctx) {
	lastTouchedRegistry = ctx.getTouchedRegistry();
	DMDModalWindow modal = (DMDModalWindow) ctx.getComponentRegistry().getComponent(ModalElement.DEFAULT_WICKET_ID);
	if (modal != null && !onloadAdded) {
	    onloadAdded = true;
	    ctx.getPage().add(new OnModalGeneratedBindingBehavior(ctx));

	}
	ctx.getPage().add(new AddNotificationStyleSheetsBehaviour());
	newPanelContext = ctx;
    }

    protected class OnModalGeneratedBindingBehavior extends AjaxEventBehavior {

	protected final DMDWebGenPageContext ctx;

	public OnModalGeneratedBindingBehavior(DMDWebGenPageContext ctx) {
	    super("onload");
	    this.ctx = ctx;
	}

	@Override
	protected void onEvent(AjaxRequestTarget target) {
	    DMDWebGenGuiServiceProvider webGuiServiceProvider = NocketSession.get()
		    .getDMDWebGenGuiServiceProvider();
	    webGuiServiceProvider.registerAjaxRequestTarget(ctx, target);
	    webGuiServiceProvider.unregisterAjaxRequestTarget(ctx, target);
	    onloadAdded = false;
	}

	//        @Override
	//        public void renderHead(Component component, IHeaderResponse response) {
	    //            response.render(OnLoadHeaderItem.forScript(getCallbackScript()));
	    //        }
    }

    protected static class AddNotificationStyleSheetsBehaviour extends Behavior {
	@Override
	public void renderHead(Component component, IHeaderResponse response) {
	    super.renderHead(component, response);
	    // Stylesheets für PNotify, falls man eine Statusmeldung anzeigen will
	    // Die Zeilen sorgen dafür, dass man sich nicht darauf verlassen muss,
	    // dass die Stylesheets im ...Page.html oder einem Basis-HTML bereits
	    // eingebunden wurden. Insbesondere die modalen Dialoge leiten direkt
	    // von DMDWebPage ab und erben daher keine HTML-Basisbestandteile
	    response.render(CssHeaderItem.forUrl("css/jquery.pnotify.default.icons.css"));
	    response.render(CssHeaderItem.forUrl("css/jquery.pnotify.default.css"));
	    response.render(JavaScriptHeaderItem.forUrl("js/defaultBtn.js"));
	}
    }

    private DMDWebGenPageContext getNewPanelContext() {
	return newPanelContext;
    }

    public synchronized void registerAjaxRequestTarget(final DMDWebGenPageContext ctx, AjaxRequestTarget target) {
	ctx.updatePage(target);
	if (this.target != null) {
	    System.err
	    .println("WARNING: Seems like the "
		    + getClass().getSimpleName()
		    + " state is inconsistent right now, since a previous "
		    + AjaxRequestTarget.class.getSimpleName()
		    + " is still set. Clearing everything to come to a sane state again! This might have been caused by some exception!");
	    eraseAllShowModalPanelConfig();
	}
	if (activeShowModalPanelConfig != null) {
	    activeShowModalPanelConfig.modalPanelActive = true;
	}
	this.targetContext = ctx;
	this.target = target;
    }

    private void eraseAllShowModalPanelConfig() {
	clear(false);
	if (activeShowModalPanelConfig != null) {
	    activeShowModalPanelConfig.close();
	}
	activeShowModalPanelConfig = null;
	nextActiveShowModalPanelConfig = null;
    }

    public synchronized void unregisterAjaxRequestTarget(DMDWebGenPageContext outerCtx, AjaxRequestTarget target) {
	// Assert.test(target == this.target, "Wrong " + AjaxRequestTarget.class.getSimpleName() + " supplied!");
	if (target != this.target) {
	    String text = "Wrong " + AjaxRequestTarget.class.getSimpleName() + " supplied! \ntarget == " + target
		    + "\nthis.target == " + this.target;
	    //            eraseAllShowModalPanelConfig();
	    //            Assert.test(target == this.target, text);
	    log.debug(text);
	    return;
	}

	Assert.test(outerCtx == this.targetContext, "Wrong " + DMDWebGenPageContext.class.getSimpleName()
		+ " supplied!");
	final DMDWebGenPageContext ctx;
	if (activeShowModalPanelConfig != null
		&& activeShowModalPanelConfig.overrideShowModalPanelParentContext != null) {
	    ctx = activeShowModalPanelConfig.overrideShowModalPanelParentContext;
	} else {
	    ctx = outerCtx;
	}
	lastTouchedRegistry = ctx.getTouchedRegistry();
	if (showPage != null) {
	    ctx.getPage().setResponsePage(DMDPageFactory.getViewPageInstance(showPage));
	    clear(true);
	} else {
	    boolean openModalMessage = message != null || title != null;
	    boolean openModalConfirm = confirm != null;
	    boolean openModalPanel = activeShowModalPanelConfig != null
		    && activeShowModalPanelConfig.showModalPanelParentContext == null;
	    if (openModalMessage || openModalConfirm || openModalPanel) {
		final DMDModalWindow modal = (DMDModalWindow) ctx.getComponentRegistry().getComponent(
			ModalElement.DEFAULT_WICKET_ID);
		Assert.test(modal != null, "There is no modal component in " + ctx.getPage().getClass().getSimpleName()
			+ ". You need to add a tag for it for this to work: <div wicket:id=\"modal\"></div>");
		if (openModalPanel) {
		    openModalPanel(ctx, modal, target);
		} else if (openModalConfirm) {
		    openModalConfirm(ctx, modal);
		} else {
		    openModalMessage(modal);
		}
	    }
	    renderStatusMessage();
	    clear(false);
	}
    }

    private void renderStatusMessage() {
	if (status == null)
	    return;
	Notifier.info(target, status);
    }

    private void openModalPanel(DMDWebGenPageContext ctx, DMDModalWindow modal, AjaxRequestTarget target) {
	activeShowModalPanelConfig.showModalPanelParentContext = ctx;
	String title = ReflectionUtil.toTitle(activeShowModalPanelConfig.showModalPanel);
	Panel panel = activeShowModalPanelConfig.newPanel(WICKET_ID_PANEL_INNER_CONTENT);
	CloserHandler closerHandler = new CloserHandler(getNewPanelContext(),
		activeShowModalPanelConfig.hideCloseButton);

	showPanel(modal, panel, title, closerHandler);
	target.add(modal);
    }

    // TODO meis026 nur über den NichtGenerischen-Webadapter erreichbar machen
    public synchronized <T> void openModalPanelFromNonGenericContext(DMDModalWindow modal, AjaxRequestTarget target,
	    Object domainObject) {
	openModalPanelFromNonGenericContext(modal, target, domainObject, false);
    }

    public synchronized <T> void openModalPanelFromNonGenericContext(DMDModalWindow modal, AjaxRequestTarget target,
	    Object domainObject, boolean hideCloseButton) {
	showModalPanel(domainObject, hideCloseButton);
	Panel panel = activeShowModalPanelConfig.newPanel(WICKET_ID_PANEL_INNER_CONTENT);
	CloserHandler closerHandler = new CloserHandler(getNewPanelContext(), hideCloseButton);
	activeShowModalPanelConfig.dmdModalWindowFromANonGenericContext = modal;
	activeShowModalPanelConfig.showModalPanelParentContext = getNewPanelContext();
	String title = ReflectionUtil.toTitle(activeShowModalPanelConfig.showModalPanel);
	showPanel(modal, panel, title, closerHandler);
	target.add(modal);
    }

    private void showPanel(DMDModalWindow modal, Panel panel, String title, CloserHandler closeHandler) {
	ModalPanel modalPanel = new ModalPanel("content", Model.of(title), modal);
	modalPanel.setDefaultCloserButtonCallback(closeHandler);
	modalPanel.setContent(panel);
	modal.setModalPanel(modalPanel);

	Dimension dimension = dimensionForModalPanel();
	modal.setDimension(dimension);
	modal.show();
    }

    private Dimension dimensionForModalPanel() {
	BodyElement bodyElement = newPanelContext.getPageRegistry().getBodyElement();
	Integer width = bodyElement.getWidth();
	Integer height = bodyElement.getHeight();
	return width != null && height != null ? new Dimension(width, height) : null;
    }

    private void openModalConfirm(final DMDWebGenPageContext ctx, final DMDModalWindow modal) {
	ModalCallback modalCallback = new ModalCallback() {
	    private final ModalResultCallback confirmCopy = confirm;

	    @Override
	    public boolean doAction(AjaxRequestTarget target, ButtonFlag flag) {
		ctx.updatePage(target);
		DMDWebGenGuiServiceProvider webGuiServiceProvider = NocketSession.get()
			.getDMDWebGenGuiServiceProvider();
		try {
		    webGuiServiceProvider.registerAjaxRequestTarget(ctx, target);
		    confirmCopy.onResult(flag);
		    SynchronizerHelper.updateAllFormsFromPage(ctx, target);
		} finally {
		    webGuiServiceProvider.unregisterAjaxRequestTarget(ctx, target);
		}
		// Wenn es noch ein modaler Dialog gezeigt werden soll, dann muss das mitgeteilt werden
		return true;//webGuiServiceProvider.activeShowModalPanelConfig == null; Ich kann mir den Grund für diese bedingten Rückgabewert nicht erklären. Es macht scheinbar keinen Sinn.
	    }
	};
	modal.showConfirm(title, message, modalCallback, confirmButtonDefs);
    }

    private void openModalMessage(final DMDModalWindow modal) {
	switch (messageType) {
	case INFO:
	    modal.showInfo(title, message);
	    break;
	case WARNING:
	    modal.showWarning(title, message);
	    break;
	case ERROR:
	    modal.showError(title, message);
	    break;
	default:
	    throw new IllegalArgumentException("Unknown " + MessageType.class.getSimpleName() + ": " + messageType);
	}
    }

    private void removeActiveConfig() {
	if (nextActiveShowModalPanelConfig != null) {
	    nextActiveShowModalPanelConfig.overrideShowModalPanelParentContext = activeShowModalPanelConfig.showModalPanelParentContext;
	}
	activeShowModalPanelConfig = nextActiveShowModalPanelConfig;
	nextActiveShowModalPanelConfig = null;
    }

    private void clear(boolean redirecting) {
	target = null;
	targetContext = null;
	if (!redirecting) {
	    title = null;
	    message = null;
	    messageType = null;
	    confirm = null;
	    status = null;
	}
	showPage = null;
    }

    @Override
    public synchronized void errorMessage(String message) {
	this.message = message;
	this.messageType = MessageType.ERROR;
    }

    @Override
    public synchronized void errorMessage(String title, String message) {
	this.title = title;
	errorMessage(message);
    }

    @Override
    public synchronized void infoMessage(String message) {
	this.message = message;
	this.messageType = MessageType.INFO;
    }

    @Override
    public synchronized void infoMessage(String title, String message) {
	this.title = title;
	infoMessage(message);
    }

    @Override
    public synchronized void warningMessage(String message) {
	this.message = message;
	this.messageType = MessageType.WARNING;
    }

    @Override
    public synchronized void warningMessage(String title, String message) {
	this.title = title;
	warningMessage(message);
    }

    @Override
    public synchronized void confirmMessage(String message, ModalResultCallback<ButtonFlag> callback,
	    ButtonDef... buttonDefs) {
	this.message = message;
	this.confirm = callback;
	this.confirmButtonDefs = buttonDefs;
    }

    @Override
    public synchronized void confirmMessage(String title, String message, ModalResultCallback<ButtonFlag> callback,
	    ButtonDef... buttonDefs) {
	this.title = title;
	this.confirmButtonDefs = buttonDefs;
	confirmMessage(message, callback, buttonDefs);
    }

    @Override
    public synchronized void status(String message) {
	this.status = message;
    }

    @Override
    public synchronized void showPage(Object domainObject) {
	if (domainObject == null) {
	    //do not reset selection
	    return;
	}
	this.showPage = domainObject;
    }

    @Override
    public synchronized void showModalPanel(Object domainObject) {
	showModalPanel(domainObject, false);
    }

    @Override
    public synchronized <T> void showModalPanel(Object domainObject, boolean hideCloseButton) {
	final ShowModalPanelConfig config = new ShowModalPanelConfig();
	config.showModalPanel = domainObject;
	config.hideCloseButton = hideCloseButton;

	if (activeShowModalPanelConfig != null) {
	    nextActiveShowModalPanelConfig = config;
	} else {
	    activeShowModalPanelConfig = config;
	}
    }

    @Override
    public synchronized boolean touched(String... wicketIdPrefixes) {
	return lastTouchedRegistry.touched(wicketIdPrefixes);
    }

    @Override
    public synchronized void touch(String... wicketIdPrefixes) {
	lastTouchedRegistry.touch(wicketIdPrefixes);
    }

    @Override
    public synchronized void untouch(String... wicketIdPrefixes) {
	lastTouchedRegistry.untouch(wicketIdPrefixes);
    }

    @Override
    public synchronized boolean isModalPanelActive() {
	return getActiveShowModalPanelConfig() != null;
    }

    @Override
    public synchronized void closeModalPanel() {
	Assert.test(isModalPanelActive(), "No ModalPage currently active!");
	getActiveShowModalPanelConfig().closeModalPanel();
    }

    @Override
    public String workdir() {
	return WebApplication.get().getServletContext().getRealPath(".");
    }

    @Override
    public void resetModalPanelConfig() {
	if (isModalPanelActive()) {
	    removeActiveConfig();
	    activeShowModalPanelConfig = null;
	}
    }

}
