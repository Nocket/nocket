package org.nocket.component.modal;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;

abstract public class ModalCallback implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Um bspw. von einer Confirmbox ein modales Panel aufrufen zu können, muss
     * vom Wrapper um die Action der ConfirmBox mitgetielt werden, dass das
     * Show-Flag nicht auf false gesetzt werden darf. Das geht leider derzeit
     * nur über diesen doofen Rückgabe-Wert, weil beide von einander nichts
     * wissen. Das ist eine lausige Umsetzung.
     */
    abstract public boolean doAction(AjaxRequestTarget target, ButtonFlag result);

}
