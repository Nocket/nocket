/*
	Script for vertical sidebar navigation
*/

$(document).ready(function () {
	// Hide all nested lists in the main menu
	$('#sidebar ul#accordion ul').hide();
	// display only the list with class "current" by default
	$('#sidebar ul#accordion ul.active').show();
	
	// if the user clicks on a menu item, execute the following function
	$('#sidebar ul#accordion > li > a').click(function () {
		// only change visible menus if the clicked sub-menu isn't displayed yet
		if($(this).siblings('ul').is(':not(:visible)')) {
			$('#sidebar nav li').children('ul').slideUp('fast'); //slideup all submenus
			$(this).siblings('ul').slideDown('fast'); //show the selected submenu
	 
			return false; // prevent that the browser follows the link and scrolls to top
		}
    });  
});