package org.nocket.component.modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A ModalSettings describes the function and appearance of a confirm, indo or
 * error dialog. <br>
 * For a confirmation dialog:<br>
 * The buttons in the confirmation dialog can be defined in the standard way (ok
 * and no) or they can be defined by up to three ButtonDefs. A @see
 * org.nocket.component.modal.ModalSettings.ButtonDef consist of a ButtonFlag
 * and a string, that will be used to retrieve the text of the button. The
 * string will be used first as a key for a property file and if the key doesn't
 * exists as the text. Up to three buttons (means three ButtonDefs) could be
 * used for a confirmation dialog.
 */
public class ModalSettings extends ModalPageSettings {

	public static class ButtonDef implements Serializable {

		final public ButtonFlag flag;
		final public String keyOrText;

		public ButtonDef(ButtonFlag flag, String keyOrText) {
			this.flag = flag;
			this.keyOrText = keyOrText;
		}

	}

	private static final long serialVersionUID = 1L;

	private final List<ButtonFlag> flags;
	private final List<String> keyOrTexts;

	private String text;

	public ModalSettings(String title, String text, ButtonFlag... buttons) {
		super(title);
		this.text = text;
		flags = Arrays.asList(buttons);
		keyOrTexts = null;
	}

	public ModalSettings(String title, String text, ButtonDef... buttonDefs) {
		super(title);
		this.text = text;
		keyOrTexts = new ArrayList<String>();
		flags = new ArrayList<ButtonFlag>();
		for (ButtonDef buttonDef : buttonDefs) {
			flags.add(buttonDef.flag);
			keyOrTexts.add(buttonDef.keyOrText);
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	boolean containsFlag(ButtonFlag flag) {
		return flags.contains(flag);
	}

	String getKeyOrTextForFlag(ButtonFlag flag) {
		if (keyOrTexts == null || keyOrTexts.isEmpty()) {
			return null;
		}
		for (int i = 0; i < flags.size(); i++) {
			ButtonFlag buttonFlag = flags.get(i);
			if (buttonFlag.equals(flag)) {
				return keyOrTexts.get(i);
			}
		}
		return null;
	}
}
