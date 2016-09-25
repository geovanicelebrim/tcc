/**
 * 
 */

function scroll() {
	$('html, body').animate({
		scrollTop : 325
	}, 1000);
}

function show_graph() {
	var div = document.getElementById('graph');
	div.style.display = 'block';
	return false;
}

function hide_graph() {
	var div = document.getElementById('graph');
	div.style.display = 'none';
	return false;
}