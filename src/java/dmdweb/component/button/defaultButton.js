// Creates a JS-handler and binds it to all input and select fields of the form.
// This handler reacts on the "ENTER" key press event and performs submit with the 
// button having class "btn-default".  
// On the other side this handler prohibits second submit of the form when the modal 
// dialog is opened. 
function registerDefaultButtonHandler() {
	$("form input, form select, form button, form checkbox").keypress(function (e) {
    if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
      // If modal window with one button is visible, perform click on it  
      if($('div.modal-footer a.btn').length === 1) {
        $('div.modal-footer a.btn').click();
      }
      // If modal window has default button, perform click on it
      if($('div.modal-footer a.btn-default').length === 1) {
        $('div.modal-footer a.btn-default').click();
      }
      // When modal window has more buttons just do nothing
      if($('div.modal-footer a.btn').length !== 0) {
        return false;        
      }
      // Otherwise perform click in a normal form
      if($('button.btn-default').length !== 0) { 
        $('button.btn-default').click();
        return false;
      }
      return true;
    } else {
      return true;
    }
 	});
}

