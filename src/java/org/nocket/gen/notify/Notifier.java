package org.nocket.gen.notify;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * This is a generic notification service based on Ajax, JQuery and the PNotify
 * JavaScript plugin It makes no assumption on any exiting preparation of the
 * current page but dynamically lazy-loads the required JavaScript libraries. It
 * is therefore intentionally <b>NOT</b> provides as a Wicket component which
 * would always asume that the pages know in beforehand that they need to
 * display general notifications.
 * 
 * @author less02
 */
public class Notifier {

    public static void notify(AjaxRequestTarget target, String message, String notificationFunction) {

        final String contextPath = WebApplication.get().getServletContext().getContextPath();

        // Das hier ist etwas tricky: die Javascript-Module f�r die schicke Meldungsanzeige
        // �ber PNotify m�ssen in den Body und nicht in den Header. Wir d�rfen aber keine
        // Annahme dar�ber treffen, ob die Module im Body der aktuellen Page bereits drin
        // stecken. Insbesondere modale Dialoge sind direkt von DMDWebPage abgeleitet und
        // erhalten keine Body-Bestandteile �ber irgend ein Basis-HTML. Deswegen laden wir
        // die Skripte �ber eine enstrechende jQuery-Funktion dynamisch nach. Das Ausl�sen
        // der eigentlichen Benachrichtigung erfolgt dann als Callback, den jQuery nach dem
        // vollst�ndigen Laden der Skripte aufruft. Der Code ist nach den Beispielen auf
        // http://api.jquery.com/jQuery.getScript/ gebaut.
        String escapedMessage = message.replace("'", "\\'").replace("\"", "&quot;");
        String script = "$.getScript('" + contextPath + "/js/add-ons/jquery.pnotify.js', function() {" + //
                "    $.getScript('" + contextPath + "/js/add-ons/dmd.notifier.js', function() { " + //
                "        Notifier." + notificationFunction + "('" + escapedMessage + "');" + //
                "     });" + //
                "});";
        target.appendJavaScript(script);
    }

    public static void info(AjaxRequestTarget target, String message) {
        notify(target, message, "Info");
    }

    public static void error(AjaxRequestTarget target, String message) {
        notify(target, message, "Error");
    }

    public static void success(AjaxRequestTarget target, String message) {
        notify(target, message, "Success");
    }

}
