<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Management</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="apple-touch-icon" href="./public/icons/apple-touch-icon.png">
<link href="./public/icons/icon.png" rel="shortcut icon">

<link rel="stylesheet" href="./public/css/bootstrap.min.css">
<link rel="stylesheet" href="./public/css/bootstrap.css">
<link rel="stylesheet" href="./public/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="./public/css/main.css">

<!--[if lt IE 9]>
            <script src="js/vendor/html5-3.6-respond-1.4.2.min.js"></script>
        <![endif]-->


<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- Include Required Prerequisites -->
<script type="text/javascript"
	src="//cdn.jsdelivr.net/jquery/1/jquery.min.js"></script>
<script type="text/javascript"
	src="//cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="//cdn.jsdelivr.net/bootstrap/3/css/bootstrap.css" />

<!-- Include Date Range Picker -->
<script type="text/javascript"
	src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />



<script type="text/javascript">
	$(function() {

		// We can attach the `fileselect` event to all file inputs on the page
		$(document).on(
				'change',
				':file',
				function() {
					var input = $(this), numFiles = input.get(0).files ? input
							.get(0).files.length : 1, label = input.val()
							.replace(/\\/g, '/').replace(/.*\//, '');
					input.trigger('fileselect', [ numFiles, label ]);
				});

		// We can watch for our custom `fileselect` event like this
		$(document)
				.ready(
						function() {
							$(':file')
									.on(
											'fileselect',
											function(event, numFiles, label) {

												var input = $(this).parents(
														'.input-group').find(
														':text'), log = numFiles > 1 ? numFiles
														+ ' files selected'
														: label;

												if (input.length) {
													input.val(log);
												} else {
													if (log)
														alert(log);
												}

											});
						});

	});

</script>

</head>
<body>
	<!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->



	<div class="container">
		<div class="row">
			<div class="vertical-top">
				<img class="img-responsive left-block"
					src="./public/images/cedim.jpg" style="width: 20%; height: 20%;">
				<div align="center" style="font-size: 35pt; position: relative;">Management</div>
			</div>
			<div>

				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a href="#add_file"
						aria-controls="add_file" role="tab" data-toggle="tab">Add File</a></li>
					<li role="presentation"><a href="#indexer_file"
						aria-controls="indexer_file" role="tab" data-toggle="tab">Indexer
							File</a></li>
					<li role="presentation"><a href="#import_database"
						aria-controls="import_database" role="tab" data-toggle="tab">Import
							to Database</a></li>
				</ul>

				<!-- Tab panes -->
				<br>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane fade in active" id="add_file">

						<div align="center">
							Here you can enter a new document for the search engine.<br>
							<br>
						</div>

						<form method="POST" action="ManagementPage?action=upload"
							enctype="multipart/form-data">

							<table style="width: 100%;">
								<tr>
									<td style="padding: 0 15px 0 15px;">
										<div class="form-group">
											<label for="title">Title:</label> <input type="text"
												class="form-control" name="title"
												placeholder="Inform the title of the document." required
												autocomplete="off">
										</div>
									</td>
									<td style="padding: 0 15px 0 15px;">
										<div class="form-group">
											<label for="author">Author:</label> <input type="text"
												class="form-control" name="author"
												placeholder="Inform the author of the document." required
												autocomplete="off">
										</div>
									</td>
								</tr>
								<tr>
									<td style="padding: 0 15px 0 15px;">
										<div class="form-group">
											<label for="year">Year:</label> <input type="text"
												class="form-control" name="year"
												placeholder="Inform the year of the document." required
												autocomplete="off">
										</div>
									</td>
									<td style="padding: 0 15px 0 15px;">
										<div class="form-group">
											<label for="source">Source:</label> <input type="text"
												class="form-control" name="source"
												placeholder="Inform the source of the document." required
												autocomplete="off">
										</div>
									</td>
								</tr>

								<tr>
									<td align="center" style="padding: 0 15px 0 15px;"><label
										for="source">Select the text file</label>
										<div class="input-group">
											<label class="input-group-btn"> <span
												class="btn btn-primary"> Text File&hellip; <input
													name="textFile" type="file" style="display: none;" multiple>
											</span>
											</label> <input type="text" class="form-control" readonly>
										</div></td>
									<td align="center" style="padding: 0 15px 0 15px;"><label
										for="source">Select the annotation file</label>
										<div class="input-group">
											<label class="input-group-btn"> <span
												class="btn btn-primary"> Ann File&hellip; <input
													name="annFile" type="file" style="display: none;" multiple>
											</span>
											</label> <input type="text" class="form-control" readonly>
										</div></td>
								</tr>
							</table>

							<div align="center">
								<br> <input class="btn btn-primary" type="submit"
									value="Next Step" name="next_step" id="next_step" />
							</div>
						</form>

					</div>

					<div role="tabpanel" class="tab-pane fade" id="indexer_file">
						<div align="center">
							Here you can schedule or index the added files. <br>
							<br>
						</div>
						<div align="center">
							<select id="selectBoxIndexer" onchange="changeFuncIndexer();" class="form-control text-center" style="width: 15em;">
								<option value="execute" selected>Execute indexing now</option>
								<option value="shedule">Schedule indexing</option>
							</select>
							
							<script type="text/javascript">

								function changeFuncIndexer() {
									var selectBox = document.getElementById("selectBoxIndexer");
									var selectedValue = selectBox.options[selectBox.selectedIndex].value;
									var div = document.getElementById('dateIndexer');
									if(selectedValue == "shedule") {
										div.style.display = 'block';
									} else {
										div.style.display = 'none';
									}
								}
							
							</script>
							
							<div id="dateIndexer" hidden="true"> <br>
								<input class="form-control text-center" type="text"
									name="birthdateIndexer" value="" style="width: 8em;" />
	
								<script type="text/javascript">
								
								$(function() {
								    $('input[name="birthdateIndexer"]').daterangepicker({
								        singleDatePicker: true,
								        showDropdowns: true
								    }, 
								    function(start, end, label) {
								        var years = moment().diff(start, 'years');
								    });
								});
								</script>
							</div>


							<div align="center">
								<br> <input class="btn btn-primary" type="submit" onclick="alert('carregando');"
									value="Next Step" name="next_step" id="next_step" />
							</div>
						</div>

					</div>

					<div role="tabpanel" class="tab-pane fade" id="import_database">
						<div align="center">
							Here you can schedule or import the added files to database. <br>
							<br>
						</div>
						<div align="center">
							<select id="selectBoxImport" onchange="changeFuncImport();" class="form-control text-center" style="width: 15em;">
								<option value="execute" selected>Execute import now</option>
								<option value="shedule">Schedule import</option>
							</select>
							
							<script type="text/javascript">

								function changeFuncImport() {
									var selectBox = document.getElementById("selectBoxImport");
									var selectedValue = selectBox.options[selectBox.selectedIndex].value;
									var div = document.getElementById('dateImport');
									if(selectedValue == "shedule") {
										div.style.display = 'block';
									} else {
										div.style.display = 'none';
									}
								}
							
							</script>
							
							<div id="dateImport" hidden="true"> <br>
								<input class="form-control text-center" type="text"
									name="birthdateImport" value="" style="width: 8em;" />
	
								<script type="text/javascript">
								
								$(function() {
								    $('input[name="birthdateImport"]').daterangepicker({
								        singleDatePicker: true,
								        showDropdowns: true
								    }, 
								    function(start, end, label) {
								        var years = moment().diff(start, 'years');
								    });
								});
								</script>
							</div>


							<div align="center">
								<br> <input class="btn btn-primary" type="submit" onclick="alert('vai');"
									value="Conclude" name="conclude" id="conclude" />
							</div>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>

	<script src="./public/js/vendor/bootstrap.min.js"></script>
	<script src="./public/js/main.js"></script>
</body>
</html>
