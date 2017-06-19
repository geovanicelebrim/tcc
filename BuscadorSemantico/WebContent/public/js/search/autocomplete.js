$(function() {
	var availableTags = [ "Pessoa", "Evento", "Local", "Data", "Documento",
			"Organizacao", "Quantidade", "Grupo", "Papel" ];

	function split(val) {
		return val.split(/--/);
	}
	function extractLast(term) {
		return split(term).pop();
	}

	$("#search-query").on(
			"keydown",
			function(event) {

				if (event.keyCode === $.ui.keyCode.TAB
						&& $(this).autocomplete("instance").menu.active) {
					event.preventDefault();
				}
			}).autocomplete(
			{
				minLength : 0,
				source : function(request, response) {
					// delegate back to autocomplete, but extract the last term
					var e = document.getElementById("search-mode");
					var strUser = e.options[e.selectedIndex].value;
					if (strUser == "semantic") {
						response($.ui.autocomplete.filter(availableTags,
								extractLast(request.term)));
					}

				},
				focus : function() {
					// prevent value inserted on focus
					return false;
				},
				select : function(event, ui) {
					var terms = split(this.value);
					// remove the current input
					terms.pop();
					// add the selected item
					terms.push(ui.item.value + ":\"\"");
					// add placeholder to get the comma-and-space at the end
					// terms.push( "" );
					this.value = terms.join("--");
					setCaretToPos(this, this.value.length - 1);
					return false;
				}
			});
});

function setSelectionRange(input, selectionStart, selectionEnd) {
	if (input.setSelectionRange) {
		input.focus();
		input.setSelectionRange(selectionStart, selectionEnd);
	} else if (input.createTextRange) {
		var range = input.createTextRange();
		range.collapse(true);
		range.moveEnd('character', selectionEnd);
		range.moveStart('character', selectionStart);
		range.select();
	}
}

function setCaretToPos(input, pos) {
	setSelectionRange(input, pos, pos);
}