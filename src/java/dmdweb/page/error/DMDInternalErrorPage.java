package dmdweb.page.error;

import org.apache.wicket.markup.html.basic.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dmdweb.page.DMDWebPage;

@SuppressWarnings("serial")
public class DMDInternalErrorPage extends DMDWebPage {

    final private static Logger log = LoggerFactory.getLogger(DMDInternalErrorPage.class);

    public DMDInternalErrorPage(Throwable t) {

	// TODO meis026 Die Klasse brauchen wir doch nicht wirklich! Die Derzeitiges Ausgabe ist natürlich bloedsinn.


	// Die Variable t war eine Member-Variable. Leider kann im Cause einer Throwable eine Exception stecken, die nicht Serialisierbar ist. Das führt, tata, zu einer Exception.
	// Also wurde alles in den Konstruktor verlagert.
	//	String errorID = Err.handler().process(t);


	String errorID = t.getMessage();
	log.error(t.getMessage(), t);
	add(new Label("errorID", errorID));
	getSession().invalidate();
    }

}
