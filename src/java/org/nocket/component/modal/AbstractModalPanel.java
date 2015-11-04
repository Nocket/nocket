/**
 * 
 */
package org.nocket.component.modal;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.nocket.gen.page.guiservice.CloserHandler;

/**
 * Aspekte für das Modale Panel im Modalen Dialog in Abstrakte Basisklasse ausgelagert
 *
 * @author Thomas.Veit@Bertelsmann.de
 *
 */
public abstract class AbstractModalPanel extends Panel {

    protected final AbstractModalWindow modalWindow;
    protected CloserHandler defaultCloserButtonCallback;
    protected Panel content;
    
    public AbstractModalPanel(String id, IModel<String> title, AbstractModalWindow dmdModalWindow) {
        super(id, null);
        this.modalWindow = dmdModalWindow;
    }

    public void close(AjaxRequestTarget target) {
        modalWindow.close(target);
    }

    public void setDefaultCloserButtonCallback(CloserHandler closerHandler) {
        this.defaultCloserButtonCallback = closerHandler;
    }

    public void setContent(Panel content) {
        this.content = content;
    }
}
