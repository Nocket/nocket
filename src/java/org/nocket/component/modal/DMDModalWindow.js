function dmdModalWindowSetzeHoeheAnModalBody( hoehe, maxhoehe) {
	// Find alle divs, die als class= "modal-body" stehen haben
	// Setze dort die styleangabe fuer die Hoehe
	$( '#innerModal .modal-body').css( {
	    "height" : hoehe + "px",
	    "max-height" : maxhoehe
	});
}

function dmdModalWindowKorrigiereFormMargin() {}

function zeigeBlockerWennModalPanelVorhanden() {
	// Sucht nach einem ge�ffneten modalen Dialog. Das wird daran erkannt, dass
	// es eine Element mit einer ID 'innerModal' gibt, die ein Element mit einer
	// Klasse 'modal-header' gibt.
	var dmdBlockerModal = $( "#dmdBlockerModal");
	
	if( $( "#innerModal .modal-header").length !== 0) {
		// Ist dieses Element vorhanden, wird geschaut, ob schon ein Blocker
		// eingetragen ist
		if( dmdBlockerModal.length == 0) {
			// wenn nicht, das Blocker-Element als letztes Element des Bodys
			// einfügen
			$( 'body').append( "<div id=\"dmdBlockerModal\" class=\"blockerModal hide\"></div>");
			$( "#dmdBlockerModal").fadeIn( 200);
		}
	}
	else {
		// Gibt es keinen modalen Dialog, soll es auch keinen Blocke geben
		dmdBlockerModal.fadeOut( 400, function() {
			dmdBlockerModal.remove();
		});
	}
}
