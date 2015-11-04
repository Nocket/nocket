/**
 * 
 */
package org.nocket.component.modal;

import java.awt.Dimension;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.nocket.component.modal.ModalSettings.ButtonDef;

/**
 * Extraktion der Grundfunktionen der Modalen Dialoge aus der DMDModalWindow
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public abstract class AbstractModalWindow extends Panel {


	protected boolean doShow;

	protected Dimension dimension;
	
	/**
	 * Das Modal Window existiert einmal pro Page und wird für verschiedene
	 * Dialoge mit unterschiedlichen ModalPanels bestückt. Wenn aus einem
	 * bestehenden Dialog heraus ein neuer Dialog angezeigt werden soll, darf
	 * das Modal Window noch nicht geschlossen werden. closePrevented sorgt
	 * dafür, dass genau dann das Schließen des Modal Windows verhindert wird,
	 * damit nachfolgend im ModalWindow eingereichte Panels nicht direkt
	 * geschlossen sind.
	 */
	protected boolean closePrevented;

	protected Panel modalPanel;
	

	public AbstractModalWindow(String id) {
		super(id);
	}

	public AbstractModalWindow(final String id, final IModel<?> model)
	{
		super(id, model);
	}
	
	


	public enum ConfirmType {
		YES_NO, YES_NO_CANCEL, OK_CANCEL;
	}

	// TODO meis026 DMDMessagePanel auf die ConfirmTypes umbauen!
	public enum ConfirmResult {
		OK, NO, CANCEL;
	}
	


	public Panel getModalPanel() {
		return modalPanel;
	}

	public void setModalPanel(Panel modalPanel) {
		closePrevented = modalPanel instanceof DMDModalMessagePanel && doShow;
		this.modalPanel = modalPanel;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public void show() {
		AjaxRequestTarget ajaxTarget = getRequestCycle().find(AjaxRequestTarget.class);
		if (ajaxTarget != null) {
			ajaxTarget.add(this);
		}
		doShow = true;
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
	
	
	/**
	 * The buttons in the confirmation dialog can be defined in the standard way
	 * (ok and no) or they can be defined by up to three ButtonDefs. A @see
	 * org.nocket.component.modal.ModalSettings.ButtonDef consist of a
	 * ButtonFlag and a string, that will be used to retrieve the text of the
	 * button. The string will be used first as a key for a property file and if
	 * the key doesn't exists as the text. Up to three buttons (means three
	 * ButtonDefs) could be used for a confirmation dialog.
	 */
	public void showConfirm(String title, String text, ModalCallback callback, ButtonDef... buttonDefs) {
		if (buttonDefs != null && buttonDefs.length > 0) {
			showModalMessagePanel(title, "confirm.title", text, callback, buttonDefs);
		}
		else {
			showModalMessagePanel(title, "confirm.title", text, callback, ButtonFlag.OK, ButtonFlag.NO);
		}
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

	public void showInfo(String title, String text, ModalCallback callback) {
		showModalMessagePanel(title, "info.title", text, callback, ButtonFlag.OK);
	}

	public void showWarning(String title, String text) {
		showModalMessagePanel(title, "warning.title", text, new DummyCallback(), ButtonFlag.OK);
	}

	public void showError(String title, String text) {
		showModalMessagePanel(title, "error.title", text, new DummyCallback(), ButtonFlag.OK);
	}
	
	
	abstract protected void showModalMessagePanel(ModalSettings settings, String defaultTitleKey, ModalCallback callback);
	abstract protected void showModalMessagePanel(String title, String defaultTitleKey, String text, ModalCallback callback, ButtonDef... buttonDefs);
	abstract protected void showModalMessagePanel(String title, String defaultTitleKey, String text, ModalCallback callback, ButtonFlag... buttonFlags);
	
	/**
	 * Methode initialisiert ein neues Modales Panel f�r den Modalen Dialog
	 * @param id Wicket-ID f�r das Panel
	 * @param title Titel f�r das Panel
	 */
	abstract public AbstractModalPanel getNewModalPanel(String id, IModel<String> title);
	
	
	
	

	protected static final class DummyCallback extends ModalCallback {

		@Override
		public boolean doAction(AjaxRequestTarget target, ButtonFlag result) {
			return true;
		}
	}
}
