package org.nocket.component.modal;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.nocket.component.header.jquery.JQueryHelper;
import org.nocket.component.modal.ModalSettings.ButtonDef;

@SuppressWarnings("serial")
public class DMDModalWindow extends AbstractModalWindow {


	/**
	 * Höhe eines Standardheaders
	 */
	protected static final double MODAL_HEADER_SIZE = 20;

	private final WebMarkupContainer content = new WebMarkupContainer("content");


	public DMDModalWindow(String id, DMDModalMessagePanel modalPanel) {
		this(id);
		this.modalPanel = modalPanel;
	}

	public DMDModalWindow(String id) {
		super(id);

		/**
		 * Das ist das Div über das Bootstrap die Anzeige des Panels steuert.
		 * Hier wird show oder hide als class ergänzt.
		 */
		WebMarkupContainer divModal = new WebMarkupContainer(
				"innerModal") {

			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);

				changeClassTag(tag);
				changeStyleTag(tag);

			}

			private void changeClassTag(ComponentTag tag) {
				String css = doShow ? " show" : " hide";
				String attribute = tag.getAttribute("class");
				tag.put("class", attribute + css);
			}

			private void changeStyleTag(ComponentTag tag) {
				if (dimension == null || !doShow) {
					return;
				}

				/**
				 * <pre>
				 * width: 900px; // SET THE WIDTH OF THE MODAL
				 * margin: -250px 0 0 -450px; // CHANGE MARGINS TO ACCOMODATE THE NEW WIDTH (original = margin: -250px 0 0 -280px;)
				 * </pre>
				 */
				int width = (int) dimension.getWidth();
				String cssClassWidth = "width: " + width + "px; ";

				// TODO blaz02
				// The calculation of margin top is buggy. It causes modal
				// dialog gets out of the visible
				// area of the browser window. Therefore commented out.
				// String marginTop = "margin-top: -" + calcHeight() + "px; ";
				String marginLeft = "margin-left: -" + (int) (dimension.getWidth() / 2) + "px; ";
				// String cssClassMargin = marginTop + marginLeft;
				String cssClassMargin = marginLeft;

				String attribute = tag.getAttribute("style");
				tag.put("style", StringUtils.trimToEmpty(attribute) + " " + cssClassWidth + " " + cssClassMargin);
			}

			private int calcHeight() {
				double height = (dimension.getHeight() + MODAL_HEADER_SIZE) / 2;
				return (int) height;
			};

			@Override
			/**
			 * Austauschen des Panels
			 */
			protected void onBeforeRender() {
				if (doShow) {
					addOrReplace(getModalPanel());
				}
				else {
					addOrReplace(content);
				}
				super.onBeforeRender();
			}
		};
		add(divModal);

		setOutputMarkupId(true);
	}

	public String getModalWindowId() {
		return content.getMarkupId();
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		if (modalPanel == null) {
			return;
		}

		JQueryHelper.initJQuery(response);
		//response.render(CssHeaderItem.forReference(new PackageResourceReference(DMDModalWindow.class, "DMDModalWindow.css")));
		response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(DMDModalWindow.class, "DMDModalWindow.js")));

		if (doShow) {
			String setDimensionToModalFooter = "";
			if (dimension != null) {
				setDimensionToModalFooter = "dmdModalWindowSetzeHoeheAnModalBody(" + (int) dimension.getHeight() + ", " + 1200 + ");";
			}
			// Bootstrap macht bei jeder Form einen margin-bottom von 20px.
			// Dieses ist in der modalen Box sehr störend und muss per
			// Javascript korrigiert werden
			response.render(OnDomReadyHeaderItem.forScript("dmdModalWindowKorrigiereFormMargin(); zeigeBlockerWennModalPanelVorhanden(); "
					+ setDimensionToModalFooter));
		}
		else {
			response.render(OnLoadHeaderItem.forScript("zeigeBlockerWennModalPanelVorhanden();"));
		}
	}

	

	@Override
	protected void showModalMessagePanel(ModalSettings settings, String defaultTitleKey, ModalCallback callback) {
		dimension = null;
		DMDModalMessagePanel modalPanel = new DMDModalMessagePanel("content", settings, this, defaultTitleKey);

		modalPanel.setCallback(callback);
		setModalPanel(modalPanel);
		show();
	}

	@Override
	protected void showModalMessagePanel(String title, String defaultTitleKey, String text, ModalCallback callback, ButtonDef... buttonDefs) {
		ModalSettings settings = new ModalSettings(title, text, buttonDefs);
		showModalMessagePanel(settings, defaultTitleKey, callback);
	}

	@Override
	protected void showModalMessagePanel(String title, String defaultTitleKey, String text, ModalCallback callback, ButtonFlag... buttonFlags) {
		ModalSettings settings = new ModalSettings(title, text, buttonFlags);
		showModalMessagePanel(settings, defaultTitleKey, callback);
	}

	@Override
	public AbstractModalPanel getNewModalPanel(String id, IModel<String> title) {
		return new ModalPanel(id, title, this);
	}
}
