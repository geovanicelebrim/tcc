<!doctype html>
<!--[if lt IE 7]>  <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<%@page import="java.util.ArrayList"%>
<html class="no-js" lang="">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Add New Files</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="./public/icons/icon.png" rel="shortcut icon">

<link rel="stylesheet" href="./public/css/bootstrap.min.css">
<link rel="stylesheet" href="./public/css/bootstrap.css">
<link rel="stylesheet" href="./public/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="./public/css/main.css">
<link rel="stylesheet" href="./public/css/daterangepicker.css">

<script src="./public/js/jquery/jquery-1.12.4.js"></script>
<script src="./public/js/util/utilFile.js"></script>
<script src="./public/js/addFile/moment.min.js"></script>
<script src="./public/js/addFile/daterangepicker.js"></script>
<script src="./public/js/vendor/bootstrap.min.js"></script>

</head>
<body>
	<!--[if lt IE 10]>
		<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
	<![endif]-->

	<div class="container">
		<div class="row">
			<div class="vertical-top">
				<img class="img-responsive left-block" id="logo"
					src="./public/images/cedim.jpg" style="width: 20%; height: 20%;">
				<div align="center">
						<h2>Add New Files</h2>
				</div>
				<script type="text/javascript">
					document.getElementById('logo').ondragstart = function() { return false; };
				</script>
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

						<form method="POST" action="ManagementAddNewFilePage?action=upload" onsubmit="return permissionFile('#selectBoxExtraction');"
							enctype="multipart/form-data">
							<table style="width: 100%;">
								<tr>
									<td style="padding: 0 15px 0 15px;">
										<div class="form-group">
											<label for="title">Title:</label> <input type="text" onkeypress="$('#error').hide();"
												class="form-control" name="title"
												placeholder="Inform the title of the document." required
												autocomplete="off">
										</div>
									</td>
									<td style="padding: 0 15px 0 15px;">
										<div class="form-group">
											<label for="author">Author:</label> <input type="text" onkeypress="$('#error').hide();"
												class="form-control" name="author"
												placeholder="Inform the author of the document." required
												autocomplete="off">
										</div>
									</td>
								</tr>
								<tr>
									<td style="padding: 0 15px 0 15px;">
										<div class="form-group">
											<label for="year">Year:</label> <input type="text" onkeypress="$('#error').hide();"
												class="form-control" name="year"
												placeholder="Inform the year of the document." required
												autocomplete="off">
										</div>
									</td>
									<td style="padding: 0 15px 0 15px;">
										<div class="form-group">
											<label for="source">Source:</label> <input type="text" onkeypress="$('#error').hide();"
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
												class="btn btn-primary"> Text File&hellip; <input accept="text/*"
													name="textFile" type="file" style="display: none;">
											</span>
											</label> <input type="text" class="form-control" readonly>
										</div></td>
									<td align="center" style="padding: 0 15px 0 15px;"><label
										for="source">Select the image</label>
										<div class="input-group">
											<label class="input-group-btn"> <span
												class="btn btn-primary"> Image&hellip; <input accept="image/*"
													name="imageFile" type="file" style="display: none;">
											</span>
											</label> <input type="text" class="form-control" readonly>
										</div></td>
								</tr>
								<tr>
									<td id="modeExtractionTD" colspan="2" align="center" style="padding: 0 15px 0 15px; padding-top: 15px;"><label
										for="source">Select the extraction mode</label>
										
										<select id="selectBoxExtraction" name="selectBoxExtraction" class="form-control text-center" style="width: auto;"
											onchange="changeModeExtraction('#selectBoxExtraction');">
											<option value="automatic" selected>Automatic extraction with OpenNLP</option>
											<option value="withFile">With annotation file, in brat output format</option>
										</select>

									</td>
									
									<td id="annotationFileTD" align="center" style="padding: 0 15px 0 15px; padding-top: 15px;" hidden="true">
										<label for="source">Select the annotation file</label>
										<div class="input-group">
											<label class="input-group-btn"> <span
												class="btn btn-primary"> Ann File&hellip; <input accept=".ann"
													name="annFile" type="file" style="display: none;">
											</span>
											</label> <input type="text" class="form-control" readonly>
										</div></td>
								</tr>
							</table> 
							
							<%
								@SuppressWarnings("unchecked")
								ArrayList<String> files = (ArrayList<String>) request.getSession().getAttribute("files");
								if(files != null) {
									for(String file : files) {
							%>
								<div style="padding: 5px 15px 0 15px; ">
									<div class="alert-success">
										<strong>Success!</strong> The file <% out.print(file); %> is added.
									</div>
								</div>
							<%
									}
								}
								
								String error = (String) request.getAttribute("error");
								String title = (String) request.getAttribute("title");
								error = error == null ? "" : error;
								title = title == null ? "" : title;
								
								if (error != "") {								
							%>
								
								<div id="error" style="padding: 5px 15px 0 15px; ">
									<div class="alert-warning">
										<strong>Warning!</strong> The file <% out.print(title); %> is invalid.
									</div>
								</div>
							
							<%
								}
							%>
							<div align="center" style="padding-bottom: 2em;">
								<br>
								<a class="btn btn-warning" href="ManagementLoginPage?action=authenticate">Back</a> 
								<input class="btn btn-primary" type="submit"
									value="Add File" name="add_file" id="add_file" />
								<%
									if ((files != null) && (files.size() > 0)) {
								%>
									<a class="btn btn-primary" id="gotoIndexer" onclick="showIndexer();">Next Step</a>
								<%
									} else {
								%>
									<a class="btn btn-primary" onclick="alert('Please enter at least one file.')">Next Step</a>
								<% } %>
							</div>
						</form>

					</div>

					<div role="tabpanel" class="tab-pane fade" id="indexer_file">
						<div align="center">
							Here you can schedule or index the added files. <br>
							<br>
						</div>
						<div align="center">
							<form id="index_file_form" method="POST" action="ManagementAddNewFilePage?action=index">
								<select id="selectBoxIndexer" name="selectBoxIndexer" onchange="changeFuncIndexer();" class="form-control text-center" style="width: 15em;">
									<option value="execute" selected>Execute indexing now</option>
									<option value="schedule">Schedule indexing</option>
								</select>

								<div id="dateIndexer" hidden="true"> <br>
									<input class="form-control text-center" type="text"
										name="birthdateIndexer" value="" style="width: 8em;" />
								</div>

								<div align="center" style="padding-bottom: 2em;">
									<br> <input class="btn btn-primary" id="gotoImport" onclick="showImport();"
										value="Next Step" type="submit" />
								</div>
							</form>
						</div>

					</div>

					<div role="tabpanel" class="tab-pane fade" id="import_database">
						<div align="center">
							Here you can schedule or import the added files to database. <br>
							<br>
						</div>
						<div align="center">
							<form id="import_file_form" method="POST" action="ManagementAddNewFilePage?action=import">
								<select id="selectBoxImport" name="selectBoxImport" onchange="changeFuncImport();" class="form-control text-center" style="width: 15em;">
									<option value="execute" selected>Execute import now</option>
									<option value="schedule">Schedule import</option>
								</select>

								<div id="dateImport" hidden="true"> <br>
									<input class="form-control text-center" type="text"
										name="birthdateImport" value="" style="width: 8em;" />
								</div>
	
	
								<div align="center" style="padding-bottom: 2em;">
									<br> <input class="btn btn-primary" type="submit"
										value="Conclude" name="conclude" id="conclude" />
								</div>
							</form>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>
</body>
</html>
