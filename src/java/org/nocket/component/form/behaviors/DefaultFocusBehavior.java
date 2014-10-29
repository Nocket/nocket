package org.nocket.component.form.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Behavior zum Fokusieren einer Formularkomponente beim Darstellen der Seite im
 * Browser. ZusÃ€tzlich wird der Inhalt der Formularkomponente markiert.
 *
 * @author arens01/janz04
 * @deprecated use HTML5 focus attribute instead
 */
@Deprecated
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
		String script = "var element = document.getElementById('" + component.getMarkupId() + "');" + focusElement + selectElement
				+ focusRadioChoice;
		/*
		 * janz04: Unterscheidung Ajax-Request vs. normalen Pageload Bei einem
		 * Request per Ajax wird der Dom ausgetauscht, was dazu fÃŒhrt, dass
		 * JavaScript nicht ausgefÃŒhrt wird. Deshalb reicht es bei einem
		 * AjaxCall nicht das Javascript in die Respone zu setzten. Wicket
		 * bietet die MÃ¶glichkeit JavaScript per appendJavaScript hinzuzufÃŒgen,
		 * dieses wird im Anschluss ausgefÃŒhrt Ein Problem aus Riester war, dass
		 * wenn man nur das JavaScript ausfÃŒhrt, der Focus erst beim zweiten
		 * Aufruf tatsÃ€chlich gesetzt wird. Dies wird dadurch erzwungen, dass
		 * zusÃ€tzlich die MÃ¶glichkeit von Wicket genutzt wird dem
		 * AjaxRequestTarget mitzuteilen welche Komponente den Fokus erhalten
		 * soll. Die Methode focusComponent reicht alleine nicht, da die hier
		 * ÃŒbergebene Component als span gerendert wird. Sollte hier jemand eine
		 * bessere Idee haben, hÃ¶re ich mir gerne gemecker an.
		 */
		AjaxRequestTarget ajaxRequestTarget = RequestCycle.get().find(AjaxRequestTarget.class);
		if (ajaxRequestTarget != null) {
			ajaxRequestTarget.appendJavaScript(script);
			ajaxRequestTarget.focusComponent(component);
		}
		else {
			response.render(OnDomReadyHeaderItem.forScript(script));
		}
	}
}
