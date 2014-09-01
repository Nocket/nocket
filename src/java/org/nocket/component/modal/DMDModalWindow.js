function dmdModalWindowSetzeHoeheAnModalBody(hoehe, maxhoehe) {
	// Find alle divs, die als class= "modal-body" stehen haben
	// Setze dort die styleangabe fuer die Hoehe
	$('#innerModal .modal-body').css({ "height": hoehe + "px", "max-height": maxhoehe });
}

function dmdModalWindowKorrigiereFormMargin() {
	// Forms haben in Bootstrap einen margin-bottom
	// Dieses passt nicht zu den modalen Dialogen. Dieser margin muss fuer die Forms der modalen Dialoge korrigiert werden
	$('#innerModal').find('form').css({"margin-bottom": "0px"});
}

function zeigeBlockerWennModalPanelVorhanden() {
	// Sucht nach einem geöffneten modalen Dialog. Das wird daran erkannt, dass es eine Element mit einer ID 'innerModal' gibt, die ein Element mit einer Klasse 'modal-header' gibt.
	if ($("#innerModal .modal-header").length !== 0 && $(".modalMessagePanel").length == 0) {
		// Ist dieses Element vorhanden, wird geschaut, ob schon ein Blocker eingetragen ist
		if ($("#blockerModal").length == 0) {
			// wenn nicht, das Blocker-Element als letztes Element des Bodys einfügen
			$('body').append("<div id=\"blockerModal\" class=\"blockerModal\" style=\"display: block;\"></div>");
		}
	} else {
		// Gibt es keinen modalen Dialog, soll es auch keinen Blocke geben
		$("#blockerModal").remove();
	}
	// Falls ein modales Message Panel vorhanden ist, gibt's nen zweiten Blocker
	if ($(".modalMessagePanel").length !== 0) {
		// Ist dieses Element vorhanden, wird geschaut, ob schon ein Blocker eingetragen ist
		if ($("#blockerModalMessagePanel").length == 0) {
			// wenn nicht, das Blocker-Element als letztes Element des Bodys einfügen
			$('.modalMessagePanel').parent().parent().append("<div id=\"blockerModalMessagePanel\" class=\"blockerModal\" style=\"display: block;\"></div>");
		}
	} else {
		// Gibt es keinen modalen Dialog, soll es auch keinen Blocke geben
		$("#blockerModalMessagePanel").remove();
	}
}

