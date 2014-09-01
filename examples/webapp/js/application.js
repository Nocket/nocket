var isDirty;

/**
 * Initialisiert unobstrusive JavaScript-Ergänzungen für:
 * <ul>
 * <li>Dirty Check für Formulare
 * <li>Verfügbare Zeichen an Textarea Inputs
 * <li>Datepicker
 * <li>Wicket Ajax Listener
 * </ul>
 * Wird aufgerufen bei Document.ready Event
 */
function initComponents() {
	addAjaxSuccessEventListener();
	javaScriptAddOns();
}

function javaScriptAddOns() {
	addFormDirtyCheck();
	addDatepicker();
	addTextareaAddOns();
}

// Wicket: in den Ajax Request/Responst Zyklus einhängen und eigenes JavaScript ergänzen.
function addAjaxSuccessEventListener() {
//	Wicket.Event.subscribe('/ajax/call/success', function(jqEvent, attributes, jqXHR, errorThrown, textStatus) {
//		// prüfen ob nach dem speichern Tabs auf Fehler geprüft werden sollen
//		var tabValidation = $('form[data-tab-validator]').data('tab-validator');
//
//		// nach jedem Ajax call neu dekorieren
//		javaScriptAddOns();
//
//		// ggfs. Fehlermarker an Tabs anzeigen
//		if (tabValidation !== null) {
//			tabValidator(tabValidation);
//		}
//
//		// falls Fehler im Formular -> Dirty-Marker anzeigen, sonst Marker
//		// entfernen
//		if ($("p.error").length > 0) {
//			$('form[data-dirty-check]').trigger('dirty.areYouSure');
//		} 
//
//		return;
//	});
}

/**
 * Dirty-Check: Blendet ein Panel mit Änderungswarnung ein, wenn in dem Formular
 * Änderungen gemacht worden sind. Kann mehrfach aufgerufen werden dekoriert nur
 * die Formulare, die noch nicht dekoriert wurden.
 * 
 * Damit Formulare geprüft werden, muss in dem Form-Tag folgendes Attribut
 * gesetzt sein <form data-dirty-check="true">
 */
function addFormDirtyCheck() {
	var form = $('form[data-dirty-check]');
	
	if (form != null && form.length > 0) {
		if( form.attr("data-has-dirty-check") == null) {
			// html Attribute setzen, als Markierung
			form.attr("data-has-dirty-check", true);
			form.areYouSure({
				'silent' : true,
				'addRemoveFieldsMarksDirty' : true
			});
			form.bind('dirty.areYouSure', function() {
				var menu = $("#sectionMenu");
				if (menu.css('top') == '0px') {
					menu.animate({
						top : "+=52"
					}, 200);
				}
				$('#dirtyMarker').fadeIn(600);
				isDirty = true;
			});
		} else {
			form.trigger('rescan.areYouSure');
		}
	}
}

/**
 * Sorgt dafür, dass bei Textarea-Feldern die noch verfügbaren Zeichen angezeigt
 * werden.
 * 
 * Damit der Zähler korrekt angezeigt wird, muss an dem Textarea das Attribut
 * "maxlength" korrekt befüllt sein.
 */
function addTextareaAddOns() {
	$('textarea').maxlength({
	    alwaysShow : true,
	    placement : 'top'
	});
}


/**
 * Fügt Datepicker an Input-Felder mit der CSS-Klasse "datepicker" hinzu. Kann
 * mehrfach aufgerufen werden, dekoriert nur die Elemente, die noch nicht
 * dekoriert wurden.
 * 
 * In den Entities muss die Annotation "@Assisted" an den Datum-Attributen
 * entfernt werden. Dieses ist notwendig damit der standard DMDWEB-Datepicker
 * nicht angezeigt wird.
 */
function addDatepicker() {
	// 
	var datepicker = $('input.datepicker').not('[data-has-datepicker]');
	if (datepicker != null && datepicker.length > 0) {
		// html Attribute setzen, als Markierung
		datepicker.attr("data-has-datepicker", true);
		datepicker.datepicker({
		    format : "dd.mm.yyyy",
		    weekStart : 1,
		    language : "de",
		    todayBtn : "linked",
		    todayHighlight : true,
		    autoclose : true
		});
	}
}

/**
 * Prüft ob auf einem Tab Validierungsfehler sind und zeigt ggfs. einen Marker
 * am Tab an. Wird aktiviert über eine Data Attribut am Formular-Tag.
 * 
 * Beispiel: <form data-tab-validator="<validatorPage>">
 * 
 * Das Data-Attribute bekommt einen Identifier zu dem die notwendige Validierung
 * in dieser Methode implementiert wird.
 * 
 * @param validatorPage
 *            Seite auf der validiert werden soll
 */
function tabValidator(validatorForPage) {
	switch (validatorForPage) {
		case "kampagne":
			showErrorsOnTab("#daten", "#datenTabFehler");
			showErrorsOnTab("#werbeerfolg", "#werbeerfolgTabFehler");
			break;
		// add more ...
		default:
			// do nothing
	}
}

/**
 * Zählt die Anzahl der Fehlermeldungen in einem Bereich und zeigt bei
 * gefundenen Fehlern ein Warn-Icon in dem Tab an.
 * 
 * @param tabContentId:
 *            HTML ID, Bereich in dem die Fehlermeldungen gezählt werden sollen
 *            (z.B. "#daten")
 * @param tabErrorSignId:
 *            HTML ID, Bereich in dem das Warn-Icon angezeigt werden soll (z.B.
 *            "#datenTabHasErrors")
 * @returns Anzahl der gefundenen Fehler
 */
function showErrorsOnTab(tabContentId, tabErrorSignId) {
	var length = $(tabContentId + " p.error").length;

	if (length > 0) {
		$(tabErrorSignId).html("<i class='icon-error text-error'></i>&nbsp;&nbsp;");
	} else {
		$(tabErrorSignId).html("");
	}

	return length;
}

