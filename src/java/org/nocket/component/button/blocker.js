// Create a global filter for events. It is defined globally so we can remove it later.
var blockerFunc = function(event) { event.preventDefault();	}

// Shows blocker
function block(id, image, title) {
	var body = $('body');
	var trigger = $('#'+id).data('show-overlay');
	body.append('<div id="dmdBlocker" class=\"blocker hide\"><div id="dmdBlockerPleaseWait" class=\"hide\"><img src=\"' + image + '\"><br>' + title + '</div></div>');
	// Bind the event filter to keypressed and keydown to filter all keyboard action for the entier page.
	if(!(trigger !== null && trigger == 'no')) { 
		body.bind("keydown keypress", blockerFunc);
		$('#dmdBlocker').fadeIn(200, function() {
			$('#dmdBlockerPleaseWait').fadeIn(400);
		});
	}
}

function unblock() {
	var blocker = $('#dmdBlocker');
	blocker.fadeOut(100, function() {
		blocker.remove();
		$('body').unbind("keydown keypress", blockerFunc);
	});
}