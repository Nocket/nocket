package org.nocket.component.table;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class LabelHtmlizer {
    public static final String NO_LABEL = "_no_label";

    public static void enableHtml(Component labelComponent) {
        if (labelComponent instanceof Label) {
            Label label = (Label) labelComponent;
            String labelText = (String) label.getDefaultModelObject();
            String strippedLabel = stripHTMLIntroOutro(labelText);
            if (strippedLabel.length() < labelText.length()) { // Text is <HTML>-introduced
                label.setDefaultModel(Model.of(strippedLabel));
                label.setEscapeModelStrings(false);
            }
        }
    }

    public static String stripHTMLIntroOutro(String message) {
        if (message.length() > 5) {
            String intro = message.substring(0, 6).toUpperCase();
            if (intro.equals("<HTML>")) {
                message = message.substring(6);
                if (message.length() > 6) {
                    String outro = message.substring(message.length() - 7, message.length()).toUpperCase();
                    if (outro.equals("</HTML>")) {
                        message = message.substring(0, message.length() - 7);
                    }
                }
            }
        }
        return message;
    }

}
