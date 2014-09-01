package org.nocket.component.form.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Behavior zum Fokusieren einer Formularkomponente beim Darstellen der Seite im
 * Browser. Zusätzlich wird der Inhalt der Formularkomponente markiert.
 * 
 * @author arens01/janz04
 */
public class DefaultFocusBehavior extends Behavior {
    private static final long serialVersionUID = -1L;

    @Override
    public void bind(org.apache.wicket.Component component) {
        if (!(component instanceof FormComponent)) {
            throw new IllegalArgumentException("DefaultFocusBehavior: component must be instanceof FormComponent");
        }
        component.setOutputMarkupId(true);
    }

    @Override
    public void renderHead(org.apache.wicket.Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        String focusElement = "element.focus();";
        String selectElement = "try { element.select() } catch(e){};";
        String focusRadioChoice = "try { element.getElementsByTagName('input')[0].focus() } catch(e){};";
        String script = "var element = document.getElementById('" + component.getMarkupId() + "');" + focusElement
                + selectElement + focusRadioChoice;
        /*
         * janz04: Unterscheidung Ajax-Request vs. normalen Pageload Bei einem
         * Request per Ajax wird der Dom ausgetauscht, was dazu führt, dass
         * JavaScript nicht ausgeführt wird. Deshalb reicht es bei einem
         * AjaxCall nicht das Javascript in die Respone zu setzten. Wicket
         * bietet die Möglichkeit JavaScript per appendJavaScript hinzuzufügen,
         * dieses wird im Anschluss ausgeführt Ein Problem aus Riester war, dass
         * wenn man nur das JavaScript ausführt, der Focus erst beim zweiten
         * Aufruf tatsächlich gesetzt wird. Dies wird dadurch erzwungen, dass
         * zusätzlich die Möglichkeit von Wicket genutzt wird dem
         * AjaxRequestTarget mitzuteilen welche Komponente den Fokus erhalten
         * soll. Die Methode focusComponent reicht alleine nicht, da die hier
         * übergebene Component als span gerendert wird. Sollte hier jemand eine
         * bessere Idee haben, höre ich mir gerne gemecker an.
         */
        AjaxRequestTarget ajaxRequestTarget = RequestCycle.get().find(AjaxRequestTarget.class);
        if (ajaxRequestTarget != null) {
            ajaxRequestTarget.appendJavaScript(script);
            ajaxRequestTarget.focusComponent(component);
        } else {
            response.render(OnDomReadyHeaderItem.forScript(script));
        }
    }
}