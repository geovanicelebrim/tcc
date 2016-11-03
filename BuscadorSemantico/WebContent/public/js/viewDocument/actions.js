function goBack() {
	window.history.back();
}

function downloadDocument() {

	var divContents = $("#document").html();
	var printWindow = window.open('', '', 'height=400,width=800');
	printWindow.document.write('<html><head>');
	printWindow.document.write('</head><body >');
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
	printWindow.print();
}

function printDocument(divID) {
	var printContents = document.getElementById(divID).innerHTML;
	var originalContents = document.body.innerHTML;

	document.body.innerHTML = printContents;

	window.print();

	document.body.innerHTML = originalContents;
}

function trustworthy(idDiv) {
	message(idDiv);
}

function unreliable(idDiv) {
	message(idDiv);
}

function cite(idDiv) {
	message(idDiv);
}

function message(idDiv) {
	$(idDiv).dialog({
		modal : true,
		buttons : {
			Ok : function() {
				$(this).dialog("close");
			}
		}
	});
}

function show_menu() {
	var div = document.getElementById('menu');
	div.style.display = 'block';
}

function hidden_menu() {
	$("#menu").fadeOut(800)
}

var cursorX;
var cursorY;
document.onmousemove = function(e){
    cursorX = e.pageX;
    cursorY = e.pageY;
}
setInterval(checkCursor, 500);
function checkCursor(){
//	alert(document.body.scrollTop);
	if(cursorY - document.body.scrollTop > 560) {
		show_menu();
	} else if (cursorY - document.body.scrollTop < 560) {
		hidden_menu();
	}
}

var mobile = (/iphone|ipad|ipod|android|blackberry|mini|windows\sce|palm/i.test(navigator.userAgent.toLowerCase()));
if (mobile) {
	alert("Some features are not yet optimized for mobile devices.")
}
else {
	
}

function showTimeoutMenu() {
	if (mobile) {
		var div = document.getElementById('menu');
		div.style.display = 'block';
		$("#menu").fadeOut(3000)
	}
}