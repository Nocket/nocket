package dmdweb.component.modal;

import java.awt.Dimension;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;

import dmdweb.component.header.jquery.JQueryHelper;
import dmdweb.component.modal.ModalSettings.ButtonDef;

@SuppressWarnings("serial")
public class DMDModalWindow extends Panel {

    private static final class DummyCallback extends ModalCallback {
        @Override
        public boolean doAction(AjaxRequestTarget target, ButtonFlag result) {
            return true;
        }
    }

    public enum ConfirmType {
        YES_NO, YES_NO_CANCEL, OK_CANCEL;
    }

    // TODO meis026 DMDMessagePanel auf die ConfirmTypes umbauen!
    public enum ConfirmResult {
        OK, NO, CANCEL;
    }

    /**
     * Höhe eines Standardheaders
     */
    protected static final double MODAL_HEADER_SIZE = 20;

    private final WebMarkupContainer content = new WebMarkupContainer("content");

    private Panel modalPanel;

    private boolean doShow;

    private Dimension dimension;

    /**
     * Das Modal Window existiert einmal pro Page und wird für verschiedene
     * Dialoge mit unterschiedlichen ModalPanels bestückt. Wenn aus einem
     * bestehenden Dialog heraus ein neuer Dialog angezeigt werden soll, darf
     * das Modal Window noch nicht geschlossen werden. closePrevented sorgt
     * dafür, dass genau dann das Schließen des Modal Windows verhindert wird,
     * damit nachfolgend im ModalWindow eingereichte Panels nicht direkt
     * geschlossen sind.
     */
    private boolean closePrevented;

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
        WebMarkupContainer divModal = new WebMarkupContainer("innerModal") {
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
                // The calculation of margin top is buggy. It causes modal dialog gets out of the visible 
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
                    this.addOrReplace(getModalPanel());
                } else {
                    this.addOrReplace(content);
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

    public void show() {
        AjaxRequestTarget ajaxTarget = getRequestCycle().find(AjaxRequestTarget.class);
        if (ajaxTarget != null) {
            ajaxTarget.add(this);
        }
        doShow = true;
    }

    public Panel getModalPanel() {
        return modalPanel;
    }

    public void setModalPanel(Panel modalPanel) {
        closePrevented = (modalPanel instanceof DMDModalMessagePanel) && doShow;
        this.modalPanel = modalPanel;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        if (modalPanel == null) {
            return;
        }

        JQueryHelper.initJQuery(response);
        response.render(CssHeaderItem.forReference(new PackageResourceReference(DMDModalWindow.class,
                "DMDModalWindow.css")));
        response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(DMDModalWindow.class,
                "DMDModalWindow.js")));

        if (doShow) {
            String setDimensionToModalFooter = "";
            if (dimension != null) {
                setDimensionToModalFooter = "dmdModalWindowSetzeHoeheAnModalBody(" + ((int) dimension.getHeight())
                        + ", " + 1200 + ");";
            }
            // Bootstrap macht bei jeder Form einen margin-bottom von 20px. Dieses ist in der modalen Box sehr störend und muss per Javascript korrigiert werden
            response.render(OnDomReadyHeaderItem
                    .forScript("dmdModalWindowKorrigiereFormMargin(); zeigeBlockerWennModalPanelVorhanden(); "
                            + setDimensionToModalFooter));
        } else {
            response.render(OnLoadHeaderItem.forScript("zeigeBlockerWennModalPanelVorhanden();"));
        }
    }

    /**
     * 
     * @param target
     */
    public void close(AjaxRequestTarget target) {
        if (!closePrevented) {
            doShow = false;
        }
        // nachdem einmal das Schließen des ModalWindow verhindert wurde
        // kann das Fenster wieder zum Schließen freigegeben werden
        closePrevented = false;
        target.add(this);
    }

    public void showInfo(String title, String text, ModalCallback callback) {
        showModalMessagePanel(title, "info.title", text, callback, ButtonFlag.OK);
    }

    /**
     * The buttons in the confirmation dialog can be defined in the standard way (ok and no) or
     * they can be defined by up to three ButtonDefs. A @see dmdweb.component.modal.ModalSettings.ButtonDef
     * consist of a ButtonFlag and a string, that will be used to retrieve the text of the button.
     * The string will be used first as a key for a property file and if the key doesn't exists as the text.
     * Up to three buttons (means three ButtonDefs) could be used for a confirmation dialog.
     */
    public void showConfirm(String title, String text, ModalCallback callback, ButtonDef... buttonDefs) {
        if (buttonDefs != null && buttonDefs.length > 0) {
            showModalMessagePanel(title, "confirm.title", text, callback, buttonDefs);
        } else {
            showModalMessagePanel(title, "confirm.title", text, callback, ButtonFlag.OK, ButtonFlag.NO);
        }
    }

    private void showModalMessagePanel(ModalSettings settings, String defaultTitleKey, ModalCallback callback) {
        dimension = null;
        DMDModalMessagePanel modalPanel = new DMDModalMessagePanel("content", settings, this, defaultTitleKey);

        modalPanel.setCallback(callback);
        this.setModalPanel(modalPanel);
        this.show();
    }

    private void showModalMessagePanel(String title, String defaultTitleKey, String text, ModalCallback callback,
            ButtonDef... buttonDefs) {
        ModalSettings settings = new ModalSettings(title, text, buttonDefs);
        showModalMessagePanel(settings, defaultTitleKey, callback);
    }

    private void showModalMessagePanel(String title, String defaultTitleKey, String text, ModalCallback callback,
            ButtonFlag... buttonFlags) {
        ModalSettings settings = new ModalSettings(title, text, buttonFlags);
        showModalMessagePanel(settings, defaultTitleKey, callback);
    }

    public void showInfo(String title, String text) {
        showInfo(title, text, new DummyCallback());
    }

    public void showInfo(String text, ModalCallback callback) {
        showInfo(null, text, callback);
    }

    public void showConfirm(String text, ModalCallback callback) {
        showConfirm(null, text, callback);
    }

    public void showWarning(String title, String text) {
        showModalMessagePanel(title, "warning.title", text, new DummyCallback(), ButtonFlag.OK);
    }

    public void showError(String title, String text) {
        showModalMessagePanel(title, "error.title", text, new DummyCallback(), ButtonFlag.OK);
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
}
