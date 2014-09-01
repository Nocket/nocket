/**
** These are user-written JavaScripts using jQuery. Make sure that jQuery is loaded before
** this script is loaded in your HTML-file.
**/

/*
	Script for using tabs in forms
*/
$(document).ready(function() {
	$(".tab-content").hide(); //Hide entire tab content
	$("ul.tabs li:first a").addClass("active").show(); //Activate first tab by default
	$(".tab-content:first").show(); //Show first tab content

	//On Click Event
	$("ul.tabs li a").click(function() {
		$("ul.tabs li a").removeClass("active"); //Remove any "active" class
		$(this).addClass("active"); //Add "active" class to selected tab
		$(".tab-content").hide(); //Hide entire tab content

		var activeTab = $(this).attr("href"); //Find the href attribute value to identify the active tab + content
		$(activeTab).show(); // Show the active ID content
		return false; // prevent that the browser scrolls to top after executing the function
	});
});