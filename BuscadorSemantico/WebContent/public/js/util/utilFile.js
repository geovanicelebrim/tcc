var file1 = false;
var file2 = false;
$(function() {

	// We can attach the `fileselect` event to all file inputs on the page
	$(document).on(
			'change',
			':file',
			function() {
				var input = $(this), numFiles = input.get(0).files ? input
						.get(0).files.length : 1, label = input.val().replace(
						/\\/g, '/').replace(/.*\//, '');
				input.trigger('fileselect', [ numFiles, label ]);
			});

	// We can watch for our custom `fileselect` event like this
	$(document).ready(
			function() {
				$(':file').on(
						'fileselect',
						function(event, numFiles, label) {

							var input = $(this).parents('.input-group').find(
									':text'), log = numFiles > 1 ? numFiles
									+ ' files selected' : label;
							if (file1) {
								file2 = true;
							} else {
								file1 = true;
							}
							if (input.length) {
								input.val(log);
							} else {
								if (log)
									alert(log);
							}

						});
			});

});

function showIndexer() {
	$('.nav-tabs a[href="#indexer_file"]').tab('show');
	$('.nav li').not('.active').addClass('disabled');
	$('.nav li').not('.active').find('a').removeAttr("data-toggle");
}

function showImport() {
	$('.nav-tabs a[href="#import_database"]').tab('show');
	$('.nav li').not('.active').addClass('disabled');
	$('.nav li').not('.active').find('a').removeAttr("data-toggle");
}

$(document).ready(function() {
	/*disable non active tabs*/
	$('.nav li').not('.active').addClass('disabled');
	$('.nav li').not('.active').find('a').removeAttr("data-toggle");

	$('gotoIndexer').click(function() {
		/*enable next tab*/
		$('.nav li.active').next('li').removeClass('disabled');
		$('.nav li.active').next('li').find('a').attr("data-toggle", "tab")
	});
	$('gotoImport').click(function() {
		/*enable next tab*/
		$('.nav li.active').next('li').removeClass('disabled');
		$('.nav li.active').next('li').find('a').attr("data-toggle", "tab")
	});
});

jQuery(document).ready(function(){
	jQuery('#index_file_form').submit(function(){
		var dados = jQuery( this ).serialize();

		jQuery.ajax({
			type: "POST",
			url: "ManagementAddNewFilePage?action=index",
			data: dados,
			success: function( data )
			{
				
			}
		});
		
		return false;
	});
});

function changeFuncIndexer() {
	var selectBox = document.getElementById("selectBoxIndexer");
	var selectedValue = selectBox.options[selectBox.selectedIndex].value;
	var div = document.getElementById('dateIndexer');
	if(selectedValue == "schedule") {
		div.style.display = 'block';
	} else {
		div.style.display = 'none';
	}
}

function changeFuncImport() {
	var selectBox = document.getElementById("selectBoxImport");
	var selectedValue = selectBox.options[selectBox.selectedIndex].value;
	var div = document.getElementById('dateImport');
	if(selectedValue == "schedule") {
		div.style.display = 'block';
	} else {
		div.style.display = 'none';
	}
}

$(function() {
	$('input[name="birthdateIndexer"]').daterangepicker({
		singleDatePicker: true,
		showDropdowns: true
	}, 
	function(start, end, label) {
		var years = moment().diff(start, 'years');
	});
});

$(function() {
	$('input[name="birthdateImport"]').daterangepicker({
		singleDatePicker: true,
		showDropdowns: true
	}, 
	function(start, end, label) {
		var years = moment().diff(start, 'years');
	});
});