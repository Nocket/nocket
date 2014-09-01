package dmdweb.component.form.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.transformer.AbstractTransformerBehavior;
import org.apache.wicket.util.iterator.ComponentHierarchyIterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dmdweb.component.form.ComponentGroup;
import dmdweb.component.select.DMDRadioChoice;

/**
 * This behavior adds to a group of the components a paragraph with error
 * messages coming from these components.
 * 
 * @auto blaz02
 * 
 */
public class ValidationTooltipStyleGroupBehavior extends AbstractTransformerBehavior {

    final private static Logger log = LoggerFactory.getLogger(ValidationTooltipStyleGroupBehavior.class);

    private static final long serialVersionUID = 1L;

    public static final String VALIDATION_ERROR_ICON_CLASS = "icon-validation-error";

    private final static String BR = "\n";
    private final static String BEFORE_MESSAGE = "<span class=\"error-icon\">"
	    + "<i class=\"" + VALIDATION_ERROR_ICON_CLASS + "\" title = \"";
    private final static String BEFORE_CS = "<div class=\"control-group error\">";
    private final static String AFTER_MESSAGE = "\"></i></span>";
    private final static String AFTER_CS = "</div>";

    @Override
    public CharSequence transform(Component component, CharSequence cs) {
	boolean added = false;
	StringBuffer messageBuffer = new StringBuffer(BEFORE_MESSAGE);
	boolean isRadio = false;
	ComponentHierarchyIterator it = ((ComponentGroup) component).visitChildren(FormComponent.class);
	for (Component n : it) {
	    FormComponent<?> fc = (FormComponent<?>) n;
	    if (!fc.isValid()) {
		if (fc instanceof DMDRadioChoice<?>) {
		    isRadio = true;
		}
		for (FeedbackMessage message : fc.getFeedbackMessages()) {
		    message.markRendered();
		    if (added) {
			messageBuffer.append(BR);
		    }
		    messageBuffer.append(message.getMessage());
		    added = true;
		}
	    }
	}

	if (!added) {
	    return cs;
	}

	messageBuffer.append(AFTER_MESSAGE);

	StringBuffer b;
	if (isRadio) {
	    b = buildOutputForRadio(cs, messageBuffer, component);
	} else {
	    b = buildOutput(cs, messageBuffer);
	}
	return b.toString();
    }

    /**
     * Wenn Radios und Checkboxen mit Wicket und Bootstrap verwendet werden,
     * darf der Fehlertext nicht wie bei den einzelnen Inputfeldern hinten
     * angefügt werden, sondern er muss in das Controls-Div hinein operiert.
     * werden
     * 
     * <pre>
     *             &lt;div class="control-group error">
     *                     &lt;div wicket:id="genderGroup" class="radio-checkbox-group">&lt;wicket:border>
     *                         &lt;wicket:body>
     *                             &lt;div class="controls">
     *                                 &lt;label class="radio" wicket:id="gender" id="gender">
     *                                     &lt;input name="memberpanel:personDataPanel:genderGroup:genderGroup_body:gender" type="radio" value="HERR" id="gender-HERR"/>
     *                                     &lt;label for="gender-HERR">Man&lt
     *                                     HIER MUSS ES HIN
     *                                     &lt;/label>
     *                                     &lt;input name="memberpanel:personDataPanel:genderGroup:genderGroup_body:gender" type="radio" value="FRAU" id="gender-FRAU"/>
     *                                     &lt;label for="gender-FRAU">Woman&lt;/label>
     *                                 &lt;/label>
     *                             &lt;/div>
     *                         &lt;/wicket:body>
     *                     &lt;/wicket:border>
     *                 &lt;/div>
     * 
     * </pre>
     * 
     * @param cs
     *            vorhandener HTML-Code
     * @param messageBuffer
     *            Fehlertexte
     * @param component
     * @return
     */
    private StringBuffer buildOutputForRadio(CharSequence cs, StringBuffer messageBuffer, Component component) {
	StringBuffer b = new StringBuffer(BEFORE_CS);

	Document document = Jsoup.parse(cs.toString());
	Elements divControls = document.select("div[class*=controls]");

	if (divControls == null) {
	    log.debug("Finde kein div[class*=controls] in Komponente = '" + component + "' und Page = '"
		    + component.getPage());
	    return buildOutput(cs, messageBuffer);
	}

	Elements labelRadio = divControls.first().select("label[class*=radio]");

	if (labelRadio == null) {
	    log.debug("Finde kein " + "label[class*=radio]" + " in Komponente = '" + component + "' und Page = '"
		    + component.getPage());
	    return buildOutput(cs, messageBuffer);
	}

	Elements label = labelRadio.first().children().select("label");

	if (label == null) {
	    log.debug("Finde kein label in Komponente = '" + component + "' und Page = '" + component.getPage());
	    return buildOutput(cs, messageBuffer);
	}

	label.first().append(messageBuffer.toString());
	b.append(document.html());

	b.append(AFTER_CS);
	return b;
    }

    private StringBuffer buildOutput(CharSequence cs, StringBuffer messageBuffer) {
	StringBuffer b = new StringBuffer(BEFORE_CS).append(cs);
	b.append(messageBuffer);
	b.append(AFTER_CS);
	return b;
    }
}
