package org.nocket.component.modal;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.nocket.component.button.DMDOnClickIndicatorAttributeModifier;
import org.nocket.component.table.LabelHtmlizer;

// TODO: Auto-generated Javadoc
/**
 * The Class DMDModalMessagePanel.
 */
@SuppressWarnings("serial")
public class DMDModalMessagePanel extends Panel {

	/** Der Callback, der nach dem Button-Klick ausgeführt wird. */
	private ModalCallback callback;

	/** Das aufrufende Panel, bei dem Close aufgerufen werden muss. */
	private final DMDModalWindow dmdModalWindow;

	/**
	 * The Class ModalPanelAjaxLink.
	 */
	private class ModalPanelAjaxLink extends AjaxLink<ModalSettings> {

		/** The flag. */
		private final ButtonFlag flag;

		/**
		 * Instantiates a new modal panel ajax link.
		 *
		 * @param flag the flag
		 * @param model the model
		 */
		public ModalPanelAjaxLink(ButtonFlag flag, IModel<ModalSettings> model) {
			super(flag.getId(), model);
			this.flag = flag;
		}

		/**
		 * Wicket wants to have AttributeModifiers added in onInitialize() for
		 * AjaxLink! m(.
		 *
		 * @see org.apache.wicket.ajax.markup.html.AjaxLink#onInitialize()
		 */
		@Override
		protected void onInitialize() {
			super.onInitialize();
			add(new DMDOnClickIndicatorAttributeModifier(ModalPanelAjaxLink.this));
		}

		/* (non-Javadoc)
		 * @see org.apache.wicket.ajax.markup.html.AjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)
		 */
		@Override
		public void onClick(AjaxRequestTarget target) {
			/**
			 * Wenn bspw. nach einer Confirm-Box noxh ein modaler Dialog
			 * aufgemacht wird, darf das show-Flag nicht auf false gesetzt
			 * werden, da ansonsten das folgende Panel nicht dargestellt wird.
			 * Der Grund liegt darin, dass wir die Instance, das die Panels hält
			 * wieder benutzten. Im Moment würde ich sagen, eine recht
			 * suboptimale Entscheidung. Das sollte refactored werden. meis026
			 */
			Boolean doClose = getCallback().doAction(target, flag);
			if (doClose == null || Boolean.TRUE == doClose) {
				dmdModalWindow.close(target);
			}
			target.appendJavaScript(DMDOnClickIndicatorAttributeModifier.getBlockerRemoveScript());
		}

		/* (non-Javadoc)
		 * @see org.apache.wicket.Component#isVisible()
		 */
		@Override
		public boolean isVisible() {
			return getModelObject().containsFlag(flag);
		}

		/* (non-Javadoc)
		 * @see org.apache.wicket.ajax.markup.html.AjaxLink#onComponentTag(org.apache.wicket.markup.ComponentTag)
		 */
		@Override
		protected void onComponentTag(ComponentTag tag) {
			tag.append("class", flag.getId(), " ");
			super.onComponentTag(tag);
		}
	}

	/**
	 * Instantiates a new DMD modal message panel.
	 *
	 * @param id the id
	 * @param modalSettings the modal settings
	 * @param dmdModalWindow the dmd modal window
	 */
	public DMDModalMessagePanel(String id, ModalSettings modalSettings, DMDModalWindow dmdModalWindow) {
		this(id, modalSettings, dmdModalWindow, null);
	}

	/**
	 * Instantiates a new DMD modal message panel.
	 *
	 * @param id the id
	 * @param modalSettings the modal settings
	 * @param dmdModalWindow the dmd modal window
	 * @param defaultTitleKey the default title key
	 */
	public DMDModalMessagePanel(String id, ModalSettings modalSettings, DMDModalWindow dmdModalWindow, String defaultTitleKey) {
		super(id);
		this.dmdModalWindow = dmdModalWindow;

		if (defaultTitleKey != null) {
			String title = createTitleStr(defaultTitleKey, modalSettings.getTitle());
			modalSettings.setTitle(title);
		}

		add(new Label("title.label", new Model<String>(modalSettings.getTitle())));
		add(createMessageLabel(modalSettings.getText()));
		ModalPanelAjaxLink btnOK = new ModalPanelAjaxLink(ButtonFlag.OK, new Model<ModalSettings>(modalSettings));
		ModalPanelAjaxLink btnYES = new ModalPanelAjaxLink(ButtonFlag.YES, new Model<ModalSettings>(modalSettings));
		ModalPanelAjaxLink btnNO = new ModalPanelAjaxLink(ButtonFlag.NO, new Model<ModalSettings>(modalSettings));
		add(btnOK);
		add(btnYES);
		add(btnNO);
		btnOK.add(new Label("ok.label", newButtonTextOrResourceModelWithDefault(modalSettings, ButtonFlag.OK, id, "modalbutton.OK")));
		btnYES.add(new Label("yes.label", newButtonTextOrResourceModelWithDefault(modalSettings, ButtonFlag.YES, id, "modalbutton.YES")));
		btnNO.add(new Label("no.label", newButtonTextOrResourceModelWithDefault(modalSettings, ButtonFlag.NO, id, "modalbutton.NO")));

		this.add(new AttributeAppender("class", "modalMessagePanel").setSeparator(" "));
	}

	/**
	 * There are two ways for defining the text of a button. First it can be
	 * taken from the standard property file. Second a string can be given. This
	 * string will be first searched as a key in the property file. If nothing
	 * is found the string will be taken as the text shown to the user.
	 *
	 * @param modalSettings the modal settings
	 * @param buttonFlag the button flag
	 * @param id the id
	 * @param keyPart the key part
	 * @return the i wrap model
	 */
	protected IWrapModel<String> newButtonTextOrResourceModelWithDefault(ModalSettings modalSettings, ButtonFlag buttonFlag, String id,
			String keyPart) {
		String keyOrText = modalSettings.getKeyOrTextForFlag(buttonFlag);
		// If there is a string defined...
		if (StringUtils.isNotBlank(keyOrText)) {
			// ... search for the string as a key in the property file or use it
			// as the default string
			return new ResourceModel(keyOrText, keyOrText).wrapOnAssignment(this);
		}
		// use the standard way
		return newResourceModelWithDefault(id, keyPart);
	}

	/**
	 * New resource model with default.
	 *
	 * @param id the id
	 * @param keyPart the key part
	 * @return the i wrap model
	 */
	protected IWrapModel<String> newResourceModelWithDefault(String id, String keyPart) {
		return new ResourceModel(id + "." + keyPart, new ResourceModel(keyPart).wrapOnAssignment(this).getObject()).wrapOnAssignment(this);
	}

	/**
	 * Creates a Label without model string escaping if the text to display is
	 * introduced by <HTML>. In this case the intro is removed and also a
	 * potential </HTML> outro. This concept is stolen from Swing. Otherwise,
	 * the method returns a MultiLineLabel
	 *
	 * @param message the message
	 * @return the web component
	 */
	protected WebComponent createMessageLabel(String message) {
		String strippedMessage = stripHTMLIntroOutro(message);
		if (strippedMessage.length() == message.length()) {
			return new MultiLineLabel("message.label", new Model<String>(message));
		}
		Label messageLabel = new Label("message.label", message);
		messageLabel.setEscapeModelStrings(false);
		return messageLabel;
	}

	/**
	 * Strip html intro outro.
	 *
	 * @param message the message
	 * @return the string
	 */
	protected String stripHTMLIntroOutro(String message) {
		return LabelHtmlizer.stripHTMLIntroOutro(message);
	}

	/**
	 * Opens MessageBox with specified {@link ModalSettings}.
	 *
	 * @return the callback
	 */
	public ModalCallback getCallback() {
		return callback;
	}

	/**
	 * Sets the callback.
	 *
	 * @param callback the new callback
	 */
	public void setCallback(ModalCallback callback) {
		this.callback = callback;
	}

	/**
	 * Creates the title str.
	 *
	 * @param defaultTitleKey the default title key
	 * @param title the title
	 * @return the string
	 */
	private String createTitleStr(String defaultTitleKey, String title) {
		String titleStr = new ResourceModel(defaultTitleKey).wrapOnAssignment(this).getObject();
		if (StringUtils.isNotBlank(title)) {
			titleStr += ": " + title;
		}
		return titleStr;
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(OnDomReadyHeaderItem.forScript("$('.modalMessagePanel').parent().addClass('modalMessagePanelInnerModal');"));
	}

}
